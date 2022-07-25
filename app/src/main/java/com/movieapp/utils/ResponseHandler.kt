package com.movieapp.utils

import java.net.UnknownHostException

class ResponseHandler {
    fun <T> handleException(e: Exception) : NetworkResponse<T> {
        return when(e) {
            is UnknownHostException -> NetworkResponse.Error("Sadadasa")
            else -> NetworkResponse.Error(e.toString())
        }
    }

    fun <T> handleSuccess(data: T? = null) : NetworkResponse<T> {
        return NetworkResponse.Success(data)
    }
}