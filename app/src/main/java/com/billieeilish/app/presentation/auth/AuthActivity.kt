package com.billieeilish.app.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.billieeilish.app.presentation.MainActivity
import com.billieeilish.app.presentation.theme.BillieEilishTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authentication activity that handles user sign-in
 */
@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            BillieEilishTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthScreen(
                        onSignInSuccess = {
                            navigateToMain()
                        }
                    )
                }
            }
        }
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}