package com.example.quizapp

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0

    private lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()
        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.btnSubmit?.text = "FINISH"
        } else {
            binding.btnSubmit?.text = "SUBMIT"
        }

        binding.tvOptionOne?.setOnClickListener(this)
        binding.tvOptionTwo?.setOnClickListener(this)
        binding.tvOptionThree?.setOnClickListener(this)
        binding.tvOptionFour?.setOnClickListener(this)
        binding.btnSubmit?.setOnClickListener(this)

        setQuestion()

    }

    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        binding.progressBar?.progress = mCurrentPosition
        binding.tvProgress?.text = "$mCurrentPosition/${binding.progressBar?.max}"
        binding.tvQuestion?.text = question.questions
        binding.tvOptionOne?.text = question.optionOne
        binding.tvOptionTwo?.text = question.optionTwo
        binding.tvOptionThree?.text = question.optionThree
        binding.tvOptionFour?.text = question.optionFour
        binding.ivImage?.setImageResource(question.image)
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        binding.tvOptionOne?.let {
            options.add(0, it)
        }
        binding.tvOptionTwo?.let {
            options.add(1, it)
        }
        binding.tvOptionThree?.let {
            options.add(2, it)
        }
        binding.tvOptionFour?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(R.color.lightGray)
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(R.color.gray)
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                binding.tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tv_option_two -> {
                binding.tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                binding.tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.tv_option_four -> {
                binding.tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }

                        else -> {
                            val intent = Intent(this, FinishActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.btnSubmit?.text = "FINISH"
                    } else {
                        binding.btnSubmit?.text = "NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = (ContextCompat.getDrawable(this, drawableView))
            }

            2 -> {
                binding.tvOptionTwo.background = (ContextCompat.getDrawable(this, drawableView))
            }

            3 -> {
                binding.tvOptionThree.background = (ContextCompat.getDrawable(this, drawableView))
            }

            4 -> {
                binding.tvOptionFour.background = (ContextCompat.getDrawable(this, drawableView))
            }
        }
    }
}
