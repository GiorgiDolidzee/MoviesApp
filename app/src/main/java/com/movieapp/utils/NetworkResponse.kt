package com.movieapp.utils


sealed class NetworkResponse<T>(val data: T?=null, val errorMessage: String?=null) {
    class Success<T>(data: T?=null) : NetworkResponse<T>(data)
    class Error<T>(errorMessage: String, data: T?=null) : NetworkResponse<T>(data, errorMessage)
}