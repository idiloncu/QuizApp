package com.example.quizapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private lateinit var binding: ActivityQuizQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val questionsList = Constants.getQuestions()
        Log.i("Questions Size", "${questionsList.size}")

        for (i in questionsList) {
            Log.e("Questions", i.questions)
        }
        var currentPosition = 1
        val question:Question=questionsList[currentPosition - 1]
        binding.progressBar?.progress = currentPosition
        binding.tvProgress?.text = "$currentPosition/${binding.progressBar?.max}"
        binding.tvQuestion?.text = question.questions
        binding.tvOptionOne?.text=question.optionOne
        binding.tvOptionTwo?.text=question.optionTwo
        binding.tvOptionThree?.text=question.optionThree
        binding.tvOptionFour?.text=question.optionFour
        binding.ivImage?.setImageResource(question.image)

    }

    override fun onClick(p0: View?) {

    }
}