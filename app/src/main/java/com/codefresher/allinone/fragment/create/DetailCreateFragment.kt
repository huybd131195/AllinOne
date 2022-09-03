package com.codefresher.allinone.fragment.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.databinding.FragmentDetailCreateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DetailCreateFragment : Fragment() {

    private var _binding: FragmentDetailCreateBinding? = null
    private val binding get() = _binding!!
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("Recipes")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCreateBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAndSetData()

        binding.btnUpdateRecipe.setOnClickListener {
            updateData()
        }

    }

    private fun getAndSetData() {
        val title = arguments?.getString("title")
        val ingredients = arguments?.getString("ingredients")
        val directions = arguments?.getString("directions")

        binding.edtTitle.setText(title)
        binding.edtIngredients.setText(ingredients)
        binding.edtDirections.setText(directions)
    }

    private fun updateData() {
        val updateTitle = binding.edtTitle.text.toString()
        val updateIngredients = binding.edtIngredients.text.toString()
        val updateDirections = binding.edtDirections.text.toString()
        val recipeId = arguments?.getString("id").toString()

        val recipeMap = mutableMapOf<String, Any>()
        recipeMap["recipeId"] = recipeId
        recipeMap["recipeTitle"] = updateTitle
        recipeMap["recipeIngredients"] = updateIngredients
        recipeMap["recipeDirections"] = updateDirections

        myReference.child(recipeId).updateChildren(recipeMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().popBackStack()

            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}