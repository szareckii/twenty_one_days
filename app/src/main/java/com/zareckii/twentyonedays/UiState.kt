package com.zareckii.twentyonedays

import android.view.View
import android.widget.Button
import android.widget.TextView

sealed class UiState {

    abstract fun apply(daysTestView: TextView, resetButton: Button)
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