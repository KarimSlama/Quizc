package com.example.quizapp.repository

import com.example.quizapp.Constants.API_KEY
import com.example.quizapp.api.ApiService
import com.example.quizapp.data.QuizData
import com.example.quizapp.data.QuizDataItem
import retrofit2.Call

class QuizRepository {

    suspend fun getQuizzes(): List<QuizDataItem> {
        return ApiService.quizRetrofit.getQuizzes(API_KEY)
    }//end getQuizzes()

}//end class