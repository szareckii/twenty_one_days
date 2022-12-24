package com.zareckii.twentyonedays

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = (application as ProvideViewModel).provideMainViewModel()

        val textView = findViewById<TextView>(R.id.countTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)

        mainViewModel.observe(this) { uiState ->
            uiState.apply(textView, resetButton)
        }

        resetButton.setOnClickListener {
            mainViewModel.reset()
        }
        mainViewModel.init(savedInstanceState == null)
    }
}
