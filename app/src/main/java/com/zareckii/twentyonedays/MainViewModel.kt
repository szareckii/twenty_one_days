package com.zareckii.twentyonedays

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
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

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observe(owner, observer)
}

sealed class UiState {

    object ZeroDays : UiState() {

    }

    data class NDays(private val days: Int) : UiState() {

    }

}

interface MainCommunication {

    interface Put<T> {
        fun put(value: T)
    }

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Mutable<T> : Put<T>, Observe<T>

    class Base<T>(private val liveData: MutableLiveData<T>) : Mutable<T> {

        override fun put(value: T) {
            liveData.value = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }
}

interface MainRepository {

    fun days(): Int
}