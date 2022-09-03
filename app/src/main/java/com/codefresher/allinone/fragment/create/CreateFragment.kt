package com.codefresher.allinone.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codefresher.allinone.R
import com.codefresher.allinone.adapter.RecipeAdapter
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.model.Recipe
import com.google.firebase.database.*


class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("Recipes")
    private val recipeList = ArrayList<Recipe>()
    lateinit var recipeAdapter: RecipeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_addCreateFragment)
        }
        retrieveData()
        return binding.root
    }

    fun retrieveData(){
        myReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (eachUser in snapshot.children){
                    recipeList.clear()
                    val recipe = eachUser.getValue(Recipe::class.java)

                    if (recipe != null){

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



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}