package com.billieeilish.app.presentation.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billieeilish.app.domain.model.TvChannel
import com.billieeilish.app.domain.repository.IptvRepository
import com.billieeilish.app.utils.Resource
import com.billieeilish.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for TV channels screen
 */
@HiltViewModel
class TvChannelsViewModel @Inject constructor(
    private val iptvRepository: IptvRepository
) : ViewModel() {
    
    private val _channels = MutableStateFlow<UiState<List<TvChannel>>>(UiState.Loading)
    val channels: StateFlow<UiState<List<TvChannel>>> = _channels.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()
    
    private val _selectedCountry = MutableStateFlow("all")
    val selectedCountry: StateFlow<String> = _selectedCountry.asStateFlow()
    
    fun loadChannels() {
        viewModelScope.launch {
            iptvRepository.getChannels().collect { resource ->
                _channels.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.let { channelList ->
                            if (channelList.isNotEmpty()) {
                                UiState.Success(channelList)
                            } else {
                                UiState.Empty
                            }
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    fun loadChannelsByCountry(countryCode: String) {
        _selectedCountry.value = countryCode
        viewModelScope.launch {
            iptvRepository.getChannelsByCountry(countryCode).collect { resource ->
                _channels.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.let { channelList ->
                            if (channelList.isNotEmpty()) {
                                UiState.Success(channelList)
                            } else {
                                UiState.Empty
                            }
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    fun loadChannelsByCategory(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            iptvRepository.getChannelsByCategory(category).collect { resource ->
                _channels.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> {
                        resource.data?.let { channelList ->
                            if (channelList.isNotEmpty()) {
                                UiState.Success(channelList)
                            } else {
                                UiState.Empty
                            }
                        } ?: UiState.Empty
                    }
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
    
    fun searchChannels(query: String) {
        if (query.isBlank()) {
            loadChannels()
            return
        }
        
        val currentChannels = when (val state = _channels.value) {
            is UiState.Success -> state.data
            else -> return
        }
        val filteredChannels = currentChannels.filter { channel ->
            channel.name.contains(query, ignoreCase = true) ||
            channel.group.contains(query, ignoreCase = true) ||
            channel.category.contains(query, ignoreCase = true)
        }
        
        _channels.value = if (filteredChannels.isNotEmpty()) {
            UiState.Success(filteredChannels)
        } else {
            UiState.Empty
        }
    }
}