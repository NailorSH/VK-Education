package com.nailorsh.rectlistviews

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    companion object {
        const val SECOND_KEY = "second_key"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val secondView : FrameLayout = findViewById(R.id.second_activity)

        val index = intent.extras?.getInt(SECOND_KEY)

        if (index != null) {
            if (index % 2 == 0) {
                secondView.setBackgroundColor(Color.RED)
            } else {
                secondView.setBackgroundColor(Color.BLUE)
            }
        }

        val indexView: TextView = findViewById(R.id.index)
        indexView.text = index.toString()
    }
}