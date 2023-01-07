package com.example.quizapp.score

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private lateinit var binding: FragmentScoreBinding
    private lateinit var scoreViewModel: ScoreViewModel
    private lateinit var scoreViewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(layoutInflater)

        scoreViewModelFactory =
            ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)

        scoreViewModel = ViewModelProvider(this, scoreViewModelFactory)[ScoreViewModel::class.java]

        scoreViewModel.score.observe(viewLifecycleOwner) {
            binding.scoreTxt.text = "The Score is: $it"
        }//end observe()

        return binding.root
    }//end onCreateView()
}//end class