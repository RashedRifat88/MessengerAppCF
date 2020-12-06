package com.egsystem.messengerappcf.credential

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.egsystem.messengerappcf.MainActivity
import com.egsystem.messengerappcf.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initToolbar()
        init()

        mAuth = FirebaseAuth.getInstance()
    }

    private fun init() {
        btn_register.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val userName: String = et_user_name.text.toString()
        val userEmail: String = et_user_email.text.toString()
        val userPassWd: String = et_user_passwd.text.toString()

        if (userName == "") {
            Toast.makeText(this@RegisterActivity, "Please, write user name", Toast.LENGTH_LONG)
                .show()
        } else if (userEmail == "") {
            Toast.makeText(this@RegisterActivity, "Please, write user email", Toast.LENGTH_LONG)
                .show()
        } else if (userPassWd == "") {
            Toast.makeText(this@RegisterActivity, "Please, entry user password", Toast.LENGTH_LONG)
                .show()
        } else {
            mAuth.createUserWithEmailAndPassword(userEmail, userPassWd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        firebaseUserId = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users")
                            .child(firebaseUserId)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserId
                        userHashMap["username"] = userName
                        userHashMap["profile"] =
                            "https://firebasestorage.googleapis.com/v0/b/messengerappcf.appspot.com/o/user_placeholder.jpg?alt=media&token=59f4441e-3f24-406b-aad5-0883caa152ae"
                        userHashMap["cover"] =
                            "https://firebasestorage.googleapis.com/v0/b/messengerappcf.appspot.com/o/cover.jpg?alt=media&token=fd3ef35e-b17e-47a1-93de-32e3108ac92d"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = userName.toLowerCase()
                        userHashMap["facebook"] = "https://m.facebook.com/"
                        userHashMap["instagram"] = "https://m.instagram.com/"
                        userHashMap["website"] = "https://www.google.com/"

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error Message:" + task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Registration"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
