package com.billieeilish.app.domain.repository

import com.billieeilish.app.domain.model.*
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for movie operations
 */
interface MovieRepository {
    
    suspend fun getPopularMovies(page: Int = 1): Flow<Resource<MovieResponse>>
    
    suspend fun getTopRatedMovies(page: Int = 1): Flow<Resource<MovieResponse>>
    
    suspend fun getUpcomingMovies(page: Int = 1): Flow<Resource<MovieResponse>>
    
    suspend fun getNowPlayingMovies(page: Int = 1): Flow<Resource<MovieResponse>>
    
    suspend fun getTrendingMovies(page: Int = 1): Flow<Resource<MovieResponse>>
    
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
    
    suspend fun getMovieCredits(movieId: Int): Flow<Resource<Credits>>
    
    suspend fun searchMovies(query: String, page: Int = 1): Flow<Resource<MovieResponse>>
}