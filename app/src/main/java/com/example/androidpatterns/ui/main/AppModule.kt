package com.example.androidpatterns.ui.main

import androidx.annotation.Keep
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Keep
val appModule = module {

    viewModel { MainViewModel(get()) }

    single<MainGateway> { ApiMainGateway() }

}
