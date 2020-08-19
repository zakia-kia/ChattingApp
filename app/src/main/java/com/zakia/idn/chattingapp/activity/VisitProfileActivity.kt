package com.zakia.idn.chattingapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.zakia.idn.chattingapp.R
import com.zakia.idn.chattingapp.model.Users
import kotlinx.android.synthetic.main.activity_visit_profile.*
import kotlinx.android.synthetic.main.fragment_setting.*

class VisitProfileActivity : AppCompatActivity() {
    private var userVisitId : String? = null
    var user : Users? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_profile)

        userVisitId = intent.getStringExtra("visit_id")

        val ref = FirebaseDatabase.getInstance().reference.child("Users")
            .child(userVisitId!!)
        ref.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    user = snapshot.getValue(Users::class.java)

                    username_display_visit.text = user!!.getUserName()
                    Picasso.get().load(user!!.getProfile()).into(profile_display_visit)
                    Picasso.get().load(user!!.getCover()).into(cv_visit_profile)
                }
            }
        })

        facebook_display_visit.setOnClickListener {
            val uri = Uri.parse(user!!.getFacebook())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        instagram_display_visit.setOnClickListener {
            val uri = Uri.parse(user.getInstagram())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        website_display_visit.setOnClickListener {
            val uri = Uri.parse(user!!.getWebsite())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        send_msg_btn_visit.setOnClickListener {
            val intent = Intent (this, MessageChatActivity::class.java
            val intent.putExtra ("visit_id",user!!.getUID())
            startActivity(intent)
        }
    }
}