package com.egsystem.messengerappcf.fragments


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.egsystem.messengerappcf.R
import com.egsystem.messengerappcf.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    var userReference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    private val REQUEST_CODE = 438
    private var imageUri: Uri? = null
    private var storageRef: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)

        initView(view);

        firebaseUser = FirebaseAuth.getInstance().currentUser
        userReference =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        storageRef = FirebaseStorage.getInstance().reference.child("User Images")

        userReference!!.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: Users? = snapshot.getValue(Users::class.java)

                    if (context != null) {
                        view.tv_username.text = user!!.getUsername()
                        Picasso.get().load(user.getProfile()).into(view.iv_profile_image3)
                        Picasso.get().load(user.getCover()).into(view.iv_cover)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return view
    }

    private fun initView(view: View) {
        view.iv_profile_image3.setOnClickListener {
            pickImage()
        }

        view.iv_cover.setOnClickListener {
            pickImage()
        }


    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data!!.data != null) {
            imageUri = data.data
            Toast.makeText(context, "Uploading...", Toast.LENGTH_LONG).show()
            uploadImageToDatabase()
        }

    }

    private fun uploadImageToDatabase() {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Image is uploading...please wait")
        progressDialog.setTitle("Upload")
        progressDialog.show()

        if (imageUri != null){
            val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".JPG")
        }
    }


}
