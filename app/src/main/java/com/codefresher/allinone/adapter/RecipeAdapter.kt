package com.codefresher.allinone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.databinding.CreateItemBinding
import com.codefresher.allinone.databinding.FoodItemBinding
import com.codefresher.allinone.model.Recipe

class RecipeAdapter(
    var context: Context,
    var recipeList: ArrayList<Recipe>,
    var onItemClick: ((Recipe?) -> Unit)? = null
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    inner class RecipeViewHolder(val adapterBinding: CreateItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = CreateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.adapterBinding.tvTitle.text = recipeList[position].title
    }

    override fun getItemCount(): Int = recipeList.size
}