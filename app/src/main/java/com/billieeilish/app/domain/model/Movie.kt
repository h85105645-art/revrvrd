package com.billieeilish.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Movie data model
 */
@Serializable
data class Movie(
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
    val popularity: Double,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val adult: Boolean,
    val video: Boolean,
    @SerialName("genre_ids")
    val genreIds: List<Int>
) {
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
    
    val fullBackdropPath: String
        get() = "https://image.tmdb.org/t/p/w1280$backdropPath"
    
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    val releaseYear: String
        get() = releaseDate.take(4)
}

/**
 * Movie response from TMDB API
 */
@Serializable
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

/**
 * Movie details with additional information
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
    val popularity: Double,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val adult: Boolean,
    val video: Boolean,
    val genres: List<Genre>,
    val runtime: Int?,
    val budget: Long,
    val revenue: Long,
    val status: String,
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
 * Genre model
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
    val order: Int
) {
    val fullProfilePath: String
        get() = profilePath?.let { "https://image.tmdb.org/t/p/w185$it" } ?: ""
}

/**
 * Credits response
 */
@Serializable
data class Credits(
    val id: Int,
    val cast: List<Cast>
)