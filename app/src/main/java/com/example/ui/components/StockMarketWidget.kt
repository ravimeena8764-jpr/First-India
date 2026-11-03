package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ShowChart
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold

data class MarketIndex(
    val name: String,
    val value: String,
    val change: String,
    val percent: String,
    val isPositive: Boolean
)

data class StockMover(
    val symbol: String,
    val price: String,
    val changePercent: String,
    val isGainer: Boolean
)

val SampleIndices = listOf(
    MarketIndex("BSE सेंसेक्स", "81,420.50", "+412.30", "+0.51%", true),
    MarketIndex("NSE निफ्टी 50", "24,835.15", "+128.60", "+0.52%", true),
    MarketIndex("बैंक निफ्टी", "51,940.80", "+280.20", "+0.54%", true),
    MarketIndex("सोना (24K)", "₹79,450", "+₹320", "+0.40%", true),
    MarketIndex("चांदी (1Kg)", "₹98,200", "+₹650", "+0.67%", true),
    MarketIndex("USD / INR", "₹84.08", "-0.04", "-0.05%", false)
)

val SampleMovers = listOf(
    StockMover("TCS", "₹4,120", "+2.8%", true),
    StockMover("रिलायंस", "₹2,980", "+1.9%", true),
    StockMover("इंफोसिस", "₹1,890", "+2.4%", true),
    StockMover("HDFC बैंक", "₹1,675", "+1.5%", true),
    StockMover("टाटा मोटर्स", "₹945", "-0.9%", false),
    StockMover("ITC", "₹485", "-0.7%", false)
)

@Composable
fun StockMarketWidget(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("stock_market_widget")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Widget Header: Title + Live Market Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E7D32).copy(alpha = 0.15f),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.ShowChart,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "शेयर बाजार व कमोडिटी (LIVE)",
                        fontFamily = MuktaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2E7D32).copy(alpha = 0.12f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2E7D32))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "मार्केट खुला है",
                            color = Color(0xFF2E7D32),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontally Scrollable Major Indices Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SampleIndices.forEach { index ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .width(140.dp)
                            .testTag("market_index_${index.name}")
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = index.name,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = index.value,
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (index.isPositive) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                    contentDescription = null,
                                    tint = if (index.isPositive) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${index.change} (${index.percent})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (index.isPositive) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(8.dp))

            // Top Stocks Gainers & Losers Ticker
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "टॉप मूवर्स:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SampleMovers.forEach { stock ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = (if (stock.isGainer) Color(0xFF2E7D32) else Color(0xFFD32F2F)).copy(alpha = 0.12f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${stock.symbol} ${stock.price}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stock.changePercent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (stock.isGainer) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                            )
                        }
                    }
                }
            }
        }
    }
}
