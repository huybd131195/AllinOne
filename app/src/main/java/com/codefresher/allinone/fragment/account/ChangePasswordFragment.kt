package com.codefresher.allinone.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentAccountBinding
import com.codefresher.allinone.databinding.FragmentChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }
        return binding.root
    }

    private fun changePassword() {

        if (binding.currentPassword.text.isNotEmpty() &&
            binding.newPassword.text.isNotEmpty() &&
            binding.confirmPassword.text.isNotEmpty()
        ) {

            if (binding.newPassword.text.toString() == binding.confirmPassword.text.toString()
            ) {

                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, binding.currentPassword.text.toString())

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Re-Authentication success.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                user.updatePassword(binding.newPassword.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Password changed successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                        findNavController().popBackStack()
                                    }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Re-Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }

            } else {
                Toast.makeText(context, "Password mismatching.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
        }
    }

}