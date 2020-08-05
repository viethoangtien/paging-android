package com.example.paginglibrary.network.data.base

import com.example.paginglibrary.network.data.News
import com.example.paginglibrary.network.data.State

class ObjectResponse<T>(
    var state: State,
    var data: T? = null,
    var throwable: Throwable? = null
) {

    companion object {
        fun <T> loading(): ObjectResponse<T> = ObjectResponse(State.LOADING, null, null)
        fun <T> success(data: T): ObjectResponse<T> = ObjectResponse(State.SUCCESS, data, null)
        fun <T> error(throwable: Throwable): ObjectResponse<T> = ObjectResponse(State.ERROR, null, throwable)
    }
}