package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NewsArticle
import com.example.data.repository.AdDataProvider
import com.example.ui.components.NewsCard
import com.example.ui.components.ads.LocalCityAdCard
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold
import com.example.viewmodel.NewsUiState
import com.example.viewmodel.NewsViewModel

@Composable
fun MyCitiesScreen(
    uiState: NewsUiState,
    articles: List<NewsArticle>,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val currentCity = uiState.selectedCity

    // Filter city news
    val cityArticles = articles.filter { article ->
        article.city?.equals(currentCity.hindiName, ignoreCase = true) == true ||
                article.city?.contains(currentCity.englishName, ignoreCase = true) == true ||
                article.content.contains(currentCity.hindiName)
    }

    val localAds = remember(currentCity.hindiName) {
        AdDataProvider.getLocalAdsForCity(currentCity.hindiName)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("my_cities_screen"),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // City Header Weather & AQI Banner Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("city_weather_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    BrandRedDark.copy(alpha = 0.95f),
                                    BrandRedPrimary.copy(alpha = 0.9f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "स्थान",
                                tint = SaffronGold,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "${currentCity.hindiName} समाचार",
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "${currentCity.stateHindi} • फर्स्ट इंडिया लोकल ब्यूरो",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }

                        // Change city action button
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier
                                .clickable { viewModel.openCitySelector(true) }
                                .testTag("change_city_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChangeCircle,
                                    contentDescription = "बदलें",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "शहर बदलें",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Weather and AQI row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Weather
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "मौसम",
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "${currentCity.tempC}°C",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 22.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = currentCity.weatherCondition,
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }

                        // Air Quality Index (AQI)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Air,
                                contentDescription = "AQI",
                                tint = Color(0xFF81C784),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "AQI ${currentCity.aqi}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "वायु: ${currentCity.aqiStatus}",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Horizontal Quick Switch City Chips
        item {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "राजस्थान के जिले व शहर",
                        fontFamily = MuktaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "सभी 50 जिले",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = BrandRedPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable { viewModel.openCitySelector(true) }
                    )
                }

                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.availableCities.forEach { city ->
                        val isSelected = city.id == currentCity.id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (isSelected) BrandRedPrimary else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { viewModel.selectCity(city) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("city_chip_${city.id}")
                        ) {
                            Text(
                                text = "${city.hindiName} (${city.tempC}°)",
                                fontFamily = MuktaFontFamily,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Plus button to open all 50 districts selector
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(BrandRedPrimary.copy(alpha = 0.15f))
                            .clickable { viewModel.openCitySelector(true) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("all_districts_chip_button")
                    ) {
                        Text(
                            text = "+ सभी 50 जिले",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = BrandRedPrimary
                        )
                    }
                }
            }
        }

        // 📍 Local Ads in Local City News Section
        if (localAds.isNotEmpty()) {
            item(key = "local_ads_header_${currentCity.id}") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📍 ${currentCity.hindiName} लोकल ऑफर्स व बिजनेस",
                        fontFamily = MuktaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = SaffronGold.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "स्थानीय विज्ञापन",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD84315),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            item(key = "local_city_ad_card_1_${currentCity.id}") {
                LocalCityAdCard(ad = localAds[0])
            }
        }

        // City News Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentCity.hindiName} के प्रमुख समाचार",
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${cityArticles.size} खबरें",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // City Articles or fallback
        if (cityArticles.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationCity,
                            contentDescription = "शहर",
                            tint = BrandRedPrimary,
                            modifier = Modifier.size(42.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${currentCity.hindiName} के लिए स्थानीय बुलेटिन जल्द अपडेट होगा",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "हमारे रिपोर्टर ग्राउंड पर हैं। इस बीच राजस्थान के अन्य प्रमुख समाचार देखें।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Text(
                    text = "राजस्थान के प्रमुख समाचार",
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            val fallbackArticles = articles.filter { it.category == "शीर्ष समाचार" || it.category == "मेरा शहर" }.take(6)
            fallbackArticles.forEach { article ->
                item(key = "state_news_${article.id}") {
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
            }
        } else {
            cityArticles.forEachIndexed { index, article ->
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

                // In-feed local ad after 1st city news article
                if (index == 0 && localAds.size > 1) {
                    item(key = "local_city_second_ad_${currentCity.id}") {
                        LocalCityAdCard(ad = localAds[1])
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}
