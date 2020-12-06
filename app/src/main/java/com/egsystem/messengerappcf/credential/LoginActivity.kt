package com.egsystem.messengerappcf.credential

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.egsystem.messengerappcf.MainActivity
import com.egsystem.messengerappcf.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initToolbar()
        mAuth = FirebaseAuth.getInstance()
        init()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Login"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    private fun init() {
        btn_login.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val userEmail: String = et_user_name_email.text.toString()
        val userPassWd: String = et_user_passwd.text.toString()

        if (userEmail == "") {
            Toast.makeText(this@LoginActivity, "Please, write user email", Toast.LENGTH_LONG)
                .show()
        } else if (userPassWd == "") {
            Toast.makeText(this@LoginActivity, "Please, entry user password", Toast.LENGTH_LONG)
                .show()
        } else {
            mAuth.signInWithEmailAndPassword(userEmail, userPassWd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error Message:" + task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }


    }


}
