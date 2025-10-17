package com.billieeilish.app.data.repository

import com.billieeilish.app.data.api.TmdbApi
import com.billieeilish.app.domain.model.*
import com.billieeilish.app.domain.repository.SeriesRepository
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of SeriesRepository
 * Handles TV series and anime data operations from TMDB API
 */
@Singleton
class SeriesRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
) : SeriesRepository {
    
    override suspend fun getPopularSeries(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getPopularSeries(page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch popular series: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getTopRatedSeries(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getTopRatedSeries(page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch top rated series: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getOnTheAirSeries(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getOnTheAirSeries(page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch on the air series: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getAiringTodaySeries(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getAiringTodaySeries(page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch airing today series: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getTrendingSeries(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getTrendingSeries(page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch trending series: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getSeriesDetails(seriesId: Int): Flow<Resource<SeriesDetails>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getSeriesDetails(seriesId)
            if (response.isSuccessful) {
                response.body()?.let { seriesDetails ->
                    emit(Resource.Success(seriesDetails))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch series details: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getSeriesCredits(seriesId: Int): Flow<Resource<Credits>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getSeriesCredits(seriesId)
            if (response.isSuccessful) {
                response.body()?.let { credits ->
                    emit(Resource.Success(credits))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch series credits: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): Flow<Resource<SeasonDetails>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getSeasonDetails(seriesId, seasonNumber)
            if (response.isSuccessful) {
                response.body()?.let { seasonDetails ->
                    emit(Resource.Success(seasonDetails))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch season details: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun searchSeries(query: String, page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.searchSeries(query, page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to search series: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getAnime(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getAnime(page = page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch anime: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getPopularAnime(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getPopularAnime(page = page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch popular anime: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getTopRatedAnime(page: Int): Flow<Resource<SeriesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getTopRatedAnime(page = page)
            if (response.isSuccessful) {
                response.body()?.let { seriesResponse ->
                    emit(Resource.Success(seriesResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch top rated anime: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
}