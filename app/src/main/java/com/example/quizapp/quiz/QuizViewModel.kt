package com.example.quizapp.quiz

import android.util.Log
import androidx.lifecycle.*
import com.example.quizapp.data.QuizData
import com.example.quizapp.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository,
) : ViewModel() {

    private val _quiz = MutableLiveData<QuizData>()
    val quiz: LiveData<QuizData>
        get() = _quiz

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    fun getQuizzes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _quiz.value = repository.getQuizzes()[0]
                _status.value = "First Question is ${_quiz.value!!.difficulty}"
            } catch (e: java.lang.Exception) {
                Log.d("QuizViewModel", e.message.toString())
            }
        }//end launch()
    }//end getQuizzes()
}//end class

class QuizViewModelFactory(private val repository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(repository) as T
    }//end create()
}//end class