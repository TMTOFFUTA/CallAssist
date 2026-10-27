# CallAssist Android prototype

A minimal Kotlin Android Studio project for Android 15+ (compile/target SDK 35, min SDK 26). Package: `com.example.callassist`.

## What works in this prototype
- Opens WhatsApp Business (`com.whatsapp.w4b`) if installed.
- Opens the Android Picture-in-picture settings page for WhatsApp Business when supported by the device Settings app.
- Provides a white-screen front illuminator with a brightness slider and manual off button.
- Provides split-screen workflow instructions. Actual split screen is controlled by Android/XOS Recents UI.
- Optional Accessibility Service detects when WhatsApp Business becomes foregrounded and displays a brief contextual hint. It does not read message text or click controls.

## Important limitations
A companion app cannot use Android's ordinary PiP API to force another app's activity into PiP. WhatsApp Business must support PiP on the installed version/device and the user must enable it. Likewise, a separate app cannot reliably force its screen light above another app or take exclusive control of the camera/torch used by WhatsApp. The white-screen light is most useful before a call or where the device's multitasking UI allows it to remain visible. Split-screen availability depends on Infinix XOS and the target apps.

## Build
1. Install Android Studio (current stable) and Android SDK Platform 35.
2. Extract this folder and choose **Open** in Android Studio, selecting `CallAssistPrototype`.
3. Allow Gradle sync to download the Android Gradle Plugin and Kotlin plugin.
4. Connect the Infinix phone with USB debugging enabled, or use an emulator. Click **Run**.
5. On the phone, allow installation/debugging prompts.
6. In CallAssist, tap **Open Accessibility settings** and enable **CallAssist helper** only if you want contextual guidance.

## Test plan
1. Tap **Check WhatsApp Business installation**.
2. Tap **Open WhatsApp Business PiP settings**. If the Settings page does not open directly, use Android Settings → Apps → WhatsApp Business → Picture-in-picture and enable it if offered.
3. Start a test video call. Use the phone's supported home/minimize gesture and verify whether WhatsApp Business enters PiP. This behavior depends on WhatsApp and XOS.
4. Return to CallAssist and test **Open front light**. Move the slider and tap **Turn light off**.
5. Test split screen using Recents → app icon/menu → Split screen (labels vary by XOS version).

## Notes
- No internet permission is declared by the app.
- No camera, microphone, contacts, notification-listener, overlay, or torch permission is requested.
- Accessibility is optional, explicitly enabled by the user, and limited to window-state events.
