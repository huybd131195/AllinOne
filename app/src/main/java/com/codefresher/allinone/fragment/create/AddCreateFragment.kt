package com.codefresher.allinone.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.databinding.FragmentAddCreateBinding
import com.codefresher.allinone.model.Recipe
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddCreateFragment : Fragment() {

    private var _binding: FragmentAddCreateBinding? = null
    private val binding get() = _binding!!

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("Recipes")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCreateBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddRecipe.setOnClickListener {
            addRecipeToDatabase()
        }

    }

    fun addRecipeToDatabase() {
        val title: String = binding.edtTitle.text.toString()
        val ingredients: String = binding.edtIngredients.text.toString()
        val directions: String = binding.edtDirections.text.toString()

        val id = myReference.push().key.toString()

        val recipe = Recipe(id, title, ingredients, directions)

        myReference.child(id).setValue(recipe).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "New recipe created",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else {

                Toast.makeText(
                    context, task.exception.toString(), Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
