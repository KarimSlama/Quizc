package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mAuth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            if (mAuth.currentUser != null)
                startActivity(Intent(this, MainActivity::class.java))
            else startActivity(Intent(this, SignUpActivity::class.java))
        }, 4000)


    }//end onCreate()
}//end class