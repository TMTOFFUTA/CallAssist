package com.example.callassist

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val waBusinessPackage = "com.whatsapp.w4b"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(24))
            setBackgroundColor(0xFFF5F7F8.toInt())
        }
        val scroll = ScrollView(this)
        val title = TextView(this).apply {
            text = "CallAssist"
            textSize = 30f
            setTextColor(0xFF145B48.toInt())
            gravity = Gravity.CENTER_VERTICAL
        }
        content.addView(title, matchWrap())
        content.addView(TextView(this).apply {
            text = "WhatsApp Business call companion\nPrototype for Android 15+ · Infinix"
            textSize = 15f
            setTextColor(0xFF4B5563.toInt())
            setPadding(0, dp(6), 0, dp(18))
        }, matchWrap())

        content.addView(section("1 · Picture-in-picture assistance"))
        content.addView(body("Enable PiP for WhatsApp Business, then use WhatsApp's own minimize/home behavior during a video call. Android does not let a companion app directly force another app's activity into PiP."))
        content.addView(actionButton("Open WhatsApp Business PiP settings") { openPipSettings() })
        content.addView(actionButton("Open WhatsApp Business") { launchWhatsApp() })

        content.addView(section("2 · Front screen light"))
        content.addView(body("A white screen can illuminate your face from the front. This is not a camera flash and may not stay visible over WhatsApp; use it before or alongside a call only if it does not cover call controls."))
        content.addView(actionButton("Open front light") {
            startActivity(Intent(this, FrontLightActivity::class.java))
        })

        content.addView(section("3 · Split-screen assistance"))
        content.addView(body("Launch WhatsApp Business, open Recents, tap/hold its app icon, then choose Split screen if Infinix XOS offers it. Select the second app. Some apps or XOS versions may not expose split screen."))
        content.addView(actionButton("Open WhatsApp for split screen") { launchWhatsApp() })

        content.addView(section("Accessibility helper"))
        content.addView(body("Optional: enable CallAssist helper below. It only detects when WhatsApp Business is foregrounded and shows guidance. It does not inspect messages, press buttons, or guarantee automatic PiP."))
        content.addView(actionButton("Open Accessibility settings") {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        })
        content.addView(actionButton("Check WhatsApp Business installation") {
            val installed = packageManager.getLaunchIntentForPackage(waBusinessPackage) != null
            Toast.makeText(this, if (installed) "WhatsApp Business is installed." else "WhatsApp Business was not found. Install it from the Play Store first.", Toast.LENGTH_LONG).show()
        })
        content.addView(TextView(this).apply {
            text = "Privacy: no account login, message collection, or network access."
            textSize = 12f
            setTextColor(0xFF6B7280.toInt())
            setPadding(0, dp(18), 0, 0)
        }, matchWrap())

        scroll.addView(content)
        setContentView(scroll)
    }

    private fun openPipSettings() {
        try {
            startActivity(Intent(Settings.ACTION_PICTURE_IN_PICTURE_SETTINGS).apply {
                data = Uri.parse("package:$waBusinessPackage")
            })
        } catch (_: Exception) {
            Toast.makeText(this, "Open Android Settings → Apps → WhatsApp Business → Picture-in-picture.", Toast.LENGTH_LONG).show()
            launchWhatsApp()
        }
    }

    private fun launchWhatsApp() {
        val launch = packageManager.getLaunchIntentForPackage(waBusinessPackage)
        if (launch == null) {
            Toast.makeText(this, "WhatsApp Business is not installed.", Toast.LENGTH_LONG).show()
            return
        }
        try { startActivity(launch) } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, "Could not open WhatsApp Business.", Toast.LENGTH_LONG).show()
        }
    }

    private fun section(text: String) = TextView(this).apply {
        this.text = text
        textSize = 19f
        setTextColor(0xFF145B48.toInt())
        setPadding(0, dp(18), 0, dp(5))
    }

    private fun body(text: String) = TextView(this).apply {
        this.text = text
        textSize = 14f
        setTextColor(0xFF374151.toInt())
        setPadding(0, 0, 0, dp(10))
    }

    private fun actionButton(text: String, action: () -> Unit) = Button(this).apply {
        this.text = text
        isAllCaps = false
        setOnClickListener { action() }
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            bottomMargin = dp(5)
        }
    }

    private fun matchWrap() = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
