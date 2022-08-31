package com.codefresher.allinone.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.CreateItemBinding
import com.codefresher.allinone.model.Users

class UsersAdapter(
    var context: Context,
    var userList: ArrayList<Users>,
    var onItemClick: ((Users?) -> Unit)? = null

) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    inner class UsersViewHolder(val adapterBinding: CreateItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root) {

        fun binItem(users: Users) {
            val tvName = adapterBinding.tvName
            tvName.text = users.userName
            val tvAge = adapterBinding.tvAge
            tvAge.text = users.userAge
            val tvEmail = adapterBinding.tvEmail
            tvEmail.text = users.userEmail


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = CreateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.binItem(userList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(userList[position])
            val tvN: TextView? = it.findViewById(R.id.tvName)
            val tvA: TextView? = it.findViewById(R.id.tvAge)
            val tvE: TextView? = it.findViewById(R.id.tvEmail)

            val bundleName = tvN?.text.toString()
            val bundleAge = tvA?.text.toString()
            val bundleEmail = tvE?.text.toString()
            val iD = userList[position].userId

            val bundle = Bundle().apply {
                putString("name",bundleName)
                putString("age",bundleAge)
                putString("email",bundleEmail)
                putString("id",iD)
            }


            Navigation.findNavController(view = it).navigate(R.id.action_createFragment_to_detailCreateFragment,bundle)
        }
    }



    override fun getItemCount(): Int = userList.size

    fun getUserId(position: Int):String{
        return userList[position].userId
    }
}

