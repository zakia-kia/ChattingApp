package com.zakia.idn.chattingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.zakia.idn.chattingapp.R
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val toolbar: Toolbar = findViewById(R.id.toolbar_signin)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.text_signin)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        mAuth = FirebaseAuth.getInstance()
        btn_signin.setOnClickListener {
            signinUser()
        }
    }

    private fun signinUser() {
        val email: String = et_email_signin.text.toString()
        val password: String = et_password_signin.text.toString()

        if (email == "") {
            Toast.makeText(
                this, getString(R.string.text_email_message),
                Toast.LENGTH_LONG
            ).show()
        } else if (password == "") {
            Toast.makeText(
                this, getString(R.string.text_password_message),
                Toast.LENGTH_LONG
            ).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this, getString(R.string.text_error_message)
                                + task.exception!!.message.toString(), Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
