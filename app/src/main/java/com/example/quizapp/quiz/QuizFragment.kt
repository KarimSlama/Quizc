package com.example.quizapp.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.repository.QuizRepository

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding

    //    private val viewModel: QuizViewModel by lazy { ViewModelProvider(this)[QuizViewModel::class.java] }
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)

        val repository = QuizRepository()
        val quizViewModelFactory = QuizViewModelFactory(repository)
        quizViewModel = ViewModelProvider(this, quizViewModelFactory)[QuizViewModel::class.java]

        quizViewModel.getQuizzes()

        quizViewModel.status.observe(viewLifecycleOwner, Observer {
            binding.questionTitle.text = it
        })
        return binding.root
    }//end onCreate()
}//end clas