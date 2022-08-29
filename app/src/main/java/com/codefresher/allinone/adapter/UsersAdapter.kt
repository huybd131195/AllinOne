package com.codefresher.allinone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.databinding.CreateItemBinding
import com.codefresher.allinone.model.Users
import com.google.firebase.database.ValueEventListener

class UsersAdapter(
    var context: Context,
    var userList: ArrayList<Users>
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    inner class UsersViewHolder(val adapterBinding: CreateItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = CreateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.adapterBinding.tvName.text = userList[position].userName
        holder.adapterBinding.tvAge.text = userList[position].userAge.toString()
        holder.adapterBinding.tvEmail.text = userList[position].userEmail
    }

    override fun getItemCount(): Int = userList.size
}

