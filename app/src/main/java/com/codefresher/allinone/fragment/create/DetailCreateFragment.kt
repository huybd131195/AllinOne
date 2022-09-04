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
import com.codefresher.allinone.databinding.FragmentDetailCreateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*


class DetailCreateFragment : Fragment() {

    private var _binding: FragmentDetailCreateBinding? = null
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
        _binding = FragmentDetailCreateBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAndSetData()
        registerActivityForResult()
        binding.btnUpdateRecipe.setOnClickListener {
            uploadPhoto()
        }

        binding.imgRecipe.setOnClickListener {
            chooseImage()
        }

    }

    fun getAndSetData() {
        val title = arguments?.getString("title").toString()
        val ingredients = arguments?.getString("ingredients").toString()
        val directions = arguments?.getString("directions").toString()
        val imageUrl = arguments?.getString("url").toString()

        binding.edtTitle.setText(title)
        binding.edtIngredients.setText(ingredients)
        binding.edtDirections.setText(directions)
        Picasso.get().load(imageUrl).into(binding.imgRecipe)
    }

    fun chooseImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLauncher.launch(intent)

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

    fun updateData(url:String,imgName:String) {
        val recipeId = arguments?.getString("id").toString()
        val updateTitle = binding.edtTitle.text.toString()
        val updateIngredients = binding.edtIngredients.text.toString()
        val updateDirections = binding.edtDirections.text.toString()


        val recipeMap = mutableMapOf<String, Any>()
        recipeMap["id"] = recipeId
        recipeMap["title"] = updateTitle
        recipeMap["ingredients"] = updateIngredients
        recipeMap["directions"] = updateDirections
        recipeMap["url"] = url
        recipeMap["imgName"] = imgName

        myReference.child(recipeId).updateChildren(recipeMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                findNavController().popBackStack()

            }

        }

    }

    fun uploadPhoto() {
        binding.btnUpdateRecipe.isClickable = false

        //UUID

        val imageName = arguments?.getString("imgName").toString()
        val imageReference = storageReference.child("images").child(imageName)

        imageUri?.let { uri ->

            imageReference.putFile(uri).addOnSuccessListener {

                Toast.makeText(requireContext(), "Image uploaded", Toast.LENGTH_LONG).show()

                //download url
                val myUploadedImageReference = storageReference.child("images").child(imageName)

                myUploadedImageReference.downloadUrl.addOnSuccessListener { url ->

                    val imageURL = url.toString()

                    updateData(imageURL, imageName)

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