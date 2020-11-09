package com.example.androidpatterns

import com.example.androidpatterns.ui.main.MainGateway
import com.example.androidpatterns.ui.main.MainViewModel
import com.example.androidpatterns.ui.main.Result
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.mock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidpatterns.ui.main.GatewayMessageLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

class MainViewModelTest {
    @get:Rule
    val mockLiveDataRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var subject: MainViewModel
    private lateinit var mainGateway: MainGateway

    @ExperimentalCoroutinesApi
    @Before
    fun `set up`() {
        Dispatchers.setMain(testDispatcher)

        mainGateway = mock(MainGateway::class.java)

        subject = MainViewModel(gateway = mainGateway)
    }

    @ExperimentalCoroutinesApi
    @After
    fun `tear down`() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `welcomeMessage returns a welcome message`() {
        assertThat(subject.welcomeMessage.value).isEqualTo("Welcome!")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `it sets the loading states appropriately`() = runBlocking {
        testDispatcher.pauseDispatcher()

        assertThat(subject.gatewayMessageLoadingState.value).isEqualTo(GatewayMessageLoadingState.UNLOADED)

        subject.fetchMessage()

        assertThat(subject.gatewayMessageLoadingState.value).isEqualTo(GatewayMessageLoadingState.LOADING)

        testDispatcher.resumeDispatcher()

        assertThat(subject.gatewayMessageLoadingState.value).isEqualTo(GatewayMessageLoadingState.LOADED)

        return@runBlocking
    }

    @Test
    fun `fetchMessage(), when successful, posts a message from the gateway`() = runBlocking {
        whenever(mainGateway.requestMessage(anyBoolean())).thenReturn(Result.Success("A gateway message"))

        subject.fetchMessage()

        assertThat(subject.gatewayMessage.value).isEqualTo("A gateway message")

        return@runBlocking
    }

    @Test
    fun `fetchMessage(), when it errors, posts an error message`() = runBlocking {
        whenever(mainGateway.requestMessage(anyBoolean())).thenReturn(Result.Error(Exception()))

        subject.fetchMessage()

        assertThat(subject.gatewayMessage.value).isEqualTo("oops, an error occurred")

        return@runBlocking
    }

}
