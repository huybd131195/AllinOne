package com.codefresher.allinone.fragment.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.databinding.FragmentDetailFavoriteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_addCreateFragment)
        }


        return binding.root
    }

    fun addUserToDatabase(){

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}