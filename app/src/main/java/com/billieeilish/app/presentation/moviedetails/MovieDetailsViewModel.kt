package com.billieeilish.app.presentation.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billieeilish.app.domain.model.Credits
import com.billieeilish.app.domain.model.Movie
import com.billieeilish.app.domain.repository.MovieRepository
import com.billieeilish.app.utils.Resource
import com.billieeilish.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for movie details screen
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    
    private val _movie = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val movie: StateFlow<UiState<Movie>> = _movie.asStateFlow()
    
    private val _credits = MutableStateFlow<UiState<Credits>>(UiState.Loading)
    val credits: StateFlow<UiState<Credits>> = _credits.asStateFlow()
    
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()
    
    fun loadMovieDetails(movieId: Int) {
        loadMovie(movieId)
        loadCredits(movieId)
    }
    
    private fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId).collect { resource ->
                _movie.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.let { movie ->
                            UiState.Success(movie)
                        } ?: UiState.Error("Movie not found")
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    private fun loadCredits(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieCredits(movieId).collect { resource ->
                _credits.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.let { credits ->
                            UiState.Success(credits)
                        } ?: UiState.Error("Credits not found")
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            // TODO: Implement favorite functionality with local database
            _isFavorite.value = !_isFavorite.value
        }
    }
}