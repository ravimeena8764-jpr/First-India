package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SlowMotionVideo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily
import com.example.viewmodel.BottomNavTab

data class NavItem(
    val tab: BottomNavTab,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)

val BottomNavItems = listOf(
    NavItem(BottomNavTab.HOME, Icons.Filled.Home, Icons.Outlined.Home, "होम"),
    NavItem(BottomNavTab.MY_CITIES, Icons.Filled.LocationCity, Icons.Outlined.LocationCity, "मेरा शहर"),
    NavItem(BottomNavTab.VIDEO, Icons.Filled.LiveTv, Icons.Outlined.LiveTv, "वीडियो"),
    NavItem(BottomNavTab.SHORTS, Icons.Filled.SlowMotionVideo, Icons.Outlined.SlowMotionVideo, "शॉर्ट्स"),
    NavItem(BottomNavTab.PROFILE, Icons.Filled.Person, Icons.Outlined.Person, "प्रोफ़ाइल")
)

@Composable
fun AppBottomNavigation(
    currentTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("bottom_nav_bar")
    ) {
        BottomNavItems.forEach { item ->
            val isSelected = item.tab == currentTab

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item.tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontFamily = MuktaFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandRedPrimary,
                    selectedTextColor = BrandRedPrimary,
                    indicatorColor = BrandRedPrimary.copy(alpha = 0.12f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.testTag("nav_${item.tab.name.lowercase()}")
            )
        }
    }
}
