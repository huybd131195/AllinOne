package com.codefresher.allinone.fragment.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.codefresher.allinone.LogInActivity
import com.codefresher.allinone.MainActivity
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentAccountBinding
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.tvNameAccount.text = currentUser?.displayName
        binding.tvEmail.text = currentUser?.email

        if (currentUser?.photoUrl != null){
            Glide.with(this).load(currentUser?.photoUrl).into(binding.imgAvatar)
        }

        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            val intent: Intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.changePassword.setOnClickListener{

            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}