package com.codefresher.allinone.fragment.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.codefresher.allinone.R
import com.codefresher.allinone.adapter.UsersAdapter
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.model.Users
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")
    private val userList = ArrayList<Users>()
    lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_addCreateFragment)
        }
        deleteItem()
        retrieveDataFromDatabase()

        return binding.root
    }
    private fun deleteItem(){
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val id = usersAdapter.getUserId(viewHolder.adapterPosition)

                myReference.child(id).removeValue()
            }

        }).attachToRecyclerView(binding.recyclerView)
    }
    private fun retrieveDataFromDatabase() {
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (eachUser in snapshot.children) {
                    val user = eachUser.getValue<Users>()

                    if (user != null) {
                        println("user Id: ${user.userId}")
                        println("userName: ${user.userName}")
                        println("userAge: ${user.userAge}")
                        println("userEmail: ${user.userEmail}")
                        println("xxxxxxxxxxxxxxxxxxxxxxx")
                        userList.add(user)
                    }

                    usersAdapter = UsersAdapter(requireContext(), userList)
                    binding.recyclerView.adapter = usersAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}