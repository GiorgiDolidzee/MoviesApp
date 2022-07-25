package com.movieapp.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException

class ResponseHandler {
    fun <T> handleException(e: Exception) : NetworkResponse<T> {
        return when(e) {
            is SocketTimeoutException -> NetworkResponse.Error("Check your internet connection")
            is HttpException -> NetworkResponse.Error("Unexcepted network error, try again")
            else -> NetworkResponse.Error("Unexcepted error, try again")
        }
    }

    fun <T> handleSuccess(data: T? = null) : NetworkResponse<T> {
        return NetworkResponse.Success(data)
    }
}