package com.codefresher.allinone.fragment.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.FragmentAddCreateBinding
import com.codefresher.allinone.databinding.FragmentCreateBinding
import com.codefresher.allinone.model.Users
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class AddCreateFragment : Fragment() {

    private var _binding: FragmentAddCreateBinding? = null
    private val binding get() = _binding!!
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCreateBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerActivityForResult()

        binding.btnAddUser.setOnClickListener {
            addUserToDatabase()
        }

        binding.imgChose.setOnClickListener {
            chooseImage()
        }

    }

    fun chooseImage() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

    fun registerActivityForResult() {
        activityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { result ->

                    val resultCode = result.resultCode
                    val imageData = result.data

                    if (resultCode == Activity.RESULT_OK && imageData != null) {
                        imageUri = imageData.data

                        imageUri?.let {
                            Picasso.get().load(it).into(binding.imgChose)
                        }
                    }
                })
    }


    private fun addUserToDatabase() {
        val name: String = binding.edtName.text.toString()
        val age: String = binding.edtAge.text.toString()
        val email: String = binding.edtEmail.text.toString()

        val id: String = myReference.push().key.toString()

        val user = Users(id, name, age, email)

        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "The new user has been added to the database",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().popBackStack()

            } else {
                Toast.makeText(
                    context,
                    task.exception.toString(),
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}