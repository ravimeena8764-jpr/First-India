package com.example.ui.components.ads

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.model.VideoAd
import com.example.data.model.VideoNewsItem
import com.example.data.repository.AdDataProvider
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedGlow
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.HindFontFamily
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import kotlinx.coroutines.delay

enum class VideoPlayerAdState {
    PRE_ROLL,
    PLAYING_CONTENT,
    MID_ROLL,
    COMPLETED
}

@Composable
fun VideoAdPlayerDialog(
    video: VideoNewsItem,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var adState by remember { mutableStateOf(VideoPlayerAdState.PRE_ROLL) }

    // Pre-roll and Mid-roll ad data
    val preRollAd = remember { AdDataProvider.videoPreRollAd }
    val midRollAd = remember { AdDataProvider.videoMidRollAd }

    // Ad Countdown Timers
    var adSecondsRemaining by remember { mutableIntStateOf(preRollAd.durationSeconds) }
    var skipSecondsRemaining by remember { mutableIntStateOf(preRollAd.skippableAfterSeconds) }
    var canSkipAd by remember { mutableStateOf(false) }

    // Main Video Playback Timers
    var isPlaying by remember { mutableStateOf(true) }
    var videoCurrentSeconds by remember { mutableFloatStateOf(0f) }
    val videoTotalSeconds = 45f // 45 seconds simulated news clip
    val midRollCueTime = 20f // Cue marker at 20 seconds
    var midRollTriggered by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }

    // Ticker for Pre-roll Ad
    LaunchedEffect(adState) {
        if (adState == VideoPlayerAdState.PRE_ROLL) {
            adSecondsRemaining = preRollAd.durationSeconds
            skipSecondsRemaining = preRollAd.skippableAfterSeconds
            canSkipAd = false

            while (adSecondsRemaining > 0 && adState == VideoPlayerAdState.PRE_ROLL) {
                delay(1000)
                adSecondsRemaining--
                if (skipSecondsRemaining > 0) {
                    skipSecondsRemaining--
                } else {
                    canSkipAd = true
                }
            }
            if (adState == VideoPlayerAdState.PRE_ROLL) {
                adState = VideoPlayerAdState.PLAYING_CONTENT
            }
        } else if (adState == VideoPlayerAdState.MID_ROLL) {
            adSecondsRemaining = midRollAd.durationSeconds
            skipSecondsRemaining = midRollAd.skippableAfterSeconds
            canSkipAd = false

            while (adSecondsRemaining > 0 && adState == VideoPlayerAdState.MID_ROLL) {
                delay(1000)
                adSecondsRemaining--
                if (skipSecondsRemaining > 0) {
                    skipSecondsRemaining--
                } else {
                    canSkipAd = true
                }
            }
            if (adState == VideoPlayerAdState.MID_ROLL) {
                adState = VideoPlayerAdState.PLAYING_CONTENT
            }
        }
    }

    // Ticker for Main Video Playback
    LaunchedEffect(adState, isPlaying) {
        if (adState == VideoPlayerAdState.PLAYING_CONTENT && isPlaying) {
            while (videoCurrentSeconds < videoTotalSeconds && adState == VideoPlayerAdState.PLAYING_CONTENT && isPlaying) {
                delay(500)
                videoCurrentSeconds += 0.5f

                // Trigger Mid-Roll Ad automatically at cue point if not triggered yet
                if (!midRollTriggered && videoCurrentSeconds >= midRollCueTime) {
                    midRollTriggered = true
                    adState = VideoPlayerAdState.MID_ROLL
                    break
                }
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .testTag("video_ad_player_dialog"),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 20.dp)
            ) {
                // Top Exit & Info Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BrandRedPrimary
                        ) {
                            Text(
                                text = "1st",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "फर्स्ट इंडिया वीडियो बुलेटिन",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("close_video_player")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "बंद करें",
                            tint = Color.White
                        )
                    }
                }

                // Video Viewport (Handles Ads & Content)
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (adState) {
                            VideoPlayerAdState.PRE_ROLL, VideoPlayerAdState.MID_ROLL -> {
                                val currentAd = if (adState == VideoPlayerAdState.PRE_ROLL) preRollAd else midRollAd

                                // Ad Video Backdrop
                                AsyncImage(
                                    model = currentAd.videoThumbnailUrl,
                                    contentDescription = currentAd.headline,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                // Gradient Overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Color.Black.copy(alpha = 0.6f),
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.85f)
                                                )
                                            )
                                        )
                                )

                                // Top Bar inside Ad: AD Badge & Remaining Seconds
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.TopStart)
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = SaffronGold
                                    ) {
                                        Text(
                                            text = if (adState == VideoPlayerAdState.PRE_ROLL)
                                                "विज्ञापन (1 of 1)"
                                            else
                                                "मध्य-विराम विज्ञापन (Mid-Roll Ad)",
                                            color = Color.Black,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color.Black.copy(alpha = 0.7f)
                                    ) {
                                        Text(
                                            text = "${adSecondsRemaining}s",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                // Center Ad Brand Info
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 12.dp, bottom = 54.dp, end = 120.dp)
                                ) {
                                    Text(
                                        text = currentAd.brandName,
                                        fontFamily = MuktaFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = SaffronGold
                                    )
                                    Text(
                                        text = currentAd.headline,
                                        fontFamily = MuktaFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color.White,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = currentAd.subHeadline,
                                        fontFamily = HindFontFamily,
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.85f),
                                        maxLines = 1
                                    )
                                }

                                // Bottom Right: Skip Ad Button & CTA
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(10.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    // CTA to visit sponsor
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = Color.White.copy(alpha = 0.25f),
                                        modifier = Modifier
                                            .clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentAd.actionUrl))
                                                    context.startActivity(intent)
                                                } catch (_: Exception) {}
                                            }
                                            .testTag("video_ad_visit_cta")
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                        ) {
                                            Text(
                                                text = currentAd.ctaText,
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(
                                                imageVector = Icons.Default.OpenInNew,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Skip Ad Button (Active after 5s)
                                    if (canSkipAd) {
                                        Button(
                                            onClick = {
                                                adState = VideoPlayerAdState.PLAYING_CONTENT
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = BrandRedPrimary),
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier
                                                .height(34.dp)
                                                .testTag("skip_video_ad_button")
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "विज्ञापन छोड़ें",
                                                    color = Color.White,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Icon(
                                                    imageVector = Icons.Default.SkipNext,
                                                    contentDescription = "स्किप",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    } else {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = Color.Black.copy(alpha = 0.8f)
                                        ) {
                                            Text(
                                                text = "${skipSecondsRemaining}s में छोड़ें",
                                                color = Color.White.copy(alpha = 0.9f),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }

                                // Linear Progress for Ad Duration
                                LinearProgressIndicator(
                                    progress = { 1f - (adSecondsRemaining.toFloat() / currentAd.durationSeconds.toFloat()) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(3.dp)
                                        .align(Alignment.BottomCenter),
                                    color = SaffronGold,
                                    trackColor = Color.White.copy(alpha = 0.2f),
                                )
                            }

                            VideoPlayerAdState.PLAYING_CONTENT, VideoPlayerAdState.COMPLETED -> {
                                // Content Video Thumbnail
                                AsyncImage(
                                    model = video.thumbnailUrl,
                                    contentDescription = video.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                // Overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Color.Black.copy(alpha = 0.5f),
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.85f)
                                                )
                                            )
                                        )
                                )

                                // Live / News Show Tag
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(10.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = BrandRedPrimary
                                    ) {
                                        Text(
                                            text = video.showName,
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                // Center Play/Pause button
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.matchParentSize()
                                ) {
                                    IconButton(
                                        onClick = { isPlaying = !isPlaying },
                                        modifier = Modifier
                                            .size(54.dp)
                                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                            .testTag("video_play_pause_button")
                                    ) {
                                        Icon(
                                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                            contentDescription = "प्ले/पॉज़",
                                            tint = Color.White,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }

                                // Bottom Controls & Timeline with YELLOW MID-ROLL CUE MARKER
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    // Custom Slider Timeline with Yellow Mid-Roll Marker
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(20.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        // Slider track
                                        Slider(
                                            value = videoCurrentSeconds,
                                            onValueChange = { newValue ->
                                                videoCurrentSeconds = newValue
                                                // Trigger midroll if scrubbed across cue point
                                                if (!midRollTriggered && newValue >= midRollCueTime) {
                                                    midRollTriggered = true
                                                    adState = VideoPlayerAdState.MID_ROLL
                                                }
                                            },
                                            valueRange = 0f..videoTotalSeconds,
                                            colors = SliderDefaults.colors(
                                                thumbColor = BrandRedPrimary,
                                                activeTrackColor = BrandRedPrimary,
                                                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("video_timeline_slider")
                                        )

                                        // Yellow Mid-Roll Marker Dot at midRollCueTime / videoTotalSeconds (~44% position)
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth((midRollCueTime / videoTotalSeconds))
                                                .padding(end = 4.dp),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFFFFD54F))
                                                    .clickable {
                                                        // Tap mid-roll cue point directly to trigger mid-roll ad
                                                        midRollTriggered = true
                                                        adState = VideoPlayerAdState.MID_ROLL
                                                    }
                                                    .testTag("mid_roll_cue_marker")
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "0:${videoCurrentSeconds.toInt().toString().padStart(2, '0')} / 0:${videoTotalSeconds.toInt()}",
                                                color = Color.White,
                                                fontSize = 11.sp
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            // Mid-roll cue label
                                            Surface(
                                                shape = RoundedCornerShape(3.dp),
                                                color = Color(0xFFFFD54F).copy(alpha = 0.2f),
                                                modifier = Modifier.clickable {
                                                    adState = VideoPlayerAdState.MID_ROLL
                                                }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(5.dp)
                                                            .clip(CircleShape)
                                                            .background(Color(0xFFFFD54F))
                                                    )
                                                    Spacer(modifier = Modifier.width(3.dp))
                                                    Text(
                                                        text = "मिड-रोल 0:20 पर",
                                                        color = Color(0xFFFFE082),
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { isMuted = !isMuted },
                                                modifier = Modifier.size(30.dp)
                                            ) {
                                                Icon(
                                                    imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                                    contentDescription = "साउंड",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Video Bulletin Title & Metadata
                Text(
                    text = video.title,
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = (24).sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${video.category} • ${video.viewersCount} • ${video.uploadTime}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    // Test trigger button for manual mid-roll testing
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFF9800).copy(alpha = 0.2f),
                        modifier = Modifier
                            .clickable {
                                adState = VideoPlayerAdState.MID_ROLL
                            }
                            .testTag("trigger_mid_roll_button")
                    ) {
                        Text(
                            text = "▶ मिड-रोल विज्ञापन चलाएं",
                            color = Color(0xFFFFB74D),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // In-player Native Display Ad Banner below the video
                val bottomBanner = remember { AdDataProvider.displayBannerAds[0] }
                DisplayBannerAdCard(ad = bottomBanner)
            }
        }
    }
}
