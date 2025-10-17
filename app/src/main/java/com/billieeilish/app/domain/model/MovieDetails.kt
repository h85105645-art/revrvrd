package com.billieeilish.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Detailed movie information model
 */
@Serializable
data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val runtime: Int?,
    val genres: List<Genre>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val popularity: Double,
    val adult: Boolean,
    val budget: Long?,
    val revenue: Long?,
    val status: String?,
    val tagline: String?
) {
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
    
    val fullBackdropPath: String
        get() = "https://image.tmdb.org/t/p/w1280$backdropPath"
    
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    val releaseYear: String
        get() = releaseDate.take(4)
    
    val formattedRuntime: String
        get() = runtime?.let { "${it / 60}h ${it % 60}m" } ?: ""
}

/**
 * Movie genre model
 */
@Serializable
data class Genre(
    val id: Int,
    val name: String
)

/**
 * Cast member model
 */
@Serializable
data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    @SerialName("profile_path")
    val profilePath: String?,
    val order: Int = 0
) {
    val fullProfilePath: String
        get() = profilePath?.let { "https://image.tmdb.org/t/p/w185$it" } ?: ""
}

/**
 * Video model (trailers, teasers, etc.)
 */
@Serializable
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean
)