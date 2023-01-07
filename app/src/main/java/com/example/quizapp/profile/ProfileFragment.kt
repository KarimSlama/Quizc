package com.example.quizapp.profile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import com.example.quizapp.Constants.EMAIL_KEY
import com.example.quizapp.Constants.NAME_KEY
import com.example.quizapp.Constants.PHONE_KEY
import com.example.quizapp.databinding.FragmentProfileBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var packageManager: PackageManager
    private lateinit var storageReference: StorageReference
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageView: ImageView
//    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)


        packageManager = activity?.packageManager!!

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val name = sharedPreferences.getString(NAME_KEY, "no name")
        val email = sharedPreferences.getString(EMAIL_KEY, "no email")
        val phone = sharedPreferences.getString(PHONE_KEY, "no phone")

        binding.name.text = name
        binding.phone.text = phone
        binding.email.text = email

        imageView = binding.userIv

        val formatter = SimpleDateFormat("yyy-MM-dd-HH-mm-ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

//        storageReference.putFile(imageUri).addOnSuccessListener {
//            binding.userIv.setImageURI(imageUri)
//            Toast.makeText(context, "successful upload", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener {
//            Toast.makeText(context, "Failed upload", Toast.LENGTH_LONG).show()
//        }

        binding.logoutBtn.setOnClickListener {

        }//end setOnClickListener

        binding.changeImageIc.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        return binding.root
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                imageView.setImageURI(data?.data)
            }//end if()
        }//end registerForActivityResult()

}
