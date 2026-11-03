package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.VideoNewsItem
import com.example.data.repository.AdDataProvider
import com.example.ui.components.ads.DisplayBannerAdCard
import com.example.ui.components.ads.VideoAdPlayerDialog
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedGlow
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import com.example.viewmodel.NewsUiState
import com.example.viewmodel.NewsViewModel
import kotlinx.coroutines.delay

val LiveChatComments = listOf(
    "सुरेश मीणा (जयपुर): सटीक कवरेज फर्स्ट इंडिया की!",
    "राकेश भाटी (जोधपुर): जोधपुर फेस्टिवल पर खास रिपोर्ट दिखाएं",
    "प्रदीप सिंह: सोलर पार्क से राजस्थान के युवाओं को बहुत लाभ होगा",
    "सुनीता शर्मा: सबसे तेज और निष्पक्ष समाचार",
    "दिनेश कुमार (कोटा): शिक्षा नगरी से राम-राम"
)

@Composable
fun VideoScreen(
    uiState: NewsUiState,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    var chatIndex by remember { mutableStateOf(0) }
    var selectedVideoForAdPlayer by remember { mutableStateOf<VideoNewsItem?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3500)
            chatIndex = (chatIndex + 1) % LiveChatComments.size
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("video_screen"),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // First India Live TV Broadcast Mock Player with Studio Feature Image
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("live_tv_player_card")
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Video Viewport with Studio Feature Backdrop Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    ) {
                        // High-res Studio Newsroom Feature Image
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=1000&q=80",
                            contentDescription = "लाइव टीवी स्टूडियो प्रसारण",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Dark gradient overlay to ensure UI elements pop
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Black.copy(alpha = 0.55f),
                                            Color.Black.copy(alpha = 0.25f),
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                        )

                        // Channel Watermark / Studio Graphic
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = BrandRedPrimary
                                ) {
                                    Text(
                                        text = "1st",
                                        fontFamily = MuktaFontFamily,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "FIRST INDIA NEWS",
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "24x7 लाइव न्यूज़ ब्रॉडकास्ट • स्टूडियो 1",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }

                        // Top Bar inside Player: LIVE badge, Viewer Count
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Red LIVE tag
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandRedGlow
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FiberManualRecord,
                                        contentDescription = "Live",
                                        tint = Color.White,
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "LIVE TV",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }

                            // Live Viewers Count
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.Black.copy(alpha = 0.65f)
                            ) {
                                Text(
                                    text = "👁 48.2K दर्शक लाइव",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Bottom Video Controls inside Player
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                                    )
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { viewModel.toggleLiveVideoPlay() },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (uiState.isLiveVideoPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "प्ले/पॉज़",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.toggleLiveVideoMute() },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (uiState.isLiveVideoMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                        contentDescription = "म्यूट/अनम्यूट",
                                        tint = Color.White
                                    )
                                }

                                Text(
                                    text = if (uiState.isLiveVideoPlaying) "लाइव स्ट्रीम चालू" else "लाइव स्ट्रीम रुकी हुई",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SaffronGold
                            ) {
                                Text(
                                    text = "HD 1080p",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Live Broadcast Description
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Text(
                            text = "फर्स्ट इंडिया सुपरफास्ट 100: देश, दुनिया और राजस्थान की तमाम बड़ी खबरें लाइव",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Simulated Live Chat Ticker
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF212121),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "💬 लाइव प्रतिक्रिया:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronGold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = LiveChatComments[chatIndex],
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // Display Banner Ad in Video Screen
        item(key = "video_screen_banner_top") {
            DisplayBannerAdCard(ad = AdDataProvider.displayBannerAds[2])
        }

        // Section Title: Video Bulletins
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "खास वीडियो बुलेटिन व इंटरव्यू",
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${viewModel.videosList.size} वीडियो",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Video Bulletins Cards with Featured Images & Pre-roll/Mid-roll ad demo
        viewModel.videosList.forEachIndexed { index, video ->
            item(key = video.id) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedVideoForAdPlayer = video
                        }
                        .testTag("video_item_${video.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Video Thumbnail with Featured Image & Play Overlay
                        Box(
                            modifier = Modifier
                                .size(width = 120.dp, height = 75.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BrandRedDark)
                        ) {
                            // Featured Image loaded via Coil AsyncImage
                            AsyncImage(
                                model = video.thumbnailUrl,
                                contentDescription = video.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Subtle dark gradient for play icon and duration visibility
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                                        )
                                    )
                            )

                            // Play icon
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.matchParentSize()
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.Black.copy(alpha = 0.55f),
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "प्ले",
                                        tint = Color.White,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }

                            // Duration badge
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.Black.copy(alpha = 0.8f),
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(4.dp)
                            ) {
                                Text(
                                    text = video.duration,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Title & Metadata
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandRedPrimary.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = video.showName,
                                    color = BrandRedPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = video.title,
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = (18 * uiState.textSize.scale).sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${video.viewersCount} • ${video.uploadTime}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            // In-feed display banner ad after 2nd video bulletin
            if (index == 1) {
                item(key = "video_screen_infeed_banner") {
                    DisplayBannerAdCard(ad = AdDataProvider.displayBannerAds[0])
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }

    // Interactive Video Player with Pre-roll & Mid-roll Ads
    selectedVideoForAdPlayer?.let { video ->
        VideoAdPlayerDialog(
            video = video,
            onDismiss = { selectedVideoForAdPlayer = null }
        )
    }
}
