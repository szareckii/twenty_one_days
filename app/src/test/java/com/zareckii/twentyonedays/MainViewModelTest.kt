package com.zareckii.twentyonedays

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.*
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test_0_days_and_reinit() {
        val repository = FakeRepository.Base(0)
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
        val repository = FakeRepository.Base(5)
        val communication = FakeMainCommunications.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(days = 5)))
        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }

    @Test
    fun test_reset() {
        val repository = FakeRepository.Base(5)
        val communication = FakeMainCommunications.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(days = 5)))
        viewModel.reset()
        assertEquals(true, repository.resetCalledCount(1))
        assertEquals(true, communication.checkCalledCount(2))
        assertEquals(true, communication.isSame(UiState.ZeroDays))
    }
}

private interface FakeRepository : MainRepository {

    fun resetCalledCount(count: Int): Boolean

    class Base(private val days: Int) : FakeRepository {

        private var resetCallCount = 0
        override fun days(): Int = days

        override fun reset() {
            resetCallCount++
        }

        override fun resetCalledCount(count: Int): Boolean = resetCallCount == count
    }
}

private interface FakeMainCommunications : MainCommunication.Mutable<UiState> {

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

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) = Unit
    }
}