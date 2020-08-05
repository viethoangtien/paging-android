package com.example.paginglibrary.network.data

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("articles") val news: List<News>
)