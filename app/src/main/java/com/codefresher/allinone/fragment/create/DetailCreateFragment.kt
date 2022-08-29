package com.codefresher.allinone.fragment.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.databinding.FragmentDetailCreateBinding


class DetailCreateFragment : Fragment() {

    private var _binding: FragmentDetailCreateBinding? = null
    private val binding get() = _binding!!
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
    }
    private fun getAndSetData(){
        val name = arguments?.getString("name")
        val age = arguments?.getString("age")?.toInt()
        val email = arguments?.getString("email")

        binding.edtAge.setText(name)
        if (age != null) {
            binding.edtAge.setText(age)
        }
        binding.edtAge.setText(email)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}