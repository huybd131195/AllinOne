package com.codefresher.allinone.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.allinone.R
import com.codefresher.allinone.adapter.RecipeAdapter
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.model.Recipe
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("Recipes")
    val recipeList = ArrayList<Recipe>()
    lateinit var recipeAdapter: RecipeAdapter

    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference: StorageReference = firebaseStorage.reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        retrieveData()
        deleteItem()
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_addCreateFragment)
        }



        return binding.root
    }


    fun retrieveData() {
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recipeList.clear()
                for (eachUser in snapshot.children) {
                    val recipe = eachUser.getValue<Recipe>()
                    if (recipe != null) {

                        recipeList.add(recipe)
                    }
                    recipeAdapter = RecipeAdapter(requireContext(), recipeList)
                    binding.recyclerView.adapter = recipeAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    private fun deleteItem() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = recipeAdapter.getRecipeId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()
                val imageName = recipeAdapter.getImageName(viewHolder.adapterPosition)
                val imageReference = storageReference.child("images").child(imageName)
                imageReference.delete()
                Toast.makeText(context,"The recipe was deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.recyclerView)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}