package com.example.quizapp.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.databinding.FragmentStatisticsBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.someTipsIv.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSomeTipsFragment())
        }//end setOnClickListener

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.yesBtn.setOnClickListener {
            binding.motivationTxt.visibility = View.VISIBLE
            startAnimation()
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment())
            }, 3000)
        }//end setOnClickListener

        binding.noBtn.setOnClickListener {
            onAlertDialog()
        }//end setOnClickListener
        return binding.root
    }//end onCreate()

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.animation)
        binding.motivationTxt.animation = animation
    }//end startAnimation()

    private fun onAlertDialog() {
        val builder = AlertDialog.Builder(view?.context!!)
        builder.setTitle("Quizc")
        builder.setIcon(R.drawable.logo)
        builder.setMessage("Would you like to remind you after a little bit time?")
        builder.setPositiveButton(
            "Sure"
        ) { dialog, id ->
            Toast.makeText(context, "We will remind you later", Toast.LENGTH_LONG).show()
        }//end setPositiveButton()

        builder.setNegativeButton("Denny") { dialog, id ->
            Toast.makeText(
                context,
                "Okay take care of your self and let us see you soon",
                Toast.LENGTH_LONG
            ).show()
        }//end setNegativeButton()
        builder.show()
    }//end onAlertDialog()

}//end class