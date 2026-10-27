plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.callassist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.callassist"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
    }
}
