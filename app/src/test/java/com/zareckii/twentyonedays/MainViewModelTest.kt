package com.zareckii.twentyonedays

import org.junit.Assert.*
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test_0_days_and_reinit() {
        val repository = FakeRepository(0)
        val communication = FakeMainCommunications.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.ZeroDays))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }

    @Test
    fun text_N_days_and_reinit() {
        val repository = FakeRepository(5)
        val communication = FakeMainCommunications.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(days = 5)))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }
}

private class FakeRepository(private val days: Int) : MainRepository {

    override fun days(): Int = days
}

interface FakeMainCommunications : MainCommunication.Put {

    fun checkCalledCount(count: Int): Boolean
    fun isSame(uiState: UiState): Boolean

    class Base() : FakeMainCommunications {
        private lateinit var state: UiState
        private var callCount = 0

        override fun isSame(uiState: UiState): Boolean = state.equals(uiState)

        override fun checkCalledCount(count: Int): Boolean = count == callCount

        override fun put(value: UiState) {
            callCount++
            state = value
        }
    }
}