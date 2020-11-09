package com.example.androidpatterns

import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidpatterns.ui.main.GatewayMessageLoadingState
import com.example.androidpatterns.ui.main.MainFragment
import com.example.androidpatterns.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class MainFragmentTest : AutoCloseKoinTest() {
    private lateinit var subject: MainFragment

    private val mainViewModel: MainViewModel by inject()

    @Before
    fun `set up`() {
        loadKoinModules(listOf(testAppModule))

        val welcomeMessage: MutableLiveData<String> = MutableLiveData()
        whenever(mainViewModel.welcomeMessage).thenReturn(welcomeMessage)

        val gatewayMessageLoadingState: MutableLiveData<GatewayMessageLoadingState> = MutableLiveData()
        whenever(mainViewModel.gatewayMessageLoadingState).thenReturn(gatewayMessageLoadingState)

        val gatewayMessage: MutableLiveData<String> = MutableLiveData()
        whenever(mainViewModel.gatewayMessage).thenReturn(gatewayMessage)

        launchFragmentInContainer<MainFragment>().onFragment { fragment ->
            subject = fragment
        }
    }

    @Test
    fun `when the fragment loads, it displays a welcome message`() {
        mainViewModel.welcomeMessage.postValue("a welcome message")

        val messageTextView = subject.view?.findViewById<TextView>(R.id.message)

        assertThat(messageTextView?.text).isEqualTo("a welcome message")
    }

}
