package com.example.quizapp.data

import com.google.gson.annotations.SerializedName

data class CountryName(
    @SerializedName("name")
    val name:String,

    @SerializedName("flag")
    val flag:String
)
