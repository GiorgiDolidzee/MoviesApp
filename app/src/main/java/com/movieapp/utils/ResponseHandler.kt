package com.movieapp.utils

import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ResponseHandler {
    fun <T> handleException(e: Exception) : Resource<T> {
        return when(e) {
            is UnknownHostException -> Resource.Error("Sadadasa")
            else -> Resource.Error(e.toString())
        }
    }

    fun <T> handleSuccess(data: T? = null) : Resource<T> {
        return Resource.Success(data)
    }
}