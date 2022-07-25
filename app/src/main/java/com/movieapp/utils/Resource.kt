package com.movieapp.utils

sealed class Resource<T>(val data: T?=null) {
    class DataIsFilled<T>(data: T?=null) : Resource<T>(data)
//    class Error<T>(errorMessage: String, data: T?=null) : Resource<T>(data, errorMessage)
    class Loading<T>: Resource<T>()
}