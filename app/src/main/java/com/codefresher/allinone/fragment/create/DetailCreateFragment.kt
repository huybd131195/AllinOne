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
    private val myReference: DatabaseReference = database.reference.child("MyUsers")
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

        binding.btnUpdate.setOnClickListener {
            updateData()
        }

    }

    private fun getAndSetData() {
        val name = arguments?.getString("name")
        val age = arguments?.getString("age")
        val email = arguments?.getString("email")

        binding.edtName.setText(name)
        binding.edtAge.setText(age)
        binding.edtEmail.setText(email)
    }

    private fun updateData() {
        val userId = arguments?.getString("id").toString()
        val updateName = binding.edtName.text.toString()
        val updateAge = binding.edtAge.text.toString()
        val updateEmail = binding.edtEmail.text.toString()


        val userMap = mutableMapOf<String, Any>()
        userMap["userId"] = userId
        userMap["userName"] = updateName
        userMap["userAge"] = updateAge
        userMap["userEmail"] = updateEmail

        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->
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