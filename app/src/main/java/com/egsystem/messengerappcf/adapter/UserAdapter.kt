package com.egsystem.messengerappcf.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egsystem.messengerappcf.R
import com.egsystem.messengerappcf.model.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

class UserAdapter(mContext: Context, mUsers: List<Users>, isChatCheck: Boolean): RecyclerView.Adapter<UserAdapter.ViewHolderM?>() {

    private val mContext: Context
    private val mUsers: List<Users>
    private var isChatCheck: Boolean

    init {
        this.mContext = mContext
        this.mUsers = mUsers
        this.isChatCheck = isChatCheck
    }


    class ViewHolderM(itemView: View): RecyclerView.ViewHolder(itemView) {

        var tv_user_name2: TextView
        var tv_msg_last: TextView
        var iv_profile_image2: CircleImageView
        var iv_online: CircleImageView
        var iv_offline: CircleImageView


        init {
            tv_user_name2 = itemView.findViewById(R.id.tv_user_name2)
            tv_msg_last = itemView.findViewById(R.id.tv_msg_last)
            iv_profile_image2 = itemView.findViewById(R.id.iv_profile_image2)
            iv_online = itemView.findViewById(R.id.iv_online)
            iv_offline = itemView.findViewById(R.id.iv_offline)
        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderM {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout, parent, false)
        return UserAdapter.ViewHolderM(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderM, position: Int) {
        val user: Users = mUsers[position]

        holder.tv_user_name2.text = user!!.getUsername()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.ic_profile).into(holder.iv_profile_image2)
    }

}