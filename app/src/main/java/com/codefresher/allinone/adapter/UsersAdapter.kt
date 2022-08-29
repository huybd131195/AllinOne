package com.codefresher.allinone.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.CreateItemBinding
import com.codefresher.allinone.fragment.create.DetailCreateFragment
import com.codefresher.allinone.model.Users
import com.google.firebase.database.ValueEventListener

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
            tvAge.text = users.userAge.toString()
            val tvEmail = adapterBinding.tvName
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

//            val bundle = Bundle().apply {
//                putString("name",bundleName)
//                putString("age",bundleAge)
//                putString("email",bundleEmail)
//                putString("id",iD)
//            }
            val bundle = Bundle()
            bundle.putString("name", bundleName)
            bundle.putString("age", bundleAge)
            bundle.putString("email", bundleEmail)
            bundle.putString("id", iD)
            val fragment = DetailCreateFragment()
            fragment.arguments = bundle
        }
    }


    override fun getItemCount(): Int = userList.size
}

