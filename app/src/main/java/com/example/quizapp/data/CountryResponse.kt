package com.example.quizapp.data

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("data")
    val countries: List<CountryName>,

    @SerializedName("flag")
    val flag:String
)