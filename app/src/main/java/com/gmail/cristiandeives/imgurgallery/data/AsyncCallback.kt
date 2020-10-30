package com.gmail.cristiandeives.imgurgallery.data

interface AsyncCallback<T> {
    fun onSuccess(response: T)
    fun onError(ex: Exception?)
}