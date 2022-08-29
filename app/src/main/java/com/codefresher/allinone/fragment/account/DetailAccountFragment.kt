package com.codefresher.allinone.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.databinding.FragmentDetailAccountBinding


class DetailAccountFragment : Fragment() {
    private var _binding: FragmentDetailAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailAccountBinding.inflate(inflater, container, false)



        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}