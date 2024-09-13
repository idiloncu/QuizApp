package com.example.quizapp.data

import retrofit2.Call
import retrofit2.http.GET

interface RestCountriesApi {

    @GET("api/v0.1/countries/flag/images")
    fun getAllCountries():Call<CountryResponse>
}