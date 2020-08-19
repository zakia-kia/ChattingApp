package com.zakia.idn.chattingapp.notification

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceId: FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        var firebaseUser = FirebaseAuth.getInstance().currentUser
        val referenceToken = FirebaseInstanceId.getInstance().token
        
        if (firebaseUser !=null){
            updateToken(referenceToken)
        }
    }

    private fun updateToken(referenceToken: String?) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val ref = FirebaseDatabase.getInstance().getReference().child("Tokens")
        val token = Token(referenceToken!!)
        ref.child(firebaseUser!!.uid).setValue(token)
    }
}