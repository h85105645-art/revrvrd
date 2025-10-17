package com.billieeilish.app.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * SuperEmbed API interface for video streaming
 * SuperEmbed is a popular video embedding service that provides streaming links
 */
interface SuperEmbedApi {
    
    companion object {
        const val BASE_URL = "YOUR_URL"
        const val EMBED_BASE_URL = "YOUR_URL"
    }
    
    /**
     * Get movie streaming link from SuperEmbed
     * @param movieId TMDB movie ID
     * @param season Season number (for TV series)
     * @param episode Episode number (for TV series)
     */
    @GET("directstream.php")
    suspend fun getMovieStream(
        @Query("video_id") movieId: Int,
        @Query("tmdb") tmdb: Int = 1
    ): Response<String>
    
    /**
     * Get TV series episode streaming link
     */
    @GET("directstream.php")
    suspend fun getSeriesStream(
        @Query("video_id") seriesId: Int,
        @Query("s") season: Int,
        @Query("e") episode: Int,
        @Query("tmdb") tmdb: Int = 1
    ): Response<String>
    
    /**
     * Alternative method using embed URL format
     */
    fun getMovieEmbedUrl(movieId: Int): String {
        return "${EMBED_BASE_URL}?video_id=${movieId}&tmdb=1"
    }
    
    /**
     * Alternative method for series embed URL
     */
    fun getSeriesEmbedUrl(seriesId: Int, season: Int, episode: Int): String {
        return "${EMBED_BASE_URL}?video_id=${seriesId}&s=${season}&e=${episode}&tmdb=1"
    }
}

/**
 * Data class for video stream response
 */
data class VideoStreamResponse(
    val success: Boolean,
    val url: String?,
    val quality: String?,
    val type: String?
)
