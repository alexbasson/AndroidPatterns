package com.example.androidpatterns

import com.example.androidpatterns.ui.main.MainViewModel
import org.koin.dsl.module
import org.mockito.Mockito.mock

val testAppModule = module(override = true) {

    single<MainViewModel> { mock(MainViewModel::class.java) }

}
