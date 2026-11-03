package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.City
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedGlow
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import com.example.ui.theme.ThemeMode

data class CategorySidebarItem(
    val name: String,
    val icon: ImageVector,
    val isHot: Boolean = false
)

val AllCategoriesList = listOf(
    CategorySidebarItem("सभी", Icons.Default.GridView),
    CategorySidebarItem("शीर्ष समाचार", Icons.Default.TrendingUp, isHot = true),
    CategorySidebarItem("मेरा शहर", Icons.Default.LocationCity),
    CategorySidebarItem("खेल", Icons.Default.SportsCricket),
    CategorySidebarItem("बिज़नेस", Icons.Default.BusinessCenter),
    CategorySidebarItem("मनोरंजन", Icons.Default.Movie),
    CategorySidebarItem("टेक", Icons.Default.SmartDisplay),
    CategorySidebarItem("राशिफल", Icons.Default.AutoAwesome),
    CategorySidebarItem("सम्पादकीय", Icons.AutoMirrored.Filled.MenuBook)
)

@Composable
fun SidebarDrawer(
    selectedCategory: String,
    selectedCity: City,
    themeMode: ThemeMode,
    isPremiumUser: Boolean,
    onSelectCategory: (String) -> Unit,
    onNavigateLiveTv: () -> Unit,
    onNavigateShorts: () -> Unit,
    onOpenCitySelector: () -> Unit,
    onOpenPremium: () -> Unit,
    onOpenBookmarks: () -> Unit,
    onOpenOffline: () -> Unit,
    onOpenNotifications: () -> Unit,
    onToggleTheme: () -> Unit,
    onCloseSidebar: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .width(310.dp)
            .fillMaxHeight(),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerTonalElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .testTag("sidebar_drawer_content")
        ) {
            // Header: Brand banner with Attached Logo & Close Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(BrandRedDark, BrandRedPrimary)
                        )
                    )
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_first_india),
                            contentDescription = "First India News",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(44.dp)
                                .width(68.dp)
                        )

                        IconButton(
                            onClick = onCloseSidebar,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "बंद करें",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Selected City Chip inside Drawer
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Black.copy(alpha = 0.25f),
                        modifier = Modifier
                            .clickable(onClick = onOpenCitySelector)
                            .testTag("sidebar_city_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationCity,
                                contentDescription = null,
                                tint = SaffronGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "शहर: ${selectedCity.hindiName} (${selectedCity.tempC}°C)",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "बदलें >",
                                color = SaffronGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Multimedia Highlights: Live TV & Shorts
            Column(modifier = Modifier.padding(12.dp)) {
                // Live TV Button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = BrandRedPrimary.copy(alpha = 0.10f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onNavigateLiveTv)
                        .testTag("sidebar_livetv_button")
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = BrandRedPrimary,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.LiveTv,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "लाइव टीवी (24x7)",
                                        fontFamily = MuktaFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = BrandRedPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .background(BrandRedGlow, CircleShape)
                                    )
                                }
                                Text(
                                    text = "सीधा प्रसारण देखें",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = BrandRedPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // First India Premium VIP Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SaffronGold.copy(alpha = 0.15f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onOpenPremium)
                        .testTag("sidebar_premium_button")
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = SaffronGold,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.WorkspacePremium,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "फर्स्ट इंडिया प्रीमियम",
                                        fontFamily = MuktaFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (isPremiumUser) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = BrandRedPrimary
                                        ) {
                                            Text(
                                                text = "VIP",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = if (isPremiumUser) "सक्रिय सदस्य" else "विज्ञापन मुक्त • विशेष लेख",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Section: All Categories (सभी श्रेणियां)
            Text(
                text = "सभी श्रेणियां (Categories)",
                fontFamily = MuktaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )

            AllCategoriesList.forEach { category ->
                val isSelected = selectedCategory == category.name
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) BrandRedPrimary.copy(alpha = 0.12f) else Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                        .clickable { onSelectCategory(category.name) }
                        .testTag("sidebar_category_${category.name}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = category.icon,
                                contentDescription = category.name,
                                tint = if (isSelected) BrandRedPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = category.name,
                                fontFamily = MuktaFontFamily,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 15.sp,
                                color = if (isSelected) BrandRedPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (category.isHot) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandRedPrimary
                            ) {
                                Text(
                                    text = "HOT",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = BrandRedPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Section: Quick Utilities (बुकमार्क, ऑफ़लाइन, नोटिफिकेशन्स, थीम)
            Text(
                text = "सुविधाएं व सेटिंग्स",
                fontFamily = MuktaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            // Bookmarks
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenBookmarks)
                    .padding(horizontal = 22.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = null,
                    tint = BrandRedPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "बुकमार्क (सहेजे गए)",
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Offline Library
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenOffline)
                    .padding(horizontal = 22.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CloudDone,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ऑफ़लाइन लाइब्रेरी",
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Notifications
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenNotifications)
                    .padding(horizontal = 22.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = SaffronGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "सूचनाएं व अलर्ट्स",
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Toggle Dark Mode
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggleTheme)
                    .padding(horizontal = 22.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "डार्क मोड",
                        fontFamily = MuktaFontFamily,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = if (themeMode == ThemeMode.DARK) "ऑन" else "ऑफ",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
