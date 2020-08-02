package com.zakia.idn.chattingapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.zakia.idn.chattingapp.R
import com.zakia.idn.chattingapp.adapter.UserAdapter
import com.zakia.idn.chattingapp.model.Users
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private var userAndapter: UserAdapter? = null
    private var mUsers: List<Users>? = null
    private var recyclerView: RecyclerView? = null
    private var searchEdit: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        searchEdit = view.findViewById(R.id.et_search)
        recyclerView = view.findViewById(R.id.rv_search_list)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        mUsers = ArrayList()
        retrieveAllUser()

        searchEdit!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchForUser(s.toString().toLowerCase())
            }
        })

        return view
    }

    private fun searchForUser(toLowerCase: String) {
        var fireUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val queryUsers = FirebaseDatabase.getInstance().reference.child("Users").orderByChild("search")
            .startAt(toLowerCase).endAt(toLowerCase + "\uf8ff")

        queryUsers.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshotS: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()
                for ( snapshot in snapshotS.children){
                    val user : Users? = snapshot.getValue(Users::class.java)
                    if (!(user!!.getUID()).equals((fireUserID))){
                        (mUsers as ArrayList<Users>).add(user)
                    }
                }
                userAndapter  = UserAdapter(context!!, mUsers!!,  false)
                recyclerView!!.adapter = userAndapter
            }

        })

    }
    //uniq untuk query = uf8ff

    private fun retrieveAllUser() {
        var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val refUsers = FirebaseDatabase.getInstance().reference.child("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshots: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()
                if (searchEdit!!.text.toString() == "") {
                    for (snapshot in snapshots.children) {
                        val user: Users? = snapshot.getValue(Users::class.java)
                        if (!(user!!.getUID()).equals(firebaseUserID)) {
                            (mUsers as ArrayList<Users>).add(user)
                        }
                    }
                    userAndapter = UserAdapter(context!!, mUsers!!, false)
                    recyclerView!!.adapter = userAndapter
                }
            }

        })
    }

}
