package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM articles ORDER BY timestamp DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    suspend fun getArticleById(id: String): ArticleEntity?

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY timestamp DESC")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE city = :city ORDER BY timestamp DESC")
    fun getArticlesByCity(city: String): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("UPDATE articles SET isBookmarked = :isBookmarked WHERE id = :articleId")
    suspend fun updateBookmarkStatus(articleId: String, isBookmarked: Boolean)

    @Query("UPDATE articles SET isDownloadedOffline = :isDownloaded WHERE id = :articleId")
    suspend fun updateDownloadStatus(articleId: String, isDownloaded: Boolean)

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE articleId = :articleId")
    suspend fun deleteBookmark(articleId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE articleId = :articleId)")
    fun isBookmarkedFlow(articleId: String): Flow<Boolean>

    // Offline Downloads
    @Query("SELECT * FROM offline_downloads ORDER BY downloadedAt DESC")
    fun getOfflineDownloads(): Flow<List<OfflineDownloadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOfflineDownload(download: OfflineDownloadEntity)

    @Query("DELETE FROM offline_downloads WHERE articleId = :articleId")
    suspend fun deleteOfflineDownload(articleId: String)

    @Query("DELETE FROM offline_downloads")
    suspend fun clearAllOfflineDownloads()

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity): Long

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markNotificationAsRead(id: Long)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllNotificationsAsRead()

    @Query("DELETE FROM notifications")
    suspend fun clearAllNotifications()
}
