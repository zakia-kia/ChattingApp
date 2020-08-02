package com.zakia.idn.chattingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zakia.idn.chattingapp.R
import com.zakia.idn.chattingapp.model.Users
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(mContext : Context, mUsers : List<Users>, isChatCheck: Boolean) : RecyclerView.Adapter<UserAdapter.ViewHolder?>(){
    private val mContext : Context
    private val mUsers : List<Users>
    private val isChatCheck : Boolean
    val lastMessage : String = ""

    //inisialisasi
    init {
        this.mUsers = mUsers
        this.mContext = mContext
        this.isChatCheck = isChatCheck
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view : View = LayoutInflater.from(mContext)
            .inflate(R.layout.user_search_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user : Users = mUsers[position]
        holder.userName.text = user!!.getUserName()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.profile).into(holder.profile)
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView
        var profile: CircleImageView
        var onlineStatus: CircleImageView
        var offlineStatus: CircleImageView
        var lastMessage: TextView

        init {
            userName = itemView.findViewById(R.id.tv_username_search)
            profile = itemView.findViewById(R.id.iv_profile_search)
            onlineStatus = itemView.findViewById(R.id.iv_online)
            offlineStatus = itemView.findViewById(R.id.iv_offline)
            lastMessage = itemView.findViewById(R.id.tv_message_last)
        }
    }



}