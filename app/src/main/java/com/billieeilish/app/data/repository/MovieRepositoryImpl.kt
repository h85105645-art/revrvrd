package com.billieeilish.app.data.repository

import com.billieeilish.app.data.api.TmdbApi
import com.billieeilish.app.domain.model.*
import com.billieeilish.app.domain.repository.MovieRepository
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of MovieRepository
 * Handles movie data operations from TMDB API
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
) : MovieRepository {
    
    override suspend fun getPopularMovies(page: Int): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getPopularMovies(page)
            if (response.isSuccessful) {
                response.body()?.let { movieResponse ->
                    emit(Resource.Success(movieResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch popular movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getTopRatedMovies(page: Int): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getTopRatedMovies(page)
            if (response.isSuccessful) {
                response.body()?.let { movieResponse ->
                    emit(Resource.Success(movieResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch top rated movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getUpcomingMovies(page: Int): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getUpcomingMovies(page)
            if (response.isSuccessful) {
                response.body()?.let { movieResponse ->
                    emit(Resource.Success(movieResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch upcoming movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getNowPlayingMovies(page: Int): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getNowPlayingMovies(page)
            if (response.isSuccessful) {
                response.body()?.let { movieResponse ->
                    emit(Resource.Success(movieResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch now playing movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getTrendingMovies(page: Int): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getTrendingMovies(page)
            if (response.isSuccessful) {
                response.body()?.let { movieResponse ->
                    emit(Resource.Success(movieResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch trending movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getMovieDetails(movieId)
            if (response.isSuccessful) {
                response.body()?.let { movieDetails ->
                    emit(Resource.Success(movieDetails))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch movie details: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getMovieCredits(movieId: Int): Flow<Resource<Credits>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.getMovieCredits(movieId)
            if (response.isSuccessful) {
                response.body()?.let { credits ->
                    emit(Resource.Success(credits))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch movie credits: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun searchMovies(query: String, page: Int): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = tmdbApi.searchMovies(query, page)
            if (response.isSuccessful) {
                response.body()?.let { movieResponse ->
                    emit(Resource.Success(movieResponse))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to search movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
}