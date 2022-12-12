package com.example.quizapp.login

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.quizapp.MainActivity
import com.example.quizapp.databinding.ActivityLoginBinding
import com.example.quizapp.forgot_password.ForgotPasswordActivity
import com.example.quizapp.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrowIcon.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }//end setOnClickListener

        binding.loginBtn.setOnClickListener {
            userLogin()
        }//end setOnClickListener

        binding.forgetPasswordLinkTxt.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }//end setOnClickListener
    }//end onCreate()

    private fun userLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.edtEmail.error = "email is required"
            binding.edtEmail.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "please enter a valid email"
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

        binding.progressBar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Failed logging by the user", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }//end if()
        }//end lambda fun.

    }//end userLogin()

}//end class