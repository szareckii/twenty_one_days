package com.zareckii.twentyonedays

import android.view.View
import android.widget.Button
import android.widget.TextView
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

    abstract fun apply(daysTestView: TextView, resetButton: Button )
    object ZeroDays : UiState() {
        override fun apply(daysTestView: TextView, resetButton: Button) {
            daysTestView.text = "0"
            resetButton.visibility = View.GONE
        }
    }

    data class NDays(private val days: Int) : UiState() {
        override fun apply(daysTestView: TextView, resetButton: Button) {
            daysTestView.text = days.toString()
            resetButton.visibility = View.VISIBLE
        }

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