package com.movieapp.utils

sealed class Resource<T>(val data: T?=null) {
    class DataIsFilled<T>(data: T?=null) : Resource<T>(data)
    class Loading<T>: Resource<T>()
}