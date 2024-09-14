package com.example.quizapp.model

data class Question(
    val id: Int,
    val questions: String,
    val flag: String,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)
