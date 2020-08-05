package com.example.paginglibrary.network.data

import com.google.gson.annotations.SerializedName

data class News(
    val title: String,
    @SerializedName("urlToImage") val image: String
)