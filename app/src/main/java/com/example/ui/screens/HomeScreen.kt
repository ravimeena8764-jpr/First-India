package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.NewsArticle
import com.example.data.repository.AdDataProvider
import com.example.ui.components.BreakingNewsTicker
import com.example.ui.components.CategoryTabsRow
import com.example.ui.components.NewsCard
import com.example.ui.components.PublicPollWidget
import com.example.ui.components.SportsMatchWidget
import com.example.ui.components.StockMarketWidget
import com.example.ui.components.WebStoriesRow
import com.example.ui.components.WebStoryViewerDialog
import com.example.ui.components.ads.DisplayBannerAdCard
import com.example.ui.components.ads.NativeInFeedAdCard
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.HindFontFamily
import com.example.ui.theme.MuktaFontFamily
import com.example.viewmodel.NewsUiState
import com.example.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    uiState: NewsUiState,
    articles: List<NewsArticle>,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Filter articles by category and search query
    val filteredArticles = articles.filter { article ->
        val matchesCategory = when (uiState.selectedCategory) {
            "सभी" -> true
            "शीर्ष समाचार" -> article.isBreaking || article.category == "शीर्ष समाचार"
            "मेरा शहर" -> article.city != null || article.category == "मेरा शहर"
            else -> article.category == uiState.selectedCategory
        }

        val matchesSearch = if (uiState.searchQuery.isBlank()) {
            true
        } else {
            article.title.contains(uiState.searchQuery, ignoreCase = true) ||
                    article.summary.contains(uiState.searchQuery, ignoreCase = true) ||
                    (article.city?.contains(uiState.searchQuery, ignoreCase = true) == true)
        }

        matchesCategory && matchesSearch
    }

    // Identify top 1 featured breaking news article
    val breakingArticle = articles.firstOrNull { it.isBreaking } ?: articles.firstOrNull()

    Column(modifier = modifier.fillMaxSize()) {
        // Breaking News Ticker (LIVE)
        BreakingNewsTicker(
            tickers = viewModel.liveTickers,
            onTickerClick = { ticker ->
                ticker.articleId?.let { id ->
                    articles.find { it.id == id }?.let { viewModel.openArticle(it) }
                }
            }
        )

        // Category Tab Row (with Category Icon & Chips)
        CategoryTabsRow(
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = { viewModel.selectCategory(it) },
            onOpenAllCategories = { viewModel.openSidebar(true) }
        )

        // Quick Pull-to-refresh / live status indicator
        if (uiState.isRefreshing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = BrandRedPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "लाइव अपडेट प्राप्त हो रहे हैं...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("news_feed_list"),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Category-specific Widgets
            // 1. Stock Market Widget in Business Category
            if (uiState.selectedCategory == "बिज़नेस") {
                item {
                    StockMarketWidget()
                }
            }

            // 2. Sports Match Widget in Sports Category
            if (uiState.selectedCategory == "खेल") {
                item {
                    SportsMatchWidget()
                }
            }

            // 1 FEATURED BREAKING NEWS HERO SECTION (Big Story on Home Page)
            if (breakingArticle != null && uiState.searchQuery.isBlank() && uiState.selectedCategory == "सभी") {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.openArticle(breakingArticle) }
                            .testTag("featured_breaking_news_card")
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Large Hero Image with Breaking Tag and Fallback
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(
                                            if (breakingArticle.id == "top_1")
                                                R.drawable.rajasthan_solar_breaking
                                            else if (breakingArticle.imageUrl.isNotBlank())
                                                breakingArticle.imageUrl
                                            else
                                                R.drawable.rajasthan_solar_breaking
                                        )
                                        .crossfade(true)
                                        .error(R.drawable.rajasthan_solar_breaking)
                                        .placeholder(R.drawable.rajasthan_solar_breaking)
                                        .build(),
                                    contentDescription = breakingArticle.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )

                                // Top Badges: Red Breaking Badge + Right Now Tag
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = BrandRedPrimary
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(7.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = "⚡ बड़ी ब्रेकिंग न्यूज़",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color.Black.copy(alpha = 0.75f)
                                    ) {
                                        Text(
                                            text = "अभी-अभी",
                                            color = Color(0xFFFFD54F),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                // Bottom Shadow Gradient for Text Contrast
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .align(Alignment.BottomStart)
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                            )
                                        )
                                )
                            }

                            // Breaking News Text & Attribution
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = breakingArticle.title,
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (18 * uiState.textSize.scale).sp,
                                    lineHeight = (25 * uiState.textSize.scale).sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = breakingArticle.summary,
                                    fontFamily = HindFontFamily,
                                    fontSize = (13 * uiState.textSize.scale).sp,
                                    lineHeight = (19 * uiState.textSize.scale).sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${breakingArticle.author} • ${breakingArticle.publishTime}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // Audio listen
                                        IconButton(
                                            onClick = {
                                                viewModel.toggleAudioReading("${breakingArticle.title}। ${breakingArticle.summary}")
                                            },
                                            modifier = Modifier
                                                .size(34.dp)
                                                .testTag("big_story_audio")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Headphones,
                                                contentDescription = "खबर सुनें",
                                                tint = BrandRedPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        // Bookmark
                                        IconButton(
                                            onClick = { viewModel.toggleBookmark(breakingArticle) },
                                            modifier = Modifier
                                                .size(34.dp)
                                                .testTag("big_story_bookmark")
                                        ) {
                                            Icon(
                                                imageVector = if (breakingArticle.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                                contentDescription = "बुकमार्क",
                                                tint = if (breakingArticle.isBookmarked) BrandRedPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        // Share Button on Big Story
                                        IconButton(
                                            onClick = {
                                                val sendIntent = Intent().apply {
                                                    action = Intent.ACTION_SEND
                                                    putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "【फर्स्ट इंडिया बड़ी खबर】\n\n${breakingArticle.title}\n\n${breakingArticle.summary}\n\nविस्तार से पढ़ें: https://firstindianews.com/read/${breakingArticle.id}"
                                                    )
                                                    type = "text/plain"
                                                }
                                                context.startActivity(Intent.createChooser(sendIntent, "बड़ी खबर शेयर करें"))
                                            },
                                            modifier = Modifier
                                                .size(34.dp)
                                                .testTag("big_story_share")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = "शेयर करें",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Top Display Banner Advertisement
            item(key = "home_top_display_banner") {
                DisplayBannerAdCard(ad = AdDataProvider.displayBannerAds[0])
            }

            // News List Items
            if (filteredArticles.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = "कोई परिणाम नहीं",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (uiState.searchQuery.isNotBlank())
                                "'${uiState.searchQuery}' के लिए कोई समाचार नहीं मिला"
                            else
                                "इस श्रेणी में अभी कोई समाचार उपलब्ध नहीं है",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.setSearchQuery("")
                                viewModel.selectCategory("सभी")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandRedPrimary)
                        ) {
                            Text("सभी समाचार देखें", color = Color.White)
                        }
                    }
                }
            } else {
                // If on "सभी" and featured breaking is shown, skip duplicate of breakingArticle
                val articlesToShow = if (uiState.selectedCategory == "सभी" && breakingArticle != null && uiState.searchQuery.isBlank()) {
                    filteredArticles.filter { it.id != breakingArticle.id }
                } else {
                    filteredArticles
                }

                articlesToShow.forEachIndexed { index, article ->
                    item(key = article.id) {
                        NewsCard(
                            article = article,
                            onArticleClick = { viewModel.openArticle(article) },
                            onToggleBookmark = { viewModel.toggleBookmark(article) },
                            onToggleOfflineDownload = { viewModel.toggleOfflineDownload(article) },
                            onAudioListen = {
                                viewModel.toggleAudioReading("${article.title}। ${article.summary}")
                            },
                            fontScale = uiState.textSize.scale
                        )
                    }

                    // In-Feed Display Banner Ad after 2nd article
                    if (index == 1) {
                        item(key = "home_feed_display_banner_1") {
                            DisplayBannerAdCard(ad = AdDataProvider.displayBannerAds[1])
                        }
                    }

                    // Web Story section on home page after 4 articles (index == 3)
                    if (index == 3 && uiState.searchQuery.isBlank() && uiState.selectedCategory == "सभी") {
                        item(key = "home_web_stories_section") {
                            WebStoriesRow(
                                stories = viewModel.webStories,
                                onStoryClick = { viewModel.openWebStory(it) }
                            )
                        }

                        // Public Poll section on home page
                        item(key = "home_public_poll_section") {
                            PublicPollWidget(
                                poll = uiState.activePublicPoll,
                                onVote = { viewModel.voteInPoll(it) },
                                onResetVote = { viewModel.resetPollVote() },
                                fontScale = uiState.textSize.scale
                            )
                        }
                    }

                    // In-Feed Native Ad 1 after 5th article
                    if (index == 4) {
                        item(key = "home_native_ad_1") {
                            NativeInFeedAdCard(
                                ad = AdDataProvider.nativeFeedAds[0],
                                fontScale = uiState.textSize.scale
                            )
                        }
                    }

                    // In-Feed Native Ad 2 after 8th article
                    if (index == 7) {
                        item(key = "home_native_ad_2") {
                            NativeInFeedAdCard(
                                ad = AdDataProvider.nativeFeedAds[1],
                                fontScale = uiState.textSize.scale
                            )
                        }
                    }
                }

                // Fallback for short lists on home page
                if (articlesToShow.isNotEmpty() && articlesToShow.size < 4 && uiState.searchQuery.isBlank() && uiState.selectedCategory == "सभी") {
                    item(key = "home_web_stories_fallback") {
                        WebStoriesRow(
                            stories = viewModel.webStories,
                            onStoryClick = { viewModel.openWebStory(it) }
                        )
                    }
                    item(key = "home_public_poll_fallback") {
                        PublicPollWidget(
                            poll = uiState.activePublicPoll,
                            onVote = { viewModel.voteInPoll(it) },
                            onResetVote = { viewModel.resetPollVote() },
                            fontScale = uiState.textSize.scale
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }

    // Fullscreen Web Story Viewer Modal
    uiState.selectedWebStory?.let { story ->
        WebStoryViewerDialog(
            story = story,
            onDismiss = { viewModel.openWebStory(null) }
        )
    }
}
