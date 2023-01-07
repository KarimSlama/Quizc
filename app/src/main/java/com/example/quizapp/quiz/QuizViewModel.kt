package com.example.quizapp.quiz

import android.util.Log
import androidx.lifecycle.*
import com.example.quizapp.data.QuizData
import com.example.quizapp.data.QuizDataItem
import com.example.quizapp.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections

class QuizViewModel(
    private val repository: QuizRepository,
) : ViewModel() {

    private val _quiz = MutableLiveData<List<QuizDataItem>>()
    val quiz: LiveData<List<QuizDataItem>>
        get() = _quiz


    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventQuizFinished = MutableLiveData<Boolean>()
    val eventQuizFinished: LiveData<Boolean>
        get() = _eventQuizFinished

    init {
        getQuizzes()
        onCorrect()
        _eventQuizFinished.value = false
    }//end init

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        _eventQuizFinished.value = true
    }//end onCorrect()

    fun quizComplete() {
        _eventQuizFinished.value = false
    }//end quizComplete()

    private fun getQuizzes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _quiz.postValue(repository.getQuizzes())
            } catch (e: java.lang.Exception) {
                Log.d("QuizViewModel", e.message.toString())
            }//end getQuizzes()
        }//end launch()
    }//end getQuizzes()
}//end class

class QuizViewModelFactory(private val repository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(repository) as T
    }//end create()
}//end class