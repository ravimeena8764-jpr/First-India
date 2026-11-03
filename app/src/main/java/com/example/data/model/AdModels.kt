package com.example.data.model

enum class AdPlacementType {
    DISPLAY_BANNER,
    NATIVE_FEED,
    IN_ARTICLE_MID,
    IN_ARTICLE_BOTTOM,
    LOCAL_CITY,
    VIDEO_PRE_ROLL,
    VIDEO_MID_ROLL,
    SPONSORED_NEWS
}

data class DisplayBannerAd(
    val id: String,
    val brandName: String,
    val headline: String,
    val subText: String,
    val badgeText: String = "विज्ञापन | AD",
    val bannerImageUrl: String,
    val ctaText: String = "अभी देखें",
    val targetUrl: String = "https://firstindianews.com/ad",
    val accentColorHex: Long = 0xFFD32F2F
)

data class NativeFeedAd(
    val id: String,
    val sponsorName: String,
    val title: String,
    val description: String,
    val badgeText: String = "प्रायोजित (Sponsored)",
    val imageUrl: String,
    val logoUrl: String = "",
    val rating: Float = 4.8f,
    val ctaText: String = "विस्तार से जानें",
    val landingUrl: String = "https://firstindianews.com/partner",
    val categoryTag: String = "पार्टनर कंटेंट"
)

data class InArticleAd(
    val id: String,
    val brandName: String,
    val headline: String,
    val bodyText: String,
    val badgeText: String = "पार्टनर संदेश | AD",
    val imageUrl: String,
    val ctaText: String = "ऑफर प्राप्त करें",
    val promoCode: String? = null,
    val targetUrl: String = "https://firstindianews.com/offers"
)

data class LocalCityAd(
    val id: String,
    val cityHindi: String,
    val businessName: String,
    val categoryHindi: String,
    val headline: String,
    val offerText: String,
    val address: String,
    val phone: String,
    val whatsappNumber: String,
    val imageUrl: String,
    val badgeText: String = "📍 स्थानीय विज्ञापन"
)

data class VideoAd(
    val id: String,
    val brandName: String,
    val headline: String,
    val subHeadline: String,
    val durationSeconds: Int = 15,
    val skippableAfterSeconds: Int = 5,
    val videoThumbnailUrl: String,
    val ctaText: String = "वेबसाइट देखें",
    val actionUrl: String = "https://firstindianews.com/sponsor",
    val isMidRoll: Boolean = false,
    val midRollCueTimeSeconds: Int = 12
)
