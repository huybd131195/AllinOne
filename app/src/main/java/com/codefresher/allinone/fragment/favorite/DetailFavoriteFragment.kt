package com.codefresher.allinone.fragment.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentDetailFavoriteBinding
import com.codefresher.allinone.databinding.FragmentHomeBinding


class DetailFavoriteFragment : Fragment() {
    private var _binding: FragmentDetailFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailFavoriteBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}