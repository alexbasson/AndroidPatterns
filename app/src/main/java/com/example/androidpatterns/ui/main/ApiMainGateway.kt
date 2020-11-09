package com.example.androidpatterns.ui.main

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ApiMainGateway: MainGateway {
    override suspend fun requestMessage(shouldSucceed: Boolean): Result<String> {
        return withContext(Dispatchers.IO) {
            delay(2000)
            if (shouldSucceed) {
                Result.Success("a message from the gateway")
            } else {
                Result.Error(Exception())
            }
        }
    }
}
