package com.billieeilish.app.data.repository

import com.billieeilish.app.domain.model.TvChannel
import com.billieeilish.app.domain.repository.IptvRepository
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of IPTV repository that fetches channels from iptv-org GitHub repository
 */
@Singleton
class IptvRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : IptvRepository {
    
    companion object {
        private const val IPTV_PLAYLIST_URL = "https://iptv-org.github.io/iptv/index.m3u"
        private const val ARABIC_CHANNELS_URL = "https://iptv-org.github.io/iptv/languages/ara.m3u"
        private const val COUNTRIES_CHANNELS_URL = "https://iptv-org.github.io/iptv/countries/"
    }
    
    override fun getChannels(): Flow<Resource<List<TvChannel>>> = flow {
        try {
            emit(Resource.Loading())
            
            val request = Request.Builder()
                .url(ARABIC_CHANNELS_URL)
                .build()
            
            val response = okHttpClient.newCall(request).execute()
            
            if (response.isSuccessful) {
                val m3uContent = response.body?.string() ?: ""
                val channels = parseM3uPlaylist(m3uContent)
                emit(Resource.Success(channels))
            } else {
                emit(Resource.Error("Failed to fetch channels: ${response.code}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.message}"))
        }
    }
    
    override fun getChannelsByCountry(country: String): Flow<Resource<List<TvChannel>>> = flow {
        try {
            emit(Resource.Loading())
            
            val url = "${COUNTRIES_CHANNELS_URL}${country.lowercase()}.m3u"
            val request = Request.Builder()
                .url(url)
                .build()
            
            val response = okHttpClient.newCall(request).execute()
            
            if (response.isSuccessful) {
                val m3uContent = response.body?.string() ?: ""
                val channels = parseM3uPlaylist(m3uContent)
                emit(Resource.Success(channels))
            } else {
                emit(Resource.Error("Failed to fetch channels for country: $country"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.message}"))
        }
    }
    
    override fun getChannelsByCategory(category: String): Flow<Resource<List<TvChannel>>> = flow {
        try {
            emit(Resource.Loading())
            
            val url = "https://iptv-org.github.io/iptv/categories/${category.lowercase()}.m3u"
            val request = Request.Builder()
                .url(url)
                .build()
            
            val response = okHttpClient.newCall(request).execute()
            
            if (response.isSuccessful) {
                val m3uContent = response.body?.string() ?: ""
                val channels = parseM3uPlaylist(m3uContent)
                emit(Resource.Success(channels))
            } else {
                emit(Resource.Error("Failed to fetch channels for category: $category"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.message}"))
        }
    }
    
    override fun searchChannels(query: String): Flow<Resource<List<TvChannel>>> = flow {
        try {
            emit(Resource.Loading())
            
            // Get all channels first
            getChannels().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val filteredChannels = resource.data?.filter { channel ->
                            channel.name.contains(query, ignoreCase = true) ||
                            channel.country.contains(query, ignoreCase = true) ||
                            channel.category.contains(query, ignoreCase = true)
                        } ?: emptyList()
                        emit(Resource.Success(filteredChannels))
                    }
                    is Resource.Error -> emit(Resource.Error(resource.message ?: "Search failed"))
                    is Resource.Loading -> emit(Resource.Loading())
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Search error: ${e.message}"))
        }
    }
    
    /**
     * Parse M3U playlist content and extract channel information
     */
    private fun parseM3uPlaylist(content: String): List<TvChannel> {
        val channels = mutableListOf<TvChannel>()
        val lines = content.split("\n")
        
        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()
            
            if (line.startsWith("#EXTINF:")) {
                // Parse channel info from EXTINF line
                val channelInfo = parseExtinfLine(line)
                
                // Get the next non-empty line which should be the URL
                var urlLine = ""
                var j = i + 1
                while (j < lines.size && urlLine.isEmpty()) {
                    val nextLine = lines[j].trim()
                    if (nextLine.isNotEmpty() && !nextLine.startsWith("#")) {
                        urlLine = nextLine
                        break
                    }
                    j++
                }
                
                if (urlLine.isNotEmpty() && channelInfo != null) {
                    val channel = TvChannel(
                        id = generateChannelId(channelInfo.name, urlLine),
                        name = channelInfo.name,
                        logo = channelInfo.logo,
                        group = channelInfo.group ?: "General",
                        url = urlLine,
                        country = channelInfo.country ?: "Unknown",
                        language = channelInfo.language ?: "Unknown",
                        category = channelInfo.category ?: "General",
                        isNsfw = false
                    )
                    channels.add(channel)
                }
                
                i = j
            }
            i++
        }
        
        return channels
    }
    
    /**
     * Parse EXTINF line to extract channel information
     */
    private fun parseExtinfLine(line: String): ChannelInfo? {
        try {
            // Example: #EXTINF:-1 tvg-id="AlJazeera.qa" tvg-logo="https://..." group-title="News",Al Jazeera
            val regex = """#EXTINF:.*?(?:tvg-id="([^"]*)")?.*?(?:tvg-logo="([^"]*)")?.*?(?:group-title="([^"]*)")?.*?,(.*)""".toRegex()
            val matchResult = regex.find(line)
            
            return if (matchResult != null) {
                val (tvgId, logo, group, name) = matchResult.destructured
                ChannelInfo(
                    name = name.trim(),
                    logo = logo.takeIf { it.isNotEmpty() },
                    group = group.takeIf { it.isNotEmpty() },
                    country = extractCountryFromTvgId(tvgId),
                    language = "Arabic", // Default for Arabic channels
                    category = mapGroupToCategory(group)
                )
            } else {
                // Fallback parsing
                val nameIndex = line.lastIndexOf(',')
                if (nameIndex != -1 && nameIndex < line.length - 1) {
                    ChannelInfo(
                        name = line.substring(nameIndex + 1).trim(),
                        logo = null,
                        group = "General",
                        country = "Unknown",
                        language = "Arabic",
                        category = "General"
                    )
                } else null
            }
        } catch (e: Exception) {
            return null
        }
    }
    
    private fun extractCountryFromTvgId(tvgId: String): String? {
        if (tvgId.isEmpty()) return null
        val parts = tvgId.split(".")
        return parts.lastOrNull()?.uppercase()
    }
    
    private fun mapGroupToCategory(group: String?): String {
        return when (group?.lowercase()) {
            "news" -> "News"
            "sports" -> "Sports"
            "entertainment" -> "Entertainment"
            "movies" -> "Movies"
            "kids" -> "Kids"
            "music" -> "Music"
            "documentary" -> "Documentary"
            "religious" -> "Religious"
            else -> "General"
        }
    }
    
    private fun generateChannelId(name: String, url: String): String {
        return "${name.hashCode()}_${url.hashCode()}".replace("-", "")
    }
    
    private data class ChannelInfo(
        val name: String,
        val logo: String?,
        val group: String?,
        val country: String?,
        val language: String?,
        val category: String?
    )
}