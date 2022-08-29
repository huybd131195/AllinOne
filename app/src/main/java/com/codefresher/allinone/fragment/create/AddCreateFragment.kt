package com.codefresher.allinone.fragment.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentAddCreateBinding
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.model.Users
import com.google.firebase.database.*


class AddCreateFragment : Fragment() {

    private var _binding: FragmentAddCreateBinding? = null
    private val binding get() = _binding!!
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCreateBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddUser.setOnClickListener {
            addUserToDatabase()
        }

    }

    private fun addUserToDatabase() {
        val name: String = binding.edtName.text.toString()
        val age: Int = binding.edtAge.text.toString().toInt()
        val email: String = binding.edtEmail.text.toString()

        val id: String = myReference.push().key.toString()

        val user = Users(id, name, age, email)

        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "The new user has been added to the database",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().popBackStack()

            } else {
                Toast.makeText(
                    context,
                    task.exception.toString(),
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}