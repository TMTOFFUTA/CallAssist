package com.example.callassist

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

/**
 * Minimal, user-enabled helper. It intentionally does not retrieve window text,
 * click WhatsApp controls, or attempt to bypass Android's app/window boundaries.
 */
class CallAssistAccessibilityService : AccessibilityService() {
    private var lastNoticeAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val packageName = event.packageName?.toString() ?: return
        if (packageName != "com.whatsapp.w4b") return
        val now = System.currentTimeMillis()
        if (now - lastNoticeAt < 12_000L) return
        lastNoticeAt = now
        Toast.makeText(
            this,
            "CallAssist: for PiP, use WhatsApp's supported minimize/home workflow after enabling PiP in Settings.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onInterrupt() = Unit
}
