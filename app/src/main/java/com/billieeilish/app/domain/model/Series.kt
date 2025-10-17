package com.billieeilish.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TV Series data model
 */
@Serializable
data class Series(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val popularity: Double,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("origin_country")
    val originCountry: List<String>
) {
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
    
    val fullBackdropPath: String
        get() = "https://image.tmdb.org/t/p/w1280$backdropPath"
    
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    val firstAirYear: String
        get() = firstAirDate.take(4)
}

/**
 * Series response from TMDB API
 */
@Serializable
data class SeriesResponse(
    val page: Int,
    val results: List<Series>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

/**
 * Series details with additional information
 */
@Serializable
data class SeriesDetails(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("last_air_date")
    val lastAirDate: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val popularity: Double,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    val genres: List<Genre>,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    val seasons: List<Season>,
    val status: String,
    val tagline: String?,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("origin_country")
    val originCountry: List<String>
) {
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
    
    val fullBackdropPath: String
        get() = "https://image.tmdb.org/t/p/w1280$backdropPath"
    
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    val firstAirYear: String
        get() = firstAirDate.take(4)
    
    val averageRuntime: String
        get() = episodeRunTime.firstOrNull()?.let { "${it}m" } ?: ""
}

/**
 * Season model
 */
@Serializable
data class Season(
    val id: Int,
    @SerialName("season_number")
    val seasonNumber: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("episode_count")
    val episodeCount: Int
) {
    val fullPosterPath: String
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
}

/**
 * Episode model
 */
@Serializable
data class Episode(
    val id: Int,
    @SerialName("episode_number")
    val episodeNumber: Int,
    val name: String,
    val overview: String,
    @SerialName("still_path")
    val stillPath: String?,
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val runtime: Int?
) {
    val fullStillPath: String
        get() = stillPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
    
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    val formattedRuntime: String
        get() = runtime?.let { "${it}m" } ?: ""
}

/**
 * Season details with episodes
 */
@Serializable
data class SeasonDetails(
    val id: Int,
    @SerialName("season_number")
    val seasonNumber: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("air_date")
    val airDate: String?,
    val episodes: List<Episode>
) {
    val fullPosterPath: String
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
}