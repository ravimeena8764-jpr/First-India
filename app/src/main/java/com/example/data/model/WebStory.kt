package com.example.data.model

data class WebStorySlide(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val tag: String? = null
)

data class WebStory(
    val id: String,
    val title: String,
    val category: String,
    val coverImageUrl: String,
    val slidesCount: Int,
    val slides: List<WebStorySlide>,
    val author: String = "फर्स्ट इंडिया टीम",
    val timeAgo: String = "1 घंटा पहले",
    val viewsCount: String = "24K"
)
