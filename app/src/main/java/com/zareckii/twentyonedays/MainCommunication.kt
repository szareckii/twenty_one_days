package com.zareckii.twentyonedays

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

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
