package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val city: String?,
    val author: String,
    val publishTime: String,
    val timestamp: Long,
    val imageUrl: String,
    val isBreaking: Boolean,
    val isLive: Boolean,
    val isVideo: Boolean,
    val videoDuration: String?,
    val readCount: Int,
    val bulletPointsRaw: String, // Comma or newline separated
    val isBookmarked: Boolean = false,
    val isDownloadedOffline: Boolean = false
)
