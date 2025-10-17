package com.billieeilish.app.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.billieeilish.app.presentation.MainActivity
import com.billieeilish.app.presentation.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash screen activity that shows app logo and checks authentication status
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        auth = FirebaseAuth.getInstance()
        
        // Keep splash screen visible while checking auth
        splashScreen.setKeepOnScreenCondition { true }
        
        lifecycleScope.launch {
            delay(2000) // Show splash for 2 seconds
            checkAuthenticationAndNavigate()
        }
    }
    
    private fun checkAuthenticationAndNavigate() {
        val currentUser = auth.currentUser
        
        val intent = if (currentUser != null) {
            // User is signed in, go to main activity
            Intent(this, MainActivity::class.java)
        } else {
            // User is not signed in, go to auth activity
            Intent(this, AuthActivity::class.java)
        }
        
        startActivity(intent)
        finish()
    }
}