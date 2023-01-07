package com.example.quizapp.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModel(finalScore: Int) : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        _score.value = finalScore
    }//end init

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }//end onPlayAgain()

    fun onPlayAgainCompleted() {
        _eventPlayAgain.value = false
    }//end onPlayAgainCompleted()

}//end class

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }//end if()
        throw IllegalArgumentException("Unknown ViewModel class")
    }//end create()
} //end class