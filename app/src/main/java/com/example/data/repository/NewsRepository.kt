package com.example.data.repository

import com.example.data.local.ArticleEntity
import com.example.data.local.BookmarkEntity
import com.example.data.local.NewsDao
import com.example.data.local.NotificationEntity
import com.example.data.local.OfflineDownloadEntity
import com.example.data.model.AppNotification
import com.example.data.model.City
import com.example.data.model.LiveTickerItem
import com.example.data.model.NewsArticle
import com.example.data.model.NotificationPreferences
import com.example.data.model.VideoNewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(private val dao: NewsDao) {

    val allArticles: Flow<List<NewsArticle>> = dao.getAllArticles().map { entities ->
        entities.map { it.toDomainModel() }
    }

    val bookmarkedArticles: Flow<List<BookmarkEntity>> = dao.getBookmarks()
    val offlineArticles: Flow<List<OfflineDownloadEntity>> = dao.getOfflineDownloads()
    val notifications: Flow<List<AppNotification>> = dao.getNotifications().map { list ->
        list.map {
            AppNotification(
                id = it.id,
                title = it.title,
                body = it.body,
                category = it.category,
                articleId = it.articleId,
                timestamp = it.timestamp,
                isRead = it.isRead
            )
        }
    }

    suspend fun getArticleById(id: String): NewsArticle? {
        return dao.getArticleById(id)?.toDomainModel()
    }

    suspend fun toggleBookmark(article: NewsArticle) {
        val willBookmark = !article.isBookmarked
        dao.updateBookmarkStatus(article.id, willBookmark)
        if (willBookmark) {
            dao.insertBookmark(
                BookmarkEntity(
                    articleId = article.id,
                    category = article.category,
                    title = article.title,
                    summary = article.summary,
                    imageUrl = article.imageUrl
                )
            )
        } else {
            dao.deleteBookmark(article.id)
        }
    }

    suspend fun toggleOfflineDownload(article: NewsArticle) {
        val willDownload = !article.isDownloadedOffline
        dao.updateDownloadStatus(article.id, willDownload)
        if (willDownload) {
            dao.insertOfflineDownload(
                OfflineDownloadEntity(
                    articleId = article.id,
                    title = article.title,
                    summary = article.summary,
                    content = article.content,
                    category = article.category,
                    author = article.author,
                    publishTime = article.publishTime,
                    imageUrl = article.imageUrl
                )
            )
        } else {
            dao.deleteOfflineDownload(article.id)
        }
    }

    suspend fun clearAllOffline() {
        dao.clearAllOfflineDownloads()
        // also reset isDownloadedOffline flags
        val current = dao.getOfflineDownloads()
    }

    suspend fun postNotification(
        title: String,
        body: String,
        category: String,
        articleId: String? = null
    ): Long {
        return dao.insertNotification(
            NotificationEntity(
                title = title,
                body = body,
                category = category,
                articleId = articleId
            )
        )
    }

    suspend fun markNotificationRead(id: Long) = dao.markNotificationAsRead(id)
    suspend fun markAllNotificationsRead() = dao.markAllNotificationsAsRead()
    suspend fun clearNotifications() = dao.clearAllNotifications()

    suspend fun seedInitialDataIfEmpty() {
        // Pre-populate realistic Hindi news articles if database is empty
        val sampleArticles = NewsDataProvider.getInitialNewsDataset()
        val entities = sampleArticles.map { it.toEntity() }
        dao.insertArticles(entities)

        // Seed 1 initial notification
        dao.insertNotification(
            NotificationEntity(
                title = "फर्स्ट इंडिया ब्रेकिंग अलर्ट",
                body = "राजस्थान में नए एक्सप्रेसवे का लोकार्पण, जयपुर-दिल्ली का सफर अब केवल 2.5 घंटे में।",
                category = "ब्रेकिंग",
                articleId = "art_1"
            )
        )
    }

    // Helper converters
    private fun ArticleEntity.toDomainModel(): NewsArticle {
        return NewsArticle(
            id = id,
            title = title,
            summary = summary,
            content = content,
            category = category,
            city = city,
            author = author,
            publishTime = publishTime,
            timestamp = timestamp,
            imageUrl = imageUrl,
            isBreaking = isBreaking,
            isLive = isLive,
            isVideo = isVideo,
            videoDuration = videoDuration,
            readCount = readCount,
            bulletPoints = if (bulletPointsRaw.isBlank()) emptyList() else bulletPointsRaw.split("\n"),
            isBookmarked = isBookmarked,
            isDownloadedOffline = isDownloadedOffline
        )
    }

    private fun NewsArticle.toEntity(): ArticleEntity {
        return ArticleEntity(
            id = id,
            title = title,
            summary = summary,
            content = content,
            category = category,
            city = city,
            author = author,
            publishTime = publishTime,
            timestamp = timestamp,
            imageUrl = imageUrl,
            isBreaking = isBreaking,
            isLive = isLive,
            isVideo = isVideo,
            videoDuration = videoDuration,
            readCount = readCount,
            bulletPointsRaw = bulletPoints.joinToString("\n"),
            isBookmarked = isBookmarked,
            isDownloadedOffline = isDownloadedOffline
        )
    }

    companion object {
        fun getCitiesList(): List<City> {
            return RajasthanDistrictsProvider.getAllDistricts()
        }

        fun getLiveTickers(): List<LiveTickerItem> {
            return listOf(
                LiveTickerItem("t1", "अभी-अभी", "जयपुर: विधानसभा के विशेष सत्र की कार्यवाही शुरू, नई ऊर्जा नीति पेश।", true, "art_1"),
                LiveTickerItem("t2", "4 मिनट पहले", "शेयर बाजार: सेंसेक्स में 450 अंकों का उछाल, आईटी और बैंकिंग शेयर चमके।", false, "art_5"),
                LiveTickerItem("t3", "9 मिनट पहले", "क्रिकेट: भारत ने ऑस्ट्रेलिया के खिलाफ पहले टी20 मैच में जीता टॉस, पहले बल्लेबाजी चुनी।", true, "art_6"),
                LiveTickerItem("t4", "15 मिनट पहले", "मौसम विभाग: अगले 48 घंटों में उत्तर भारत के मैदानी इलाकों में तापमान में गिरावट की चेतावनी।", false, "art_7"),
                LiveTickerItem("t5", "22 मिनट पहले", "दिल्ली: प्रदूषण नियंत्रण के लिए ग्रेडेड रिस्पांस एक्शन प्लान लागू।", true, "art_8")
            )
        }

        fun getVideoNews(): List<VideoNewsItem> {
            return listOf(
                VideoNewsItem(
                    id = "v1",
                    title = "फर्स्ट इंडिया LIVE: राजस्थान की सियासत में आज क्या है हलचल? देखें प्राइम बुलेटिन",
                    showName = "सुपरफास्ट 100",
                    duration = "LIVE",
                    viewersCount = "48.2K लाइव दर्शक",
                    isLiveStream = true,
                    videoUrl = "https://example.com/live_broadcast",
                    thumbnailUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=800&q=80",
                    category = "लाइव टीवी",
                    uploadTime = "लाइव प्रसारित"
                ),
                VideoNewsItem(
                    id = "v2",
                    title = "जयपुर मेट्रो फेज-2 का नया रूट मैप: चारदीवारी से सांगानेर तक क्या होगा किराया?",
                    showName = "स्पेशल रिपोर्ट",
                    duration = "08:45",
                    viewersCount = "125K व्यूज",
                    isLiveStream = false,
                    videoUrl = "https://example.com/video_metro",
                    thumbnailUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800&q=80",
                    category = "मेरा शहर",
                    uploadTime = "2 घंटे पहले"
                ),
                VideoNewsItem(
                    id = "v3",
                    title = "वर्ल्ड कप जीत के बाद कप्तान का एक्सक्लूसिव इंटरव्यू: कैसे बनी जीत की रणनीति?",
                    showName = "पिच रिपोर्ट",
                    duration = "14:20",
                    viewersCount = "240K व्यूज",
                    isLiveStream = false,
                    videoUrl = "https://example.com/video_cricket",
                    thumbnailUrl = "https://images.unsplash.com/photo-1540747913346-19e32dc3e97e?w=800&q=80",
                    category = "खेल",
                    uploadTime = "4 घंटे पहले"
                ),
                VideoNewsItem(
                    id = "v4",
                    title = "सोना-चांदी के भाव में भारी फेरबदल: त्योहारी सीजन में खरीदारों के लिए खुशखबरी",
                    showName = "मार्केट इनसाइट",
                    duration = "06:12",
                    viewersCount = "89K व्यूज",
                    isLiveStream = false,
                    videoUrl = "https://example.com/video_market",
                    thumbnailUrl = "https://images.unsplash.com/photo-1610375461246-83df859d849d?w=800&q=80",
                    category = "बिज़नेस",
                    uploadTime = "6 घंटे पहले"
                ),
                VideoNewsItem(
                    id = "v5",
                    title = "सिनेमा स्पेशल: दिवाली रिलीज फिल्मों का फर्स्ट डे बॉक्स ऑफिस प्रेडिक्शन",
                    showName = "ग्लैमर वर्ल्ड",
                    duration = "10:05",
                    viewersCount = "165K व्यूज",
                    isLiveStream = false,
                    videoUrl = "https://example.com/video_bollywood",
                    thumbnailUrl = "https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?w=800&q=80",
                    category = "मनोरंजन",
                    uploadTime = "8 घंटे पहले"
                )
            )
        }
    }
}
