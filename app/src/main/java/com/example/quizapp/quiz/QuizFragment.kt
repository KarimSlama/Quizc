package com.example.quizapp.quiz

import android.content.SharedPreferences
import android.media.session.MediaSession.QueueItem
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.quizapp.Constants
import com.example.quizapp.data.QuizData
import com.example.quizapp.data.QuizDataItem
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.repository.QuizRepository
import com.example.quizapp.signup.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.Collections

class QuizFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var quizCurrentPosition: Int = 0
//    private lateinit var quizList: MutableList<QuizDataItem>

//    private val quizList: MutableList<QuizDataItem> = mutableListOf()

    private val quizList = mutableListOf<QuizDataItem>()

    private lateinit var binding: FragmentQuizBinding

    //    private val viewModel: QuizViewModel by lazy { ViewModelProvider(this)[QuizViewModel::class.java] }
    private lateinit var quizViewModel: QuizViewModel

    private var selectedAnswers: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val name = sharedPreferences.getString(Constants.NAME_KEY, "no name here")
        binding.nameTxt.text = name

        binding.progressLoading.visibility = View.VISIBLE
        val repository = QuizRepository()
        val quizViewModelFactory = QuizViewModelFactory(repository)

        quizViewModel = ViewModelProvider(this, quizViewModelFactory)[QuizViewModel::class.java]

        currentQuiz()

        binding.gotItBtn.setOnClickListener {
            if (quizCurrentPosition <= 10) {
                selectedAnswers = if (binding.choiceOneRadio.isChecked)
                    binding.choiceOneRadio.text as String?
                else (if (binding.choiceTwoRadio.isChecked)
                    binding.choiceTwoRadio.text
                else if (binding.choiceThreeRadio.isChecked)
                    binding.choiceThreeRadio.text
                else binding.choiceFourRadio.text) as String?

//                quizViewModel.quiz.observe(viewLifecycleOwner) {
//                    if (selectedAnswers == it[quizCurrentPosition].correct_answer)
//                        quizViewModel.onCorrect()
//                }

                quizCurrentPosition++
                currentQuiz()

            } else {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToScoreFragment(
                        quizViewModel.score.value ?: 0
                    )
                )
                quizViewModel.quizComplete()
            }//end else

        }//end setOnClickListener

        if (quizList[quizCurrentPosition].correct_answer == (selectedAnswers)!!) {
            quizViewModel.onCorrect()
        }

        return binding.root
    }//end onCreate()

    private fun currentQuiz() {
        quizViewModel.quiz.observe(viewLifecycleOwner) { quizItems ->
            binding.questionTitle.text = quizItems[quizCurrentPosition].question
            binding.choiceOneRadio.text = quizItems[quizCurrentPosition].answers.answer_a
            binding.choiceTwoRadio.text = quizItems[quizCurrentPosition].answers.answer_b
            binding.choiceThreeRadio.text = quizItems[quizCurrentPosition].answers.answer_c
            binding.choiceFourRadio.text = quizItems[quizCurrentPosition].answers.answer_d
            binding.progressLoading.visibility = View.GONE
            for (quiz in quizItems) {
                quizList.add(quiz)
            }//end for()
        }//end observe()
    }//end currentQuiz()

}//end class