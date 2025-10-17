package com.billieeilish.app.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billieeilish.app.domain.model.Movie
import com.billieeilish.app.domain.repository.MovieRepository
import com.billieeilish.app.utils.Resource
import com.billieeilish.app.utils.UiState
import com.billieeilish.app.utils.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for movies screen
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    
    private val _popularMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val popularMovies: StateFlow<UiState<List<Movie>>> = _popularMovies.asStateFlow()
    
    private val _topRatedMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val topRatedMovies: StateFlow<UiState<List<Movie>>> = _topRatedMovies.asStateFlow()
    
    private val _upcomingMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val upcomingMovies: StateFlow<UiState<List<Movie>>> = _upcomingMovies.asStateFlow()
    
    private val _trendingMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val trendingMovies: StateFlow<UiState<List<Movie>>> = _trendingMovies.asStateFlow()
    
    private val _nowPlayingMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val nowPlayingMovies: StateFlow<UiState<List<Movie>>> = _nowPlayingMovies.asStateFlow()
    
    fun loadMovies() {
        loadPopularMovies()
        loadTopRatedMovies()
        loadUpcomingMovies()
        loadTrendingMovies()
        loadNowPlayingMovies()
    }
    
    private fun loadPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collect { resource ->
                _popularMovies.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.results?.let { movies ->
                            UiState.Success(movies)
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    private fun loadTopRatedMovies() {
        viewModelScope.launch {
            movieRepository.getTopRatedMovies().collect { resource ->
                _topRatedMovies.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.results?.let { movies ->
                            UiState.Success(movies)
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    private fun loadUpcomingMovies() {
        viewModelScope.launch {
            movieRepository.getUpcomingMovies().collect { resource ->
                _upcomingMovies.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.results?.let { movies ->
                            UiState.Success(movies)
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    private fun loadTrendingMovies() {
        viewModelScope.launch {
            movieRepository.getTrendingMovies().collect { resource ->
                _trendingMovies.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.results?.let { movies ->
                            UiState.Success(movies)
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    private fun loadNowPlayingMovies() {
        viewModelScope.launch {
            movieRepository.getNowPlayingMovies().collect { resource ->
                _nowPlayingMovies.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.results?.let { movies ->
                            UiState.Success(movies)
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    fun searchMovies(query: String) {
        if (query.isBlank()) {
            loadMovies()
            return
        }
        
        viewModelScope.launch {
            movieRepository.searchMovies(query).collect { resource ->
                _popularMovies.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.results?.let { movies ->
                            UiState.Success(movies)
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
}