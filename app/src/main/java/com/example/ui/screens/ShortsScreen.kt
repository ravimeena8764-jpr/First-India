package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.NewsArticle
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.HindFontFamily
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import com.example.viewmodel.NewsUiState
import com.example.viewmodel.NewsViewModel

@Composable
fun ShortsScreen(
    uiState: NewsUiState,
    articles: List<NewsArticle>,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val shortsArticles = articles.filter { it.bulletPoints.isNotEmpty() }
    val currentArticle = if (shortsArticles.isNotEmpty()) {
        shortsArticles[uiState.currentShortIndex.coerceIn(0, shortsArticles.size - 1)]
    } else {
        articles.firstOrNull()
    }

    if (currentArticle == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("कोई शॉर्ट्स उपलब्ध नहीं हैं")
        }
        return
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("shorts_screen")
    ) {
        // Vertical Animated News Card
        AnimatedContent(
            targetState = currentArticle,
            transitionSpec = {
                (slideInVertically { it } + fadeIn()).togetherWith(
                    slideOutVertically { -it } + fadeOut()
                )
            },
            label = "shorts_anim",
            modifier = Modifier.fillMaxSize()
        ) { article ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Backdrop Image
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark Editorial Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Black.copy(alpha = 0.6f),
                                    Color.Black.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Content Overlay (Title + 3 Key Bullets + Actions)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar inside Short: Category, Counter & Audio
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandRedPrimary
                            ) {
                                Text(
                                    text = "⚡ शॉर्ट्स",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.White.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = article.category,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Index indicator (e.g. 1/8)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = "${uiState.currentShortIndex + 1} / ${shortsArticles.size}",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Bottom Content: Headline + 3 Points Box + Action Buttons
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = article.title,
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = (20 * uiState.textSize.scale).sp,
                            lineHeight = (26 * uiState.textSize.scale).sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // "3 बड़ी बातें" (Key Summary Bullets)
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.12f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "📌 3 बड़ी बातें:",
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = SaffronGold
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                article.bulletPoints.take(3).forEach { point ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = SaffronGold,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .padding(top = 2.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = point,
                                            fontFamily = HindFontFamily,
                                            fontSize = (13 * uiState.textSize.scale).sp,
                                            lineHeight = (18 * uiState.textSize.scale).sp,
                                            color = Color.White.copy(alpha = 0.95f)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Actions Row: Read Full Story + Audio Listen + Bookmark + Share
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { viewModel.openArticle(article) },
                                colors = ButtonDefaults.buttonColors(containerColor = BrandRedPrimary),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.testTag("read_full_news_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("पूरी खबर पढ़ें", color = Color.White, fontWeight = FontWeight.Bold)
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Audio listen
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
                                            viewModel.toggleAudioReading(
                                                "${article.title}। " + article.bulletPoints.joinToString("। ")
                                            )
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Headphones,
                                        contentDescription = "खबर सुनें",
                                        tint = SaffronGold,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }

                                // Bookmark
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable { viewModel.toggleBookmark(article) }
                                ) {
                                    Icon(
                                        imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "सहेजें",
                                        tint = if (article.isBookmarked) BrandRedPrimary else Color.White,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }

                                // Share
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(
                                                    Intent.EXTRA_TEXT,
                                                    "【फर्स्ट इंडिया शॉर्ट्स】\n\n${article.title}\n\n3 बड़ी बातें:\n${article.bulletPoints.joinToString("\n• ")}"
                                                )
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(sendIntent, "शेयर करें"))
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "शेयर",
                                        tint = Color.White,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }

        // Side Navigation Controls: Previous / Next buttons
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier
                    .size(44.dp)
                    .clickable { viewModel.previousShort(shortsArticles.size) }
                    .testTag("prev_short_button")
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "पिछली खबर",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Surface(
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier
                    .size(44.dp)
                    .clickable { viewModel.nextShort(shortsArticles.size) }
                    .testTag("next_short_button")
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "अगली खबर",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}
