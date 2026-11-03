package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val articleId: String,
    val savedAt: Long = System.currentTimeMillis(),
    val category: String,
    val title: String,
    val summary: String,
    val imageUrl: String
)

@Entity(tableName = "offline_downloads")
data class OfflineDownloadEntity(
    @PrimaryKey val articleId: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val author: String,
    val publishTime: String,
    val imageUrl: String,
    val downloadedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val body: String,
    val category: String,
    val articleId: String?,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
