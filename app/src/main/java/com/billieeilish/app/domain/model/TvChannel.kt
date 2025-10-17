package com.billieeilish.app.domain.model

import kotlinx.serialization.Serializable

/**
 * TV Channel data model
 */
@Serializable
data class TvChannel(
    val id: String,
    val name: String,
    val logo: String?,
    val group: String,
    val url: String,
    val country: String,
    val language: String,
    val category: String,
    val isNsfw: Boolean = false
) {
    val displayName: String
        get() = name.replace("_", " ").trim()
    
    val countryFlag: String
        get() = getCountryFlag(country)
    
    private fun getCountryFlag(countryCode: String): String {
        return when (countryCode.uppercase()) {
            "SA" -> "🇸🇦"
            "AE" -> "🇦🇪"
            "EG" -> "🇪🇬"
            "JO" -> "🇯🇴"
            "LB" -> "🇱🇧"
            "SY" -> "🇸🇾"
            "IQ" -> "🇮🇶"
            "KW" -> "🇰🇼"
            "QA" -> "🇶🇦"
            "BH" -> "🇧🇭"
            "OM" -> "🇴🇲"
            "YE" -> "🇾🇪"
            "MA" -> "🇲🇦"
            "TN" -> "🇹🇳"
            "DZ" -> "🇩🇿"
            "LY" -> "🇱🇾"
            "SD" -> "🇸🇩"
            "US" -> "🇺🇸"
            "GB" -> "🇬🇧"
            "FR" -> "🇫🇷"
            "DE" -> "🇩🇪"
            "IT" -> "🇮🇹"
            "ES" -> "🇪🇸"
            "TR" -> "🇹🇷"
            "IN" -> "🇮🇳"
            "PK" -> "🇵🇰"
            else -> "🌍"
        }
    }
}

/**
 * TV Channel category
 */
enum class TvChannelCategory(val displayName: String) {
    NEWS("الأخبار"),
    SPORTS("الرياضة"),
    ENTERTAINMENT("الترفيه"),
    MOVIES("الأفلام"),
    SERIES("المسلسلات"),
    KIDS("الأطفال"),
    MUSIC("الموسيقى"),
    DOCUMENTARY("الوثائقيات"),
    RELIGIOUS("الدينية"),
    COOKING("الطبخ"),
    LIFESTYLE("نمط الحياة"),
    EDUCATION("التعليمية"),
    GENERAL("عامة")
}