package com.example.paginglibrary.network.data.base

import com.example.paginglibrary.network.data.State
import com.google.gson.annotations.SerializedName

open class ListResponse<T>(
    val type: State,
    @SerializedName("articles")
    val data: ArrayList<T>?,
    val error: Throwable?
) {
    companion object {
        fun <T> loading(): ListResponse<T> =
            ListResponse(State.LOADING, null, null)

        fun <T> success(data: ArrayList<T>): ListResponse<T> =
            ListResponse(State.SUCCESS, data, null)

        fun <T> error(throwable: Throwable): ListResponse<T> =
            ListResponse(State.ERROR, null, throwable)
    }
}