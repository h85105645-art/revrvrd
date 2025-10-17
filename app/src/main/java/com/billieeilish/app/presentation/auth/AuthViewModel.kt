package com.billieeilish.app.presentation.auth

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * ViewModel for authentication operations
 */
@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    private var googleSignInClient: GoogleSignInClient? = null
    
    fun signInWithGoogle(activity: ComponentActivity) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                
                // Configure Google Sign-In
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("123456789012-abcdefghijklmnopqrstuvwxyz654321.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                
                googleSignInClient = GoogleSignIn.getClient(activity, gso)
                
                // Start sign-in intent
                val signInIntent = googleSignInClient?.signInIntent
                signInIntent?.let { intent ->
                    activity.startActivityForResult(intent, RC_SIGN_IN)
                }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "فشل في تسجيل الدخول: ${e.localizedMessage}"
                )
            }
        }
    }
    
    fun handleSignInResult(data: android.content.Intent?) {
        viewModelScope.launch {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                
                // Firebase auth with Google
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                val authResult = auth.signInWithCredential(credential).await()
                
                // Save user data to Firestore
                authResult.user?.let { user ->
                    val userData = hashMapOf(
                        "uid" to user.uid,
                        "name" to user.displayName,
                        "email" to user.email,
                        "photoUrl" to user.photoUrl?.toString(),
                        "createdAt" to System.currentTimeMillis(),
                        "lastSignIn" to System.currentTimeMillis()
                    )
                    
                    firestore.collection("users")
                        .document(user.uid)
                        .set(userData)
                        .await()
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSignedIn = true,
                    errorMessage = null
                )
                
            } catch (e: ApiException) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "فشل في تسجيل الدخول: ${e.localizedMessage}"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "حدث خطأ غير متوقع: ${e.localizedMessage}"
                )
            }
        }
    }
    
    companion object {
        const val RC_SIGN_IN = 9001
    }
}

/**
 * UI state for authentication screen
 */
data class AuthUiState(
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val errorMessage: String? = null
)