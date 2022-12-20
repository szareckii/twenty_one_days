package com.zareckii.twentyonedays

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class MainViewModel(
    private val repository: MainRepository,
    private val communication: MainCommunication.Mutable<UiState>
) : MainCommunication.Observe<UiState> {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            val days = repository.days()

            communication.put(
                if (days == 0)
                    UiState.ZeroDays
                else
                    UiState.NDays(days)
            )
        }
    }

    fun reset() {
        repository.reset()
        communication.put(UiState.ZeroDays)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observe(owner, observer)
}
