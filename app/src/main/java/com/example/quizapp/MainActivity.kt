package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.data.CountryResponse
import com.example.quizapp.data.RestCountriesApi
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://countriesnow.space/"
    private val TAG:String="CHECK_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        lifecycleScope.launch {
            loadData()
        }

        with(binding) {
            btnStart.setOnClickListener {
                if (etName.text!!.isEmpty() || etName.text.toString().trim().isEmpty() ) {
                    Toast.makeText(this@MainActivity, "Please give a name", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val intent = Intent(this@MainActivity, QuizQuestionsActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, etName.text.toString())
                    startActivity(intent)
                }
            }
        }
        lifecycleScope.launch {
            loadData()
        }
    }
    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestCountriesApi::class.java)

        retrofit.getAllCountries().enqueue(object : Callback<CountryResponse>{
            override fun onResponse(call: Call<CountryResponse>,response: Response<CountryResponse>) {
                val countries = response.body()?.countries
               if(response.isSuccessful){
                   val countryList = response.body()?.countries
                   val flagList = response.body()?.flag
                   response.body()?.let {
                       for(country in it.countries){
                           Log.i("country", "Name: ${country.name}, Flag: ${country.flag}")
                       }
                   }
               }
                else{
                   Log.e("API Error", "Response Code: ${response.code()}")
               }
            }

            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                Log.e(TAG, "API Failure: ${t.message}", t)
            }
        })
    }
}