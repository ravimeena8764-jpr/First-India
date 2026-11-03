package com.example.ui.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.NewsArticle
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.HindFontFamily
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold

@Composable
fun NewsCard(
    article: NewsArticle,
    onArticleClick: () -> Unit,
    onToggleBookmark: () -> Unit,
    onToggleOfflineDownload: () -> Unit,
    onAudioListen: () -> Unit,
    modifier: Modifier = Modifier,
    fontScale: Float = 1.0f
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = if (article.isSponsored) androidx.compose.foundation.BorderStroke(1.5.dp, SaffronGold) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onArticleClick)
            .testTag("news_card_${article.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image with Category & Breaking Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(
                            if (article.id == "top_1")
                                R.drawable.rajasthan_solar_breaking
                            else
                                article.imageUrl
                        )
                        .crossfade(true)
                        .error(if (article.id == "top_1") R.drawable.rajasthan_solar_breaking else R.drawable.logo_first_india)
                        .placeholder(if (article.id == "top_1") R.drawable.rajasthan_solar_breaking else R.drawable.logo_first_india)
                        .build(),
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                // Top Left: Category & Breaking Badges & Sponsored Tag
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (article.isSponsored) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SaffronGold
                        ) {
                            Text(
                                text = "⭐ प्रायोजित (Sponsored)",
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

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

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = article.category,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    if (article.city != null) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SaffronGold.copy(alpha = 0.9f)
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

                // Top Right: Offline Downloaded Badge if already cached
                if (article.isDownloadedOffline) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E7D32).copy(alpha = 0.9f),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = "ऑफ़लाइन उपलब्ध",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "ऑफ़लाइन",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // News Text & Metadata
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = article.title,
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = (17 * fontScale).sp,
                    lineHeight = (23 * fontScale).sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = article.summary,
                    fontFamily = HindFontFamily,
                    fontSize = (13 * fontScale).sp,
                    lineHeight = (18 * fontScale).sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom Bar: Author, Time Ago, Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Author & Time / Sponsor Label
                    Column {
                        if (article.isSponsored) {
                            Text(
                                text = "प्रायोजक: ${article.sponsorName ?: "पार्टनर"} • ${article.publishTime}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFD84315),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                        } else {
                            Text(
                                text = "${article.author} • ${article.publishTime}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    // Action Icons: Audio, Download Offline, Bookmark, Share
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Audio listen button
                        IconButton(
                            onClick = onAudioListen,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("audio_listen_${article.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = "खबर सुनें",
                                tint = BrandRedPrimary,
                                modifier = Modifier.size(19.dp)
                            )
                        }

                        // Offline save button
                        IconButton(
                            onClick = onToggleOfflineDownload,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("offline_download_${article.id}")
                        ) {
                            Icon(
                                imageVector = if (article.isDownloadedOffline) Icons.Default.CloudDone else Icons.Default.FileDownload,
                                contentDescription = if (article.isDownloadedOffline) "ऑफ़लाइन सहेजा गया" else "ऑफ़लाइन डाउनलोड करें",
                                tint = if (article.isDownloadedOffline) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(19.dp)
                            )
                        }

                        // Bookmark button
                        IconButton(
                            onClick = onToggleBookmark,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("bookmark_${article.id}")
                        ) {
                            Icon(
                                imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "बुकमार्क",
                                tint = if (article.isBookmarked) BrandRedPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(19.dp)
                            )
                        }

                        // WhatsApp / System Share button
                        IconButton(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "【फर्स्ट इंडिया न्यूज़】\n\n${article.title}\n\nविस्तार से पढ़ें: https://firstindianews.com/news/${article.id}"
                                    )
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "खबर शेयर करें")
                                context.startActivity(shareIntent)
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("share_${article.id}")
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
