package com.billieeilish.app.domain.repository

import com.billieeilish.app.domain.model.*
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for TV series operations
 */
interface SeriesRepository {
    
    suspend fun getPopularSeries(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getTopRatedSeries(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getOnTheAirSeries(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getAiringTodaySeries(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getTrendingSeries(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getSeriesDetails(seriesId: Int): Flow<Resource<SeriesDetails>>
    
    suspend fun getSeriesCredits(seriesId: Int): Flow<Resource<Credits>>
    
    suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): Flow<Resource<SeasonDetails>>
    
    suspend fun searchSeries(query: String, page: Int = 1): Flow<Resource<SeriesResponse>>
    
    // Anime specific methods
    suspend fun getAnime(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getPopularAnime(page: Int = 1): Flow<Resource<SeriesResponse>>
    
    suspend fun getTopRatedAnime(page: Int = 1): Flow<Resource<SeriesResponse>>
}