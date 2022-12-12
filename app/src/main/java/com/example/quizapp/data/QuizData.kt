package com.example.quizapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizData(
    val id: Int,
    val question: String,
    val description: String,
    val answers: List<String>,
    val multiple_correct_answers: Boolean,
    val correct_answer: String,
    val explanation: String,
    val tip: String,
    val tags: String,
    val category: String,
    val difficulty: String
) : Parcelable