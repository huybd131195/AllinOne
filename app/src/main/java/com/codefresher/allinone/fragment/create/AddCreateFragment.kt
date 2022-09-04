package com.codefresher.allinone.fragment.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codefresher.allinone.databinding.FragmentAddCreateBinding
import com.codefresher.allinone.model.Recipe
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*


class AddCreateFragment : Fragment() {

    private var _binding: FragmentAddCreateBinding? = null
    private val binding get() = _binding!!

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("Recipes")

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

        binding.btnAddRecipe.setOnClickListener {
            uploadPhoto()
        }

        binding.imgRecipe.setOnClickListener {
            chooseImage()
        }

    }

    fun addRecipeToDatabase(url: String,imgName : String) {
        val title: String = binding.edtTitle.text.toString()
        val ingredients: String = binding.edtIngredients.text.toString()
        val directions: String = binding.edtDirections.text.toString()

        val id = myReference.push().key.toString()

        val recipe = Recipe(id, title, ingredients, directions,url,imgName)

        myReference.child(id).setValue(recipe).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "New recipe created",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else {

                Toast.makeText(
                    context, task.exception.toString(), Toast.LENGTH_SHORT
                ).show()

            }
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
                            Picasso.get().load(it).into(binding.imgRecipe)
                        }
                    }
                })
    }

    fun uploadPhoto() {
        binding.btnAddRecipe.isClickable = false
        binding.progressBar.visibility = View.VISIBLE

        //UUID

        val imageName = UUID.randomUUID().toString()
        val imageReference = storageReference.child("images").child(imageName)

        imageUri?.let { uri ->

            imageReference.putFile(uri).addOnSuccessListener {

                Toast.makeText(requireContext(), "Image uploaded", Toast.LENGTH_LONG).show()

                //download url
                val myUploadedImageReference = storageReference.child("images").child(imageName)

                myUploadedImageReference.downloadUrl.addOnSuccessListener { url ->

                    val imageURL = url.toString()

                    addRecipeToDatabase(imageURL,imageName)

                }

            }.addOnFailureListener {

                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
