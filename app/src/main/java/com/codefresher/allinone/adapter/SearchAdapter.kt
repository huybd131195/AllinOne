package com.codefresher.allinone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.databinding.FoodItemBinding
import com.codefresher.allinone.model.Food
import com.codefresher.allinone.model.SearchModel
import com.squareup.picasso.Picasso

class SearchAdapter(var searchList: List<SearchModel>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val adapterBinding: FoodItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root) {
        fun bind(searchModel: SearchModel) {
            adapterBinding.tvTrending.text = searchModel.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }
    var onclickItem: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchList[position])
        val imageUrl  = searchList[position].imageUrl
        Picasso.get().load(imageUrl).into(holder.adapterBinding.imgTrending)

        holder.itemView.setOnClickListener {
            onclickItem?.invoke(searchList[position].url)
        }


    }

    override fun getItemCount(): Int = searchList.size

    fun addList(array: List<SearchModel>){
        searchList=array
        notifyDataSetChanged()
    }
}