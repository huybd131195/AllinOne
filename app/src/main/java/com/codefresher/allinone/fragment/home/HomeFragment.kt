package com.codefresher.allinone.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.codefresher.allinone.MainActivity
import com.codefresher.allinone.R
import com.codefresher.allinone.adapter.TrendingAdapter
import com.codefresher.allinone.databinding.FragmentHomeBinding
import com.codefresher.allinone.model.Trending
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        fetchData()
        return binding.root
    }

    private fun fetchData() {
        var post = ArrayList<Trending>()
        val postsAdapter = TrendingAdapter(requireContext(), post)
        FirebaseFirestore.getInstance().collection("Trending")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    post = documents.toObjects(Trending::class.java) as ArrayList<Trending>


                }
                postsAdapter.addList(post)


            }
            .addOnFailureListener {

            }

        binding.trendRecyclerView.adapter = postsAdapter
        postsAdapter.onclickItem = { trending ->
            val intent =
                Intent((activity as MainActivity), DetailActivity::class.java)
            intent.putExtra("url",trending)
            (activity as MainActivity).startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}