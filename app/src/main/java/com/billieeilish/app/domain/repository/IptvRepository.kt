package com.billieeilish.app.domain.repository

import com.billieeilish.app.domain.model.TvChannel
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for IPTV channels
 */
interface IptvRepository {
    
    /**
     * Get all available TV channels
     */
    fun getChannels(): Flow<Resource<List<TvChannel>>>
    
    /**
     * Get channels by country
     */
    fun getChannelsByCountry(country: String): Flow<Resource<List<TvChannel>>>
    
    /**
     * Get channels by category
     */
    fun getChannelsByCategory(category: String): Flow<Resource<List<TvChannel>>>
    
    /**
     * Search channels by name
     */
    fun searchChannels(query: String): Flow<Resource<List<TvChannel>>>
}