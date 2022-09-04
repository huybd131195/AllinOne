package com.codefresher.allinone.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.CreateItemBinding
import com.codefresher.allinone.model.Recipe
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

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

        val imageUrl  = recipeList[position].url
        Picasso.get().load(imageUrl).into(holder.adapterBinding.imgItem,object : Callback{
            override fun onSuccess() {
                holder.adapterBinding.progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                Toast.makeText(context,e?.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(recipeList[position])

            val title = recipeList[position].title
            val ingredients = recipeList[position].ingredients
            val directions = recipeList[position].directions
            val iD = recipeList[position].id
            val bundle = Bundle().apply {
                putString("title", title)
                putString("ingredients", ingredients)
                putString("directions", directions)
                putString("url",imageUrl)
                putString("imgName",recipeList[position].imgName)
                putString("id", iD)
            }
            Navigation.findNavController(view = it)
                .navigate(R.id.action_createFragment_to_detailCreateFragment, bundle)

        }
    }

    override fun getItemCount(): Int = recipeList.size

    fun getRecipeId(position: Int):String{
        return recipeList[position].id
    }

    fun getImageName(position: Int) : String{

        return recipeList[position].imgName

    }
}