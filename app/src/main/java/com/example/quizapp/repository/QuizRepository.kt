package com.example.quizapp.repository

import com.example.quizapp.Constants
import com.example.quizapp.Constants.API_KEY
import com.example.quizapp.Constants.BASE_URL
import com.example.quizapp.api.ApiService
import com.example.quizapp.data.QuizData

class QuizRepository {

    suspend fun getQuizzes(): List<QuizData> {
        return ApiService.quizRetrofit.getQuizzes(API_KEY)
    }//end getQuizzes()

}//end class