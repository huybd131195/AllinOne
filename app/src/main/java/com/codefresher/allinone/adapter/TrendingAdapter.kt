package com.codefresher.allinone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codefresher.allinone.MainActivity
import com.codefresher.allinone.databinding.TrendingItemBinding
import com.codefresher.allinone.model.Trending

class TrendingAdapter(
    var context: Context,
    private var trendingList: List<Trending>

) : RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {
    inner class TrendingViewHolder(val adapterBinding: TrendingItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = TrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingViewHolder(binding)
    }
    var onclickItem: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {

        val post = trendingList[position]
        holder.adapterBinding.tvTrending.text = post.title
        Glide.with(context)
            .load(post.imageUrl)
            .into(holder.adapterBinding.imgTrending)

        holder.adapterBinding.imgTrending.setOnClickListener {
            onclickItem?.invoke(post.url)
        }


    }

    override fun getItemCount(): Int = trendingList.size

    fun addList(array: List<Trending>){
        trendingList=array
        notifyDataSetChanged()
    }
}

