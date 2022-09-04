package com.codefresher.allinone.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.MainActivity
import com.codefresher.allinone.R
import com.codefresher.allinone.adapter.FoodAdapter
import com.codefresher.allinone.databinding.FragmentHomeBinding
import com.codefresher.allinone.model.Food
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lovingNow.setOnClickListener {
            val url =
                "https://www.foodnetwork.com/healthy/packages/healthy-every-week/healthy-mains/healthy-weeknight-dinners"
            val intent =
                Intent((activity as MainActivity), DetailActivity::class.java)
            intent.putExtra("url", url)
            (activity as MainActivity).startActivity(intent)
        }

        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        fetchDataTrending()
        fetchDataChill()
        drinksData()
        recommendData()
        return binding.root
    }

    private fun recommendData() {
        var post = ArrayList<Food>()
        val postsAdapter = FoodAdapter(requireContext(), post)

        FirebaseFirestore.getInstance().collection("Recommend")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    post = documents.toObjects(Food::class.java) as ArrayList<Food>


                }
                postsAdapter.addList(post)


            }
            .addOnFailureListener {

            }

        binding.recommendRecyclerView.adapter = postsAdapter
        postsAdapter.onclickItem = {
            val intent =
                Intent((activity as MainActivity), DetailActivity::class.java)
            intent.putExtra("url", it)
            (activity as MainActivity).startActivity(intent)
        }
    }

    private fun fetchDataTrending() {
        var post = ArrayList<Food>()
        val postsAdapter = FoodAdapter(requireContext(), post)

        FirebaseFirestore.getInstance().collection("Trending")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    post = documents.toObjects(Food::class.java) as ArrayList<Food>


                }
                postsAdapter.addList(post)


            }
            .addOnFailureListener {

            }

        binding.trendRecyclerView.adapter = postsAdapter
        postsAdapter.onclickItem = {
            val intent =
                Intent((activity as MainActivity), DetailActivity::class.java)
            intent.putExtra("url", it)
            (activity as MainActivity).startActivity(intent)
        }

    }

    private fun fetchDataChill() {
        var post = ArrayList<Food>()
        val postsAdapter = FoodAdapter(requireContext(), post)

        FirebaseFirestore.getInstance().collection("Grillin' + Chillin'")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    post = documents.toObjects(Food::class.java) as ArrayList<Food>


                }
                postsAdapter.addList(post)


            }
            .addOnFailureListener {

            }

        binding.chillRecyclerView.adapter = postsAdapter
        postsAdapter.onclickItem = {
            val intent =
                Intent((activity as MainActivity), DetailActivity::class.java)
            intent.putExtra("url", it)
            (activity as MainActivity).startActivity(intent)
        }

    }

    private fun drinksData() {
        var post = ArrayList<Food>()
        val postsAdapter = FoodAdapter(requireContext(), post)

        FirebaseFirestore.getInstance().collection("Drink")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    post = documents.toObjects(Food::class.java) as ArrayList<Food>


                }
                postsAdapter.addList(post)


            }
            .addOnFailureListener {

            }

        binding.drinksRecyclerView.adapter = postsAdapter
        postsAdapter.onclickItem = {
            val intent =
                Intent((activity as MainActivity), DetailActivity::class.java)
            intent.putExtra("url", it)
            (activity as MainActivity).startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}