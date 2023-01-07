package com.example.quizapp.topic_reading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentReadingBinding

class ReadingFragment : Fragment() {

    private lateinit var binding: FragmentReadingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReadingBinding.inflate(layoutInflater)



        return binding.root
    }
}