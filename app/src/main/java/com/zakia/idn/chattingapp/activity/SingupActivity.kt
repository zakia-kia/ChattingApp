package com.zakia.idn.chattingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zakia.idn.chattingapp.R
import kotlinx.android.synthetic.main.activity_singup.*

class SingupActivity : AppCompatActivity() {

    private lateinit var mAunt: FirebaseAuth
    private lateinit var refUser: DatabaseReference
    private var firebaseUsetId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        val toolbar: Toolbar = findViewById(R.id.toolbar_signup)
        setSupportActionBar(toolbar)

        supportActionBar!!.title = getString(R.string.text_signup)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        mAunt = FirebaseAuth.getInstance()
        btn_signup.setOnClickListener {
            signupUser()
        }
    }

    private fun signupUser() {
        val username: String = et_user_name_signup.text.toString()
        val email: String = et_email.text.toString()
        val password: String = et_password_signup.text.toString()


        if (username == "") {
            Toast.makeText(this, getString(R.string.text_message_username), Toast.LENGTH_LONG)
                .show()

        } else if (email == "") {
            Toast.makeText(this, getString(R.string.text_email_message), Toast.LENGTH_LONG).show()

        } else if (password == "") {
            Toast.makeText(this, getString(R.string.text_password_message), Toast.LENGTH_LONG)
                .show()

        } else {
            mAunt.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUsetId = mAunt.currentUser!!.uid
                    refUser =
                        FirebaseDatabase.getInstance().reference.child(getString(R.string.Text_users))
                            .child(firebaseUsetId)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUsetId
                    userHashMap["username"] = username
                    userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/chattingapp-76f4e.appspot.com/o/profile.jpg?alt=media&token=23e240b7-fc68-452d-b02d-990a836d3072"
                    userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/chattingapp-76f4e.appspot.com/o/cover.jpg?alt=media&token=be110dcf-5076-4b01-b91f-28b66d8d1e9f"
                    userHashMap["status"] = "offline"
                    userHashMap["search"] = username.toLowerCase()
                    userHashMap["facebook"] = "https://m.facebook.com"
                    userHashMap["instagram"] = "https://m.instagram.com"
                    userHashMap["website"] = "https://www.google.com"

                    refUser.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }

                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.text_error_message) + task.exception!!.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
