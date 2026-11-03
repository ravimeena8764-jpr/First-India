package com.example.ui.screens

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ReadingTextSize
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import com.example.ui.theme.ThemeMode
import com.example.viewmodel.NewsUiState
import com.example.viewmodel.NewsViewModel

@Composable
fun ProfileScreen(
    uiState: NewsUiState,
    bookmarkCount: Int,
    offlineCount: Int,
    notificationCount: Int,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // User Profile Masthead
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(BrandRedDark, BrandRedPrimary)
                            )
                        )
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "प्रोफ़ाइल",
                                    tint = BrandRedPrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "फर्स्ट इंडिया पाठक",
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                                if (uiState.isPremiumUser) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = SaffronGold
                                    ) {
                                        Text(
                                            text = "VIP",
                                            color = Color.Black,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = uiState.selectedCity.hindiName,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3 Stat Counters: Bookmarks, Offline Downloads, Notifications
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        // Bookmarks count
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { viewModel.openBookmarksScreen(true) }
                                .testTag("profile_stat_bookmarks")
                        ) {
                            Text(
                                text = bookmarkCount.toString(),
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = "बुकमार्क",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }

                        // Offline reading count
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { viewModel.openOfflineDownloadsScreen(true) }
                                .testTag("profile_stat_offline")
                        ) {
                            Text(
                                text = offlineCount.toString(),
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = "ऑफ़लाइन",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }

                        // Notifications count
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { viewModel.openNotificationCenter(true) }
                                .testTag("profile_stat_notifications")
                        ) {
                            Text(
                                text = notificationCount.toString(),
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = "सूचनाएं",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }
                }
            }
        }

        // Section: Main User Settings (The 7 Requested Items)
        item {
            Text(
                text = "मेरी प्राथमिकताएं व सेवाएं",
                fontFamily = MuktaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    // 1. फर्स्ट इंडिया प्रीमियम (First India Premium)
                    ProfileListItem(
                        icon = Icons.Default.WorkspacePremium,
                        iconTint = SaffronGold,
                        title = "फर्स्ट इंडिया प्रीमियम",
                        subtitle = if (uiState.isPremiumUser) "प्रीमियम वीआईपी सक्रिय • विज्ञापन मुक्त" else "विज्ञापन मुक्त समाचार, एक्सक्लूसिव खुलासे और ई-पेपर",
                        trailingTag = if (uiState.isPremiumUser) "सक्रिय" else "VIP",
                        trailingTagColor = if (uiState.isPremiumUser) Color(0xFF2E7D32) else BrandRedPrimary,
                        onClick = { viewModel.openPremium(true) },
                        testTag = "profile_item_premium"
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 2. नोटिफिकेशन (Notifications)
                    ProfileListItem(
                        icon = Icons.Default.NotificationsActive,
                        iconTint = BrandRedPrimary,
                        title = "नोटिफिकेशन",
                        subtitle = "ब्रेकिंग अलर्ट्स, शहर व क्रिकेट लाइव अपडेट्स",
                        trailingTag = "${notificationCount} नए",
                        trailingTagColor = BrandRedPrimary,
                        onClick = { viewModel.openNotificationSettings(true) },
                        testTag = "profile_item_notifications"
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 3. अपना शहर चुने (Select your city)
                    ProfileListItem(
                        icon = Icons.Default.LocationCity,
                        iconTint = Color(0xFF1976D2),
                        title = "अपना शहर चुने",
                        subtitle = "वर्तमान शहर: ${uiState.selectedCity.hindiName} (${uiState.selectedCity.tempC}°C)",
                        trailingTag = "बदलें",
                        trailingTagColor = MaterialTheme.colorScheme.primary,
                        onClick = { viewModel.openCitySelector(true) },
                        testTag = "profile_item_select_city"
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 4. पसंदीदा टॉपिक (Favorite Topics)
                    ProfileListItem(
                        icon = Icons.Default.Category,
                        iconTint = Color(0xFFE65100),
                        title = "पसंदीदा टॉपिक",
                        subtitle = uiState.favoriteTopics.joinToString(" • "),
                        trailingTag = "${uiState.favoriteTopics.size} चुने गए",
                        trailingTagColor = MaterialTheme.colorScheme.secondary,
                        onClick = { viewModel.openFavoriteTopics(true) },
                        testTag = "profile_item_favorite_topics"
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 5. दोस्तों को इनवाइट करें (Invite Friends)
                    ProfileListItem(
                        icon = Icons.Default.Share,
                        iconTint = Color(0xFF00796B),
                        title = "दोस्तों को इनवाइट करें",
                        subtitle = "व्हाट्सएप, फेसबुक और अन्य ऐप्स पर शेयर करें",
                        trailingTag = "शेयर",
                        trailingTagColor = Color(0xFF00796B),
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "फर्स्ट इंडिया न्यूज़ - राजस्थान व देश का सबसे निष्पक्ष व विश्वसनीय हिंदी न्यूज़ नेटवर्क। आज ही ऐप डाउनलोड करें: https://firstindianews.com/download"
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "दोस्तों को फर्स्ट इंडिया ऐप इनवाइट करें")
                            context.startActivity(shareIntent)
                        },
                        testTag = "profile_item_invite_friends"
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 6. एप को रेटिंग दे (Rate the App)
                    ProfileListItem(
                        icon = Icons.Default.Star,
                        iconTint = SaffronGold,
                        title = "एप को रेटिंग दे",
                        subtitle = "प्ले स्टोर पर हमें 5 स्टार रेटिंग देकर प्रोत्साहित करें",
                        trailingTag = "${uiState.userRating} ★",
                        trailingTagColor = SaffronGold,
                        onClick = { viewModel.openRatingDialog(true) },
                        testTag = "profile_item_rate_app"
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 7. लॉग आउट (Log Out)
                    ProfileListItem(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        iconTint = Color(0xFFD32F2F),
                        title = "लॉग आउट",
                        subtitle = "वर्तमान पाठक प्रोफाइल से बाहर निकलें",
                        titleColor = Color(0xFFD32F2F),
                        onClick = { viewModel.openLogoutDialog(true) },
                        testTag = "profile_item_logout"
                    )
                }
            }
        }

        // Reading Text Size Selector
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = null,
                            tint = BrandRedPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "फ़ॉन्ट का आकार (Text Size)",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ReadingTextSize.values().forEach { size ->
                            val isSelected = uiState.textSize == size
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) BrandRedPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.setTextSize(size) }
                                    .testTag("text_size_${size.name.lowercase()}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                    Text(
                                        text = size.labelHindi,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Offline Cache Management
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.clearAllOfflineDownloads() }
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = null,
                            tint = Color(0xFFD32F2F)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "ऑफ़लाइन कैश खाली करें",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "डिवाइस स्टोरेज खाली करने के लिए कैश हटाएं",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // App Info & Version
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "First India News",
                        fontFamily = MuktaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "संस्करण 2.5.0 • First India Media Group",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun ProfileListItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    trailingTag: String? = null,
    trailingTagColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 13.dp)
            .testTag(testTag),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                shape = CircleShape,
                color = iconTint.copy(alpha = 0.12f),
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = titleColor
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (trailingTag != null) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = trailingTagColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = trailingTag,
                        color = trailingTagColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
