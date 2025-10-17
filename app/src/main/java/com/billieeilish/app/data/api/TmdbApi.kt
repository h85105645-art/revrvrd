package com.billieeilish.app.data.api

import com.billieeilish.app.domain.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * TMDB API interface for fetching movies and TV series data
 */
interface TmdbApi {
    
    companion object {
        const val BASE_URL = "YOUR_URL"
        const val API_KEY = "YOUR_API"
        const val BEARER_TOKEN = "YOUR_TOKEN_HEAR"
    // Movies endpoints
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieResponse>
    
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieResponse>
    
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieResponse>
    
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieResponse>
    
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieResponse>
    
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieDetails>
    
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Response<Credits>
    
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<MovieResponse>
    
    // TV Series endpoints
    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("tv/on_the_air")
    suspend fun getOnTheAirSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("tv/airing_today")
    suspend fun getAiringTodaySeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("trending/tv/day")
    suspend fun getTrendingSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("tv/{tv_id}")
    suspend fun getSeriesDetails(
        @Path("tv_id") seriesId: Int,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesDetails>
    
    @GET("tv/{tv_id}/credits")
    suspend fun getSeriesCredits(
        @Path("tv_id") seriesId: Int
    ): Response<Credits>
    
    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String = "ar-SA"
    ): Response<SeasonDetails>
    
    @GET("search/tv")
    suspend fun searchSeries(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    // Anime endpoints (using discover with specific filters)
    @GET("discover/tv")
    suspend fun getAnime(
        @Query("with_genres") genres: String = "16", // Animation genre
        @Query("with_origin_country") country: String = "JP", // Japan
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("discover/tv")
    suspend fun getPopularAnime(
        @Query("with_genres") genres: String = "16",
        @Query("with_origin_country") country: String = "JP",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    @GET("discover/tv")
    suspend fun getTopRatedAnime(
        @Query("with_genres") genres: String = "16",
        @Query("with_origin_country") country: String = "JP",
        @Query("sort_by") sortBy: String = "vote_average.desc",
        @Query("vote_count.gte") minVotes: Int = 100,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<SeriesResponse>
    
    // Multi search (movies, series, people)
    @GET("search/multi")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ar-SA"
    ): Response<Any> // Will need to handle different types
}
