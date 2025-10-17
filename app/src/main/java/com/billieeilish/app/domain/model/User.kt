package com.billieeilish.app.domain.model

import kotlinx.serialization.Serializable

/**
 * User data model
 */
@Serializable
data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis()
)

/**
 * User preferences model
 */
@Serializable
data class UserPreferences(
    val userId: String,
    val language: String = "ar",
    val isDarkMode: Boolean = true,
    val videoQuality: VideoQuality = VideoQuality.AUTO,
    val videoProvider: VideoProvider = VideoProvider.SUPER_EMBED,
    val notificationsEnabled: Boolean = true,
    val autoPlay: Boolean = true
)

/**
 * Video quality options
 */
enum class VideoQuality(val displayName: String, val value: String) {
    AUTO("تلقائي", "auto"),
    HD_1080("1080p", "1080"),
    HD_720("720p", "720"),
    SD_480("480p", "480"),
    SD_360("360p", "360")
}

/**
 * Video provider options
 */
enum class VideoProvider(val displayName: String, val baseUrl: String) {
    SUPER_EMBED("SuperEmbed", "https://multiembed.mov"),
    VIDSRC("VidSrc", "https://vidsrc.me"),
    EMBED_SU("Embed.su", "https://embed.su")
}

/**
 * Favorite item model
 */
@Serializable
data class FavoriteItem(
    val id: String,
    val contentId: Int,
    val contentType: ContentType,
    val title: String,
    val posterPath: String?,
    val rating: Double,
    val releaseDate: String,
    val addedAt: Long = System.currentTimeMillis()
) {
    val fullPosterPath: String
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
}

/**
 * Content type enum
 */
enum class ContentType {
    MOVIE,
    SERIES,
    ANIME
}