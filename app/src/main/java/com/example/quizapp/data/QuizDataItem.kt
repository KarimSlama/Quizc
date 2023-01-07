package com.example.quizapp.data

data class QuizDataItem(
    val answers: Answers,
    val category: String,
    val correct_answer: String?,
    val correct_answers: CorrectAnswers,
    val description: String?,
    val difficulty: String,
    val explanation: String?,
    val id: Long,
    val multiple_correct_answers: String,
    val question: String,
    val tags: List<Tag>,
    val tip: String?
)