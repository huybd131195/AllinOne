package com.codefresher.allinone.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentDetailHomeBinding
import com.codefresher.allinone.databinding.FragmentHomeBinding


class DetailHomeFragment : Fragment() {

    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHomeBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}