package com.example.data.model

data class NewsArticle(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val city: String? = null,
    val author: String = "फर्स्ट इंडिया डिजिटल",
    val publishTime: String,
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String,
    val isBreaking: Boolean = false,
    val isLive: Boolean = false,
    val isVideo: Boolean = false,
    val videoDuration: String? = null,
    val readCount: Int = 1200,
    val bulletPoints: List<String> = emptyList(),
    val isBookmarked: Boolean = false,
    val isDownloadedOffline: Boolean = false,
    val isSponsored: Boolean = false,
    val sponsorName: String? = null
)

data class City(
    val id: String,
    val hindiName: String,
    val englishName: String,
    val stateHindi: String,
    val tempC: Int,
    val weatherCondition: String,
    val aqi: Int,
    val aqiStatus: String
)

data class LiveTickerItem(
    val id: String,
    val timeAgo: String,
    val headline: String,
    val isUrgent: Boolean = true,
    val articleId: String? = null
)

data class VideoNewsItem(
    val id: String,
    val title: String,
    val showName: String,
    val duration: String,
    val viewersCount: String,
    val isLiveStream: Boolean = false,
    val videoUrl: String,
    val thumbnailUrl: String = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=800&q=80",
    val category: String,
    val uploadTime: String
)

data class AppNotification(
    val id: Long = 0,
    val title: String,
    val body: String,
    val category: String,
    val articleId: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

data class NotificationPreferences(
    val breakingNews: Boolean = true,
    val cityAlerts: Boolean = true,
    val cricketScores: Boolean = true,
    val dailyHoroscope: Boolean = false,
    val marketFinance: Boolean = true,
    val quietHoursEnabled: Boolean = false
)

enum class ReadingTextSize(val scale: Float, val labelHindi: String) {
    SMALL(0.85f, "छोटा"),
    MEDIUM(1.0f, "मध्यम"),
    LARGE(1.25f, "बड़ा")
}
