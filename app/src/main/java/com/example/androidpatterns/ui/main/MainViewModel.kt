package com.example.androidpatterns.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

enum class GatewayMessageLoadingState {
    UNLOADED,
    LOADING,
    LOADED
}

class MainViewModel(private val gateway: MainGateway) : ViewModel() {

    val welcomeMessage: MutableLiveData<String> = MutableLiveData("Welcome!")

    val gatewayMessage: MutableLiveData<String> = MutableLiveData()
    val gatewayMessageLoadingState: MutableLiveData<GatewayMessageLoadingState> = MutableLiveData(GatewayMessageLoadingState.UNLOADED)

    private val random = Random()

    fun fetchMessage() {
        gatewayMessageLoadingState.postValue(GatewayMessageLoadingState.LOADING)

        viewModelScope.launch {
            val result = gateway.requestMessage(random.nextBoolean())
            gatewayMessageLoadingState.postValue(GatewayMessageLoadingState.LOADED)
            when(result) {
                is Result.Success -> {
                    gatewayMessage.postValue(result.data)
                }
                is Result.Error -> {
                    gatewayMessage.postValue("oops, an error occurred")
                }
            }
        }
    }

}
