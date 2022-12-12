package com.example.quizapp.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.quizapp.MainActivity
import com.example.quizapp.databinding.ActivitySignUpBinding
import com.example.quizapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            registerUser()
        }//end setOnClickListener

        binding.alreadyLoggedTxt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }//end setOnClickListener

    }//end onCreate()

    private fun registerUser() {
        val name = binding.edtName.text?.trim().toString()
        val email = binding.edtEmail.text?.trim().toString()
        val phone = binding.edtPhone.text?.trim().toString()
        val password = binding.edtPassword.text?.trim().toString()

        if (name.isEmpty()) {
            binding.edtName.error = "name is required"
            binding.edtName.requestFocus()
            return
        } else if (phone.isEmpty()) {
            binding.edtPhone.error = "phone is required"
            binding.edtPhone.requestFocus()
            return
        } else if (email.isEmpty()) {
            binding.edtEmail.error = "email is required"
            binding.edtEmail.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "please provide valid email"
            binding.edtEmail.requestFocus()
            return
        } else if (password.isEmpty()) {
            binding.edtPassword.error = "password is required"
            binding.edtPassword.requestFocus()
            return
        } else if (password.length < 6) {
            binding.edtPassword.error = "min password length should be 6 character or more"
            binding.edtPassword.requestFocus()
            return
        }//end else if()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(name, email, phone)
                    //with this line i can save the current user data on real database with the current user id
                    FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "user has been completed logged in",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }//end if()
                            else Toast.makeText(this, "failed registerd", Toast.LENGTH_LONG).show()
                        }//end addOnCompleteListener
                }//end if()
                else Toast.makeText(this, "failed registerd", Toast.LENGTH_LONG).show()
            }//end addOnCompleteListener

    }//end registerUser()

}//end class