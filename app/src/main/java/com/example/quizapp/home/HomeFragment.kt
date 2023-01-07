package com.example.quizapp.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.quizapp.Constants
import com.example.quizapp.Constants.EMAIL_KEY
import com.example.quizapp.Constants.NAME_KEY
import com.example.quizapp.Constants.PHONE_KEY
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.notification.cancelNotification
import com.example.quizapp.notification.sendNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var user: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.someTipsIv.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSomeTipsFragment())
        }//end setOnClickListener

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        user = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("users")
        val userId = user.uid
        reference.child(userId).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value
                val email = it.child("email").value
                val phone = it.child("phone").value

                binding.txtName.text = name.toString()

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                editor = sharedPreferences.edit()
                editor.putString(NAME_KEY, name.toString())
                editor.putString(EMAIL_KEY, email.toString())
                editor.putString(PHONE_KEY, phone.toString())
                editor.apply()

            } else Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed Listener", Toast.LENGTH_LONG).show()
        }

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

            //push notification maybe after 10 minutes

            val count = object : CountDownTimer(600000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    createChannel(
                        getString(R.string.quiz_notification_channel_id),
                        getString(R.string.quiz_notification_channel_name)
                    )

                    val notificationManager = ContextCompat.getSystemService(
                        context!!,
                        NotificationManager::class.java
                    ) as NotificationManager
                    notificationManager.cancelNotification()
                }//end onTick()

                override fun onFinish() {

                    val notificationManager =
                        ContextCompat.getSystemService(
                            context!!,
                            NotificationManager::class.java
                        ) as NotificationManager
                    notificationManager.sendNotification(
                        context!!.getString(R.string.quiz_ready),
                        context!!
                    )
                }//end onFinish()
            }//end CountDownTimer()

            count.start()

        }//end setPositiveButton()

        builder.setNegativeButton("Denny") { dialog, id ->
            Toast.makeText(
                context,
                "Till you be ready make your energy high by reading this:...",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToReadingFragment())
        }//end setNegativeButton()
        builder.show()
    }//end onAlertDialog()

    fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                getString(R.string.quiz_notification_channel_description)
            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(notificationChannel)
        }//end if()
    }//end createChannel()

}//end class