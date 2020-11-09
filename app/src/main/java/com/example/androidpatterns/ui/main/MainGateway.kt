package com.example.androidpatterns.ui.main

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

interface MainGateway {
    suspend fun requestMessage(shouldSucceed: Boolean): Result<String>
}
