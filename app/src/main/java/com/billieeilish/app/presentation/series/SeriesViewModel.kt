package com.billieeilish.app.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billieeilish.app.domain.model.Series
import com.billieeilish.app.domain.repository.SeriesRepository
import com.billieeilish.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for series screen with search and category functionality
 */
@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SeriesUiState())
    val uiState: StateFlow<SeriesUiState> = _uiState.asStateFlow()
    
    init {
        loadSeries("popular")
    }
    
    fun loadSeries(category: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(series = Resource.Loading())
            
            try {
                val seriesFlow = when (category) {
                    "popular" -> seriesRepository.getPopularSeries()
                    "top_rated" -> seriesRepository.getTopRatedSeries()
                    "on_the_air" -> seriesRepository.getOnTheAirSeries()
                    "airing_today" -> seriesRepository.getAiringTodaySeries()
                    else -> seriesRepository.getPopularSeries()
                }
                
                seriesFlow.collect { resource ->
                    _uiState.value = _uiState.value.copy(
                        series = when (resource) {
                            is Resource.Loading -> Resource.Loading()
                            is Resource.Success -> Resource.Success(resource.data?.results ?: emptyList())
                            is Resource.Error -> Resource.Error(resource.message ?: "خطأ في تحميل المسلسلات")
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    series = Resource.Error(e.message ?: "خطأ في تحميل المسلسلات")
                )
            }
        }
    }
    
    fun searchSeries(query: String) {
        if (query.isBlank()) {
            loadSeries("popular")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(series = Resource.Loading())
            
            try {
                seriesRepository.searchSeries(query).collect { resource ->
                    _uiState.value = _uiState.value.copy(
                        series = when (resource) {
                            is Resource.Loading -> Resource.Loading()
                            is Resource.Success -> Resource.Success(resource.data?.results ?: emptyList())
                            is Resource.Error -> Resource.Error(resource.message ?: "خطأ في البحث")
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    series = Resource.Error(e.message ?: "خطأ في البحث")
                )
            }
        }
    }
    
    fun toggleSearch() {
        _uiState.value = _uiState.value.copy(
            isSearchVisible = !_uiState.value.isSearchVisible
        )
        
        if (!_uiState.value.isSearchVisible) {
            clearSearch()
        }
    }
    
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        
        if (query.isNotEmpty()) {
            searchSeries(query)
        }
    }
    
    fun clearSearch() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            isSearchVisible = false
        )
        loadSeries("popular")
    }
    

}

/**
 * UI state for series screen
 */
data class SeriesUiState(
    val series: Resource<List<Series>> = Resource.Loading(),
    val searchQuery: String = "",
    val isSearchVisible: Boolean = false
)