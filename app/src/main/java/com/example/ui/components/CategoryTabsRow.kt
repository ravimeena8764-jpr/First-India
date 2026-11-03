package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily

data class CategoryTabItem(
    val name: String,
    val icon: ImageVector
)

val NewsCategoryItems = listOf(
    CategoryTabItem("सभी", Icons.Default.GridView),
    CategoryTabItem("शीर्ष समाचार", Icons.Default.TrendingUp),
    CategoryTabItem("मेरा शहर", Icons.Default.LocationCity),
    CategoryTabItem("खेल", Icons.Default.SportsCricket),
    CategoryTabItem("बिज़नेस", Icons.Default.BusinessCenter),
    CategoryTabItem("मनोरंजन", Icons.Default.Movie),
    CategoryTabItem("टेक", Icons.Default.SmartDisplay),
    CategoryTabItem("राशिफल", Icons.Default.AutoAwesome),
    CategoryTabItem("सम्पादकीय", Icons.AutoMirrored.Filled.MenuBook)
)

val NewsCategories = NewsCategoryItems.map { it.name }

@Composable
fun CategoryTabsRow(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onOpenAllCategories: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Optional Leading "Category" quick icon button to open all categories
            if (onOpenAllCategories != null) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = onOpenAllCategories)
                        .testTag("top_menu_category_icon_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = "सभी श्रेणियां",
                            tint = BrandRedPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "कैटेगरी",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Category Tab Chips with Category Icons
            NewsCategoryItems.forEach { item ->
                val isSelected = item.name == selectedCategory
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) BrandRedPrimary else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable { onCategorySelected(item.name) }
                        .padding(horizontal = 11.dp, vertical = 6.dp)
                        .testTag("category_tab_${item.name}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = if (isSelected) Color.White else BrandRedPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = item.name,
                            fontFamily = MuktaFontFamily,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
