package com.billieeilish.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Billie Eilish app
 * Initializes Hilt dependency injection
 */
@HiltAndroidApp
class BillieEilishApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}