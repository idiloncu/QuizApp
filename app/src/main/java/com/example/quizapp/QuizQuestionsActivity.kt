package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.decode.SvgDecoder
import coil.load
import com.example.quizapp.data.CountryName
import com.example.quizapp.data.CountryResponse
import com.example.quizapp.data.RestCountriesApi
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding
import com.example.quizapp.model.Question
import com.example.quizapp.view.FinishActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private lateinit var binding: ActivityQuizQuestionsBinding
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCountries()
        setupClickListeners()
    }

    private fun fetchCountries() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://countriesnow.space/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RestCountriesApi::class.java)
        api.getAllCountries().enqueue(object : Callback<CountryResponse> {
            override fun onResponse(
                call: Call<CountryResponse>,
                response: Response<CountryResponse>
            ) {
                val countries = response.body()?.countries
                countries?.let {
                    prepareQuizQuestions(it)
                    setQuestion()
                }
            }

            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                Log.e("API Error", t.message.toString())
            }
        })
    }

    private fun prepareQuizQuestions(countries: List<CountryName>) {
        mQuestionsList = ArrayList()
        val selectedCountries = countries.shuffled().take(10) // Take 10 random countries

        for (country in selectedCountries) {
            val wrongOptions = countries.filter { it.name != country.name }.shuffled().take(3)

            val options = listOf(
                country.name,
                wrongOptions[0].name,
                wrongOptions[1].name,
                wrongOptions[2].name
            ).shuffled()

            val correctAnswerIndex = options.indexOf(country.name) + 1

            val question = Question(
                id = 1,
                flag = country.flag,
                questions = "What country does this flag belong to?",
                optionOne = options[0],
                optionTwo = options[1],
                optionThree = options[2],
                optionFour = options[3],
                correctAnswer = correctAnswerIndex
            )
            mQuestionsList?.add(question)
        }
    }

    private fun setQuestion() {
        if (mQuestionsList != null && mQuestionsList!!.isNotEmpty()) {
            val question = mQuestionsList!![mCurrentPosition - 1]
            defaultOptionsView()
            binding.tvQuestion.text = question.questions
            binding.tvOptionOne.text = question.optionOne
            binding.tvOptionTwo.text = question.optionTwo
            binding.tvOptionThree.text = question.optionThree
            binding.tvOptionFour.text = question.optionFour

            val flagUrl: String = question.flag
            binding.ivImage.load(flagUrl) {
                crossfade(true)
                error(R.drawable.error_image) // Optional error image
                decoderFactory(SvgDecoder.Factory())
                listener(
                    onSuccess = { _, _ -> Log.d("Coil", "Image loaded successfully") },
                    onError = { _, result ->
                        Log.e(
                            "Coil",
                            "Image load failed: ${result.throwable.message}"
                        )
                    }
                )
                size(204, 120)
                build()
            }
            binding.progressBar.progress = mCurrentPosition - 1
            binding.progressBar.max = mQuestionsList!!.size - 1


        }
    }

    private fun defaultOptionsView() {
        val options = arrayListOf<TextView>(
            binding.tvOptionOne,
            binding.tvOptionTwo,
            binding.tvOptionThree,
            binding.tvOptionFour
        )

        for (option in options) {
            option.setTextColor(resources.getColor(R.color.lightGray))
            option.background = resources.getDrawable(R.drawable.default_option_border_bg)
        }
    }

    private fun setupClickListeners() {
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> selectedOptionView(binding.tvOptionOne, 1)
            R.id.tv_option_two -> selectedOptionView(binding.tvOptionTwo, 2)
            R.id.tv_option_three -> selectedOptionView(binding.tvOptionThree, 3)
            R.id.tv_option_four -> selectedOptionView(binding.tvOptionFour, 4)
            R.id.btn_submit -> handleSubmit()
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(resources.getColor(R.color.gray))
        tv.background = resources.getDrawable(R.drawable.selected_option_border_bg)
    }

    private fun handleSubmit() {
        if (mSelectedOptionPosition == 0) {
            mCurrentPosition++
            if (mCurrentPosition <= mQuestionsList!!.size) {
                setQuestion()
                binding.tvProgress.text = "$mCurrentPosition / ${mQuestionsList!!.size}"
                binding.btnSubmit.text = "NEXT QUESTION"

            }
        } else {
            val question = mQuestionsList!![mCurrentPosition - 1]
            if (question.correctAnswer != mSelectedOptionPosition) {
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                mCorrectAnswers++
            }
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)


            if (mCurrentPosition == mQuestionsList!!.size) {
                binding.btnSubmit.text = "FINISH"

            }

            mSelectedOptionPosition = 0
        }
        binding.btnSubmit.setOnClickListener {
            if (mCurrentPosition == mQuestionsList!!.size) {
                val intent = Intent(this@QuizQuestionsActivity, FinishActivity::class.java)
                intent.putExtra("CORRECT_ANSWERS", mCorrectAnswers)
                intent.putExtra("TOTAL_QUESTIONS", mQuestionsList!!.size)
                startActivity(intent)
                finish()
            } else {
                handleSubmit()
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background = resources.getDrawable(drawableView)
            2 -> binding.tvOptionTwo.background = resources.getDrawable(drawableView)
            3 -> binding.tvOptionThree.background = resources.getDrawable(drawableView)
            4 -> binding.tvOptionFour.background = resources.getDrawable(drawableView)
        }
    }
}
