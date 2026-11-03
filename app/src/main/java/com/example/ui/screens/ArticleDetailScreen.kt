package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.TextIncrease
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.NewsArticle
import com.example.data.model.ReadingTextSize
import com.example.data.repository.AdDataProvider
import com.example.ui.components.NewsCard
import com.example.ui.components.ads.InArticleBottomBannerAd
import com.example.ui.components.ads.InArticleMidReadAd
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.HindFontFamily
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import com.example.viewmodel.NewsUiState
import com.example.viewmodel.NewsViewModel

data class UserComment(
    val id: String,
    val author: String,
    val city: String,
    val timeAgo: String,
    val text: String,
    var likesCount: Int = 14,
    var isLiked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    article: NewsArticle,
    uiState: NewsUiState,
    allArticles: List<NewsArticle>,
    viewModel: NewsViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    BackHandler { onBack() }

    // 3-4 Related Articles
    val relatedArticles = remember(article.id, allArticles) {
        val sameCategoryOrCity = allArticles.filter {
            it.id != article.id && (it.category == article.category || it.city == article.city)
        }
        val remainder = allArticles.filter { it.id != article.id && it !in sameCategoryOrCity }
        (sameCategoryOrCity + remainder).take(4)
    }

    // Comment State
    var commentInput by remember { mutableStateOf("") }
    val comments = remember(article.id) {
        mutableStateListOf(
            UserComment(
                id = "c1",
                author = "राजेंद्र विश्नोई",
                city = "बीकानेर",
                timeAgo = "25 मिनट पहले",
                text = "फर्स्ट इंडिया की यह रिपोर्ट बहुत तथ्यपरक है। पश्चिमी राजस्थान के किसानों के लिए यह कदम मील का पत्थर साबित होगा।",
                likesCount = 28
            ),
            UserComment(
                id = "c2",
                author = "डॉ. कविता शर्मा",
                city = "जयपुर",
                timeAgo = "1 घंटा पहले",
                text = "सटीक और निष्पक्ष पत्रकारिता। ऐसे जमीनी विकास और जनहित के मुद्दों को लगातार प्रमुखता से उठाना सराहनीय है।",
                likesCount = 19
            ),
            UserComment(
                id = "c3",
                author = "महेश चौधरी",
                city = "जोधपुर",
                timeAgo = "2 घंटे पहले",
                text = "शानदार पहल! युवाओं को स्थानीय स्तर पर रोजगार मिलेगा तो बड़े शहरों की ओर पलायन रुकेगा।",
                likesCount = 12
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = article.category,
                        fontFamily = MuktaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("article_detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "वापस जाएं"
                        )
                    }
                },
                actions = {
                    // Font Size toggle
                    IconButton(
                        onClick = {
                            val nextSize = when (uiState.textSize) {
                                ReadingTextSize.SMALL -> ReadingTextSize.MEDIUM
                                ReadingTextSize.MEDIUM -> ReadingTextSize.LARGE
                                ReadingTextSize.LARGE -> ReadingTextSize.SMALL
                            }
                            viewModel.setTextSize(nextSize)
                        },
                        modifier = Modifier.testTag("article_font_size_toggle")
                    ) {
                        Icon(
                            imageVector = Icons.Default.TextIncrease,
                            contentDescription = "अक्षर का आकार बदलें",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Offline Download Toggle
                    IconButton(
                        onClick = { viewModel.toggleOfflineDownload(article) },
                        modifier = Modifier.testTag("article_detail_offline_toggle")
                    ) {
                        Icon(
                            imageVector = if (article.isDownloadedOffline) Icons.Default.CloudDone else Icons.Default.FileDownload,
                            contentDescription = "ऑफ़लाइन सहेजें",
                            tint = if (article.isDownloadedOffline) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Bookmark Toggle
                    IconButton(
                        onClick = { viewModel.toggleBookmark(article) },
                        modifier = Modifier.testTag("article_detail_bookmark_toggle")
                    ) {
                        Icon(
                            imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "बुकमार्क",
                            tint = if (article.isBookmarked) BrandRedPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Share
                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "【फर्स्ट इंडिया न्यूज़】\n\n${article.title}\n\n${article.summary}\n\nपूरी खबर पढ़ें: https://firstindianews.com/read/${article.id}"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "खबर शेयर करें"))
                        },
                        modifier = Modifier.testTag("article_detail_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "शेयर करें"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("article_detail_scroll_view")
        ) {
            // Audio Listening Floating Banner if TTS is speaking
            item {
                AnimatedVisibility(visible = uiState.isSpeakingArticle) {
                    Surface(
                        color = BrandRedPrimary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "🔊 ऑडियो बुलेटिन चल रहा है (खबर सुनें)",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            IconButton(
                                onClick = { viewModel.toggleAudioReading("") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "रोकें",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Featured Hero Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                if (article.id == "top_1")
                                    R.drawable.rajasthan_solar_breaking
                                else if (article.imageUrl.isNotBlank())
                                    article.imageUrl
                                else
                                    R.drawable.logo_first_india
                            )
                            .crossfade(true)
                            .error(if (article.id == "top_1") R.drawable.rajasthan_solar_breaking else R.drawable.logo_first_india)
                            .placeholder(if (article.id == "top_1") R.drawable.rajasthan_solar_breaking else R.drawable.logo_first_india)
                            .build(),
                        contentDescription = article.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    // Badges overlay
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (article.isBreaking) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandRedPrimary
                            ) {
                                Text(
                                    text = "🔴 ब्रेकिंग",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        if (article.city != null) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SaffronGold
                            ) {
                                Text(
                                    text = article.city,
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Article Meta and Title
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Sponsored Article Header Disclaimer (if sponsored)
                    if (article.isSponsored) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFFF8E1),
                            border = BorderStroke(1.dp, SaffronGold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .testTag("sponsored_article_disclaimer")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = Color(0xFFE65100),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "⭐ प्रायोजित आलेख (Sponsored Placement)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFFBF360C)
                                    )
                                    Text(
                                        text = "प्रस्तुति: ${article.sponsorName ?: "पार्टनर"} • फर्स्ट इंडिया कमर्शियल पार्टनरशिप",
                                        fontSize = 11.sp,
                                        color = Color(0xFFD84315)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = article.title,
                        fontFamily = MuktaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = (22 * uiState.textSize.scale).sp,
                        lineHeight = (30 * uiState.textSize.scale).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Author, Date & Audio Read Aloud button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (article.isSponsored) "प्रायोजक: ${article.sponsorName ?: "पार्टनर"}" else "ब्यूरो: ${article.author}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (article.isSponsored) Color(0xFFD84315) else MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "प्रकाशित: ${article.publishTime} • ${article.readCount} पाठकों ने पढ़ा",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        // Listen audio button
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = BrandRedPrimary.copy(alpha = 0.12f),
                            modifier = Modifier
                                .clickable {
                                    viewModel.toggleAudioReading("${article.title}। ${article.content}")
                                }
                                .testTag("article_audio_listen_pill")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = if (uiState.isSpeakingArticle) Icons.Default.Stop else Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = BrandRedPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (uiState.isSpeakingArticle) "ऑडियो रोकें" else "खबर सुनें",
                                    color = BrandRedPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Key Highlights (3 Points Box)
                    if (article.bulletPoints.isNotEmpty()) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "मुख्य बिंदु (Key Highlights):",
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = BrandRedPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                article.bulletPoints.forEach { point ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = BrandRedPrimary,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .padding(top = 2.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = point,
                                            fontFamily = HindFontFamily,
                                            fontSize = (14 * uiState.textSize.scale).sp,
                                            lineHeight = (20 * uiState.textSize.scale).sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // In-Article Advertising: Mid-read Ad
                    val inArticleAd = remember(article.id) {
                        AdDataProvider.inArticleAds[Math.abs(article.id.hashCode()) % AdDataProvider.inArticleAds.size]
                    }
                    InArticleMidReadAd(
                        ad = inArticleAd,
                        fontScale = uiState.textSize.scale,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Full Article Body Copy
                    Text(
                        text = article.content,
                        fontFamily = HindFontFamily,
                        fontSize = (16 * uiState.textSize.scale).sp,
                        lineHeight = (26 * uiState.textSize.scale).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Prominent Article Share Card
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("article_share_card")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "खबर उपयोगी लगी? दोस्तों व ग्रुप्स में शेयर करें:",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // WhatsApp Direct Share Button
                                Button(
                                    onClick = {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                "【फर्स्ट इंडिया न्यूज़】\n\n${article.title}\n\n${article.summary}\n\nविस्तार से पढ़ें: https://firstindianews.com/news/${article.id}"
                                            )
                                            type = "text/plain"
                                            setPackage("com.whatsapp")
                                        }
                                        try {
                                            context.startActivity(sendIntent)
                                        } catch (e: Exception) {
                                            val fallback = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(
                                                    Intent.EXTRA_TEXT,
                                                    "【फर्स्ट इंडिया न्यूज़】\n\n${article.title}\n\n${article.summary}\n\nविस्तार से पढ़ें: https://firstindianews.com/news/${article.id}"
                                                )
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(fallback, "खबर शेयर करें"))
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("whatsapp_share_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("व्हाट्सएप", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }

                                // General Share Button
                                Button(
                                    onClick = {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                "【फर्स्ट इंडिया न्यूज़】\n\n${article.title}\n\n${article.summary}\n\nविस्तार से पढ़ें: https://firstindianews.com/news/${article.id}"
                                            )
                                            type = "text/plain"
                                        }
                                        context.startActivity(Intent.createChooser(sendIntent, "खबर शेयर करें"))
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = BrandRedPrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("general_share_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("अन्य शेयर", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }
            }

            // In-Article Advertising: Bottom Sponsor Banner before comments
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    val bottomAd = remember(article.id) {
                        AdDataProvider.inArticleAds[(Math.abs(article.id.hashCode()) + 1) % AdDataProvider.inArticleAds.size]
                    }
                    InArticleBottomBannerAd(ad = bottomAd)
                }
            }

            // 1. READER COMMENT SECTION (Directly after article ends)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .testTag("comments_section")
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Comment,
                                contentDescription = null,
                                tint = BrandRedPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "पाठकों की प्रतिक्रियाएं (${comments.size})",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BrandRedPrimary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "निष्पक्ष राय",
                                color = BrandRedPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Write Comment Input Box
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            OutlinedTextField(
                                value = commentInput,
                                onValueChange = { commentInput = it },
                                placeholder = {
                                    Text(
                                        "इस खबर पर अपनी राय लिखें...",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                maxLines = 3,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("comment_input_field"),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    focusedIndicatorColor = BrandRedPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "नाम: फर्स्ट इंडिया पाठक",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Button(
                                    onClick = {
                                        if (commentInput.isNotBlank()) {
                                            comments.add(
                                                0,
                                                UserComment(
                                                    id = "user_${System.currentTimeMillis()}",
                                                    author = "फर्स्ट इंडिया पाठक",
                                                    city = uiState.selectedCity.hindiName,
                                                    timeAgo = "अभी-अभी",
                                                    text = commentInput.trim(),
                                                    likesCount = 1
                                                )
                                            )
                                            commentInput = ""
                                        }
                                    },
                                    enabled = commentInput.isNotBlank(),
                                    colors = ButtonDefaults.buttonColors(containerColor = BrandRedPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("post_comment_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "टिप्पणी भेजें",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Existing Comments List
                    comments.forEachIndexed { index, comment ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = CircleShape,
                                            color = BrandRedPrimary.copy(alpha = 0.15f),
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = comment.author.first().toString(),
                                                    fontWeight = FontWeight.Bold,
                                                    color = BrandRedPrimary,
                                                    fontSize = 13.sp
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Column {
                                            Text(
                                                text = "${comment.author} (${comment.city})",
                                                fontFamily = MuktaFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = comment.timeAgo,
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    // Like Button
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable {
                                                val current = comments[index]
                                                comments[index] = current.copy(
                                                    isLiked = !current.isLiked,
                                                    likesCount = if (current.isLiked) current.likesCount - 1 else current.likesCount + 1
                                                )
                                            }
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (comment.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = "लाइक",
                                            tint = if (comment.isLiked) BrandRedPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = comment.likesCount.toString(),
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = comment.text,
                                    fontFamily = HindFontFamily,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // 2. RELATED ARTICLES SECTION (Directly after Comment Section)
            if (relatedArticles.isNotEmpty()) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "संबंधित खबरें (3-4 Related Articles)",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "इस विषय और श्रेणी से जुड़ी अन्य महत्वपूर्ण खबरें:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                items(relatedArticles, key = { it.id }) { related ->
                    Box(modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)) {
                        NewsCard(
                            article = related,
                            onArticleClick = { viewModel.openArticle(related) },
                            onToggleBookmark = { viewModel.toggleBookmark(related) },
                            onToggleOfflineDownload = { viewModel.toggleOfflineDownload(related) },
                            onAudioListen = {
                                viewModel.toggleAudioReading("${related.title}। ${related.summary}")
                            },
                            fontScale = uiState.textSize.scale
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}
