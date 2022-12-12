package com.example.quizapp.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityForgotPasswordBinding
import com.example.quizapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrowIcon.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }//end setOnClickListener

        binding.submitBtn.setOnClickListener {
            resetPassword()
        }//end setOnClickListener
    }//end onCreate()

    private fun resetPassword() {
        val email = binding.restEmailEdt.text.toString().trim()
        if (email.isEmpty()) {
            binding.restEmailEdt.error = "email is required"
            binding.restEmailEdt.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.restEmailEdt.error = "please provide valid email"
            binding.restEmailEdt.requestFocus()
            return
        }//end else if()

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "check your email to reset password", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "something went wrong! try again later", Toast.LENGTH_LONG)
                    .show()
            }//end else
        }//end lambda fun.
    }//end resetPassword()

}//end class