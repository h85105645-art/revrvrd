package com.billieeilish.app.utils

/**
 * A generic wrapper class for handling API responses and states
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

/**
 * UI State for handling different states in composables
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}

/**
 * Extension function to convert Resource to UiState
 */
fun <T> Resource<T>.toUiState(): UiState<T> {
    return when (this) {
        is Resource.Loading -> UiState.Loading
        is Resource.Success -> {
            if (data != null) {
                UiState.Success(data)
            } else {
                UiState.Empty
            }
        }
        is Resource.Error -> UiState.Error(message ?: "Unknown error occurred")
    }
}