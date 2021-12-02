package com.supinfo.roomapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supinfo.roomapp.model.UserEntity

class UserAdapter(private var users : List<UserEntity>, val clickOnItemListener: ClickOnItemListener) : RecyclerView.Adapter<UserAdapter.UserHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindView(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserHolder(userView: View) : RecyclerView.ViewHolder(userView){

        private var textUser: TextView = itemView.findViewById(R.id.textUserFullName)
        private var imageDeleteUser: ImageView = itemView.findViewById(R.id.imageDeleteUser)

        @SuppressLint("SetTextI18n")
        fun bindView(userEntity: UserEntity){

            textUser.text = "${userEntity.firstName} ${userEntity.lastName}"

            imageDeleteUser.setOnClickListener {
                clickOnItemListener.onItemLClicked(userEntity)
            }

        }
    }
}