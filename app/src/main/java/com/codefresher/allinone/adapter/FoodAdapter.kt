package com.codefresher.allinone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codefresher.allinone.databinding.FoodItemBinding
import com.codefresher.allinone.model.Food

class FoodAdapter(
    var context: Context,
    private var foodList: List<Food>

) : RecyclerView.Adapter<FoodAdapter.TrendingViewHolder>() {
    inner class TrendingViewHolder(val adapterBinding: FoodItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingViewHolder(binding)
    }
    var onclickItem: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {

        val post = foodList[position]
        holder.adapterBinding.tvTrending.text = post.title
        Glide.with(context)
            .load(post.imageUrl)
            .into(holder.adapterBinding.imgTrending)

        holder.adapterBinding.imgTrending.setOnClickListener {
            onclickItem?.invoke(post.url)
        }


    }

    override fun getItemCount(): Int = foodList.size

    fun addList(array: List<Food>){
        foodList=array
        notifyDataSetChanged()
    }
}

