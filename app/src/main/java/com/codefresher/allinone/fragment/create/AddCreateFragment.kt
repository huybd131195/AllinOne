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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*


class AddCreateFragment : Fragment() {

    private var _binding: FragmentAddCreateBinding? = null
    private val binding get() = _binding!!
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri: Uri? = null

    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference: StorageReference = firebaseStorage.reference

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
            uploadPhoto()
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


    private fun addUserToDatabase(url: String) {
        val name: String = binding.edtName.text.toString()
        val age: String = binding.edtAge.text.toString()
        val email: String = binding.edtEmail.text.toString()

        val id: String = myReference.push().key.toString()

        val user = Users(id, name, age, email, url)

        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "The new user has been added to the database",
                    Toast.LENGTH_LONG
                ).show()
                binding.btnAddUser.isClickable = true
                binding.progressBar.visibility = View.INVISIBLE
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

    fun uploadPhoto() {
        binding.btnAddUser.isClickable = false
        binding.progressBar.visibility = View.VISIBLE

        //UUID

        val imageName = UUID.randomUUID().toString()
        val imageReference = storageReference.child("images").child(imageName)

        imageUri?.let { uri ->

            imageReference.putFile(uri).addOnSuccessListener {

                    Toast.makeText(requireContext(),"Image uploaded", Toast.LENGTH_LONG).show()

                //download url
                 val myUploadedImageReference = storageReference.child("images").child(imageName)

                myUploadedImageReference.downloadUrl.addOnSuccessListener { url ->

                    val imageURL = url.toString()

                    addUserToDatabase(imageURL)

                }

            }.addOnFailureListener {

                Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
