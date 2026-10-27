package com.example.callassist

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView

class FrontLightActivity : Activity() {
    private var previousBrightness = -1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        previousBrightness = window.attributes.screenBrightness
        window.attributes = window.attributes.apply { screenBrightness = 1f }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.setBackgroundColor(0xFFFFFFFF.toInt())

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(0xFFFFFFFF.toInt())
        }
        root.addView(TextView(this).apply {
            text = "Front screen light"
            textSize = 24f
            setTextColor(0xFF222222.toInt())
            gravity = Gravity.CENTER
        })
        root.addView(TextView(this).apply {
            text = "Adjust screen brightness in Quick Settings if needed. This is screen illumination, not an LED flash."
            textSize = 14f
            setTextColor(0xFF333333.toInt())
            gravity = Gravity.CENTER
            setPadding(0, 18, 0, 18)
        })
        root.addView(SeekBar(this).apply {
            max = 100
            progress = 100
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    window.attributes = window.attributes.apply { screenBrightness = (progress.coerceAtLeast(5) / 100f) }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        })
        root.addView(Button(this).apply {
            text = "Turn light off"
            isAllCaps = false
            setOnClickListener { finish() }
        })
        setContentView(root)
    }

    override fun onDestroy() {
        if (previousBrightness >= 0f) {
            window.attributes = window.attributes.apply { screenBrightness = previousBrightness }
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onDestroy()
    }
}
