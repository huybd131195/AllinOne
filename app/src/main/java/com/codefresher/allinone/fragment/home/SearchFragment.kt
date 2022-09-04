package com.codefresher.allinone.fragment.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefresher.allinone.MainActivity
import com.codefresher.allinone.adapter.SearchAdapter
import com.codefresher.allinone.databinding.FragmentSearchBinding
import com.codefresher.allinone.model.SearchModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var searchList: List<SearchModel> = ArrayList()
    private val searchListAdapter = SearchAdapter(searchList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.recyclerViewSearch.adapter = searchListAdapter
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText: String = binding.searchView.text.toString()
                searchInFirestore(searchText)
            }

            override fun afterTextChanged(p0: Editable?) {
                onClick()
            }

        })


        return binding.root
    }

    private fun searchInFirestore(searchText: String) {
        firebaseFirestore.collection("All").orderBy("title")
            .startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener {
                if (it.isSuccessful) {

                    searchList = it.result!!.toObjects(SearchModel::class.java)
                    searchListAdapter.searchList = searchList
                    searchListAdapter.notifyDataSetChanged()
                }
            }
    }

    fun onClick() {
        searchListAdapter.addList(searchList)
        searchListAdapter.onclickItem = {

            searchListAdapter.onclickItem = {
                val intent =
                    Intent((activity as MainActivity), DetailActivity::class.java)
                intent.putExtra("url", it)
                (activity as MainActivity).startActivity(intent)
            }


        }
    }
}