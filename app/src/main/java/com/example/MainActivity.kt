package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.HeadsUpNotificationBanner
import com.example.ui.components.NewsTopBar
import com.example.ui.components.SidebarDrawer
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.BookmarksListScreen
import com.example.ui.screens.CitySelectorDialog
import com.example.ui.screens.FavoriteTopicsDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LogoutConfirmationDialog
import com.example.ui.screens.MyCitiesScreen
import com.example.ui.screens.NotificationCenterDialog
import com.example.ui.screens.NotificationSettingsDialog
import com.example.ui.screens.OfflineDownloadsScreen
import com.example.ui.screens.PremiumMembershipDialog
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RateAppDialog
import com.example.ui.screens.ShortsScreen
import com.example.ui.screens.VideoScreen
import com.example.ui.theme.FirstIndiaTheme
import com.example.ui.theme.ThemeMode
import com.example.viewmodel.BottomNavTab
import com.example.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val articles by viewModel.articles.collectAsStateWithLifecycle()
            val bookmarks by viewModel.bookmarkedArticles.collectAsStateWithLifecycle()
            val offlineDownloads by viewModel.offlineArticles.collectAsStateWithLifecycle()
            val notifications by viewModel.notifications.collectAsStateWithLifecycle()

            val snackbarHostState = remember { SnackbarHostState() }
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            // Handle Toast / feedback messages
            LaunchedEffect(uiState.toastMessage) {
                uiState.toastMessage?.let { msg ->
                    snackbarHostState.showSnackbar(msg)
                    viewModel.clearToast()
                }
            }

            // Sync drawer state with ViewModel if triggered externally
            LaunchedEffect(uiState.isSidebarOpen) {
                if (uiState.isSidebarOpen && drawerState.isClosed) {
                    drawerState.open()
                } else if (!uiState.isSidebarOpen && drawerState.isOpen) {
                    drawerState.close()
                }
            }

            FirstIndiaTheme(themeMode = uiState.themeMode) {
                // If an article detail is open, show ArticleDetailScreen full bleed
                if (uiState.selectedArticle != null) {
                    ArticleDetailScreen(
                        article = uiState.selectedArticle!!,
                        uiState = uiState,
                        allArticles = articles,
                        viewModel = viewModel,
                        onBack = { viewModel.closeArticle() }
                    )
                } else if (uiState.isBookmarksScreenOpen) {
                    BookmarksListScreen(
                        bookmarks = bookmarks,
                        allArticles = articles,
                        viewModel = viewModel,
                        onBack = { viewModel.openBookmarksScreen(false) }
                    )
                } else if (uiState.isOfflineDownloadsScreenOpen) {
                    OfflineDownloadsScreen(
                        downloads = offlineDownloads,
                        allArticles = articles,
                        viewModel = viewModel,
                        onBack = { viewModel.openOfflineDownloadsScreen(false) }
                    )
                } else {
                    // Back handler for drawer and secondary tabs
                    if (drawerState.isOpen) {
                        BackHandler {
                            scope.launch { drawerState.close() }
                            viewModel.openSidebar(false)
                        }
                    } else if (uiState.currentTab != BottomNavTab.HOME) {
                        BackHandler {
                            viewModel.selectBottomTab(BottomNavTab.HOME)
                        }
                    }

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        gesturesEnabled = uiState.currentTab != BottomNavTab.SHORTS,
                        drawerContent = {
                            SidebarDrawer(
                                selectedCategory = uiState.selectedCategory,
                                selectedCity = uiState.selectedCity,
                                themeMode = uiState.themeMode,
                                isPremiumUser = uiState.isPremiumUser,
                                onSelectCategory = { cat ->
                                    viewModel.selectCategory(cat)
                                    if (uiState.currentTab != BottomNavTab.HOME) {
                                        viewModel.selectBottomTab(BottomNavTab.HOME)
                                    }
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                },
                                onNavigateLiveTv = {
                                    viewModel.navigateToLiveTv()
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                },
                                onNavigateShorts = {
                                    viewModel.selectBottomTab(BottomNavTab.SHORTS)
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                },
                                onOpenCitySelector = {
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                    viewModel.openCitySelector(true)
                                },
                                onOpenPremium = {
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                    viewModel.openPremium(true)
                                },
                                onOpenBookmarks = {
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                    viewModel.openBookmarksScreen(true)
                                },
                                onOpenOffline = {
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                    viewModel.openOfflineDownloadsScreen(true)
                                },
                                onOpenNotifications = {
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                    viewModel.openNotificationCenter(true)
                                },
                                onToggleTheme = {
                                    val nextMode = if (uiState.themeMode == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK
                                    viewModel.setThemeMode(nextMode)
                                },
                                onCloseSidebar = {
                                    scope.launch { drawerState.close() }
                                    viewModel.openSidebar(false)
                                }
                            )
                        }
                    ) {
                        Scaffold(
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            topBar = {
                                // Don't show top bar on Shorts screen to allow full-height immersive cards
                                if (uiState.currentTab != BottomNavTab.SHORTS) {
                                    NewsTopBar(
                                        unreadNotificationCount = notifications.count { !it.isRead },
                                        searchQuery = uiState.searchQuery,
                                        isSearching = uiState.isSearching,
                                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                        onToggleSearch = { viewModel.toggleSearch(it) },
                                        onOpenSidebar = {
                                            scope.launch { drawerState.open() }
                                            viewModel.openSidebar(true)
                                        },
                                        onLiveTvClick = { viewModel.navigateToLiveTv() },
                                        onNotificationClick = { viewModel.openNotificationCenter(true) }
                                    )
                                }
                            },
                            bottomBar = {
                                AppBottomNavigation(
                                    currentTab = uiState.currentTab,
                                    onTabSelected = { viewModel.selectBottomTab(it) }
                                )
                            },
                            modifier = Modifier.fillMaxSize()
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                AnimatedContent(
                                    targetState = uiState.currentTab,
                                    transitionSpec = { fadeIn().togetherWith(fadeOut()) },
                                    label = "tab_switch_anim"
                                ) { tab ->
                                    when (tab) {
                                        BottomNavTab.HOME -> HomeScreen(
                                            uiState = uiState,
                                            articles = articles,
                                            viewModel = viewModel
                                        )
                                        BottomNavTab.MY_CITIES -> MyCitiesScreen(
                                            uiState = uiState,
                                            articles = articles,
                                            viewModel = viewModel
                                        )
                                        BottomNavTab.VIDEO -> VideoScreen(
                                            uiState = uiState,
                                            viewModel = viewModel
                                        )
                                        BottomNavTab.SHORTS -> ShortsScreen(
                                            uiState = uiState,
                                            articles = articles,
                                            viewModel = viewModel
                                        )
                                        BottomNavTab.PROFILE -> ProfileScreen(
                                            uiState = uiState,
                                            bookmarkCount = bookmarks.size,
                                            offlineCount = offlineDownloads.size,
                                            notificationCount = notifications.size,
                                            viewModel = viewModel
                                        )
                                    }
                                }

                                // In-App Simulated Heads-Up Push Notification Alert
                                HeadsUpNotificationBanner(
                                    notification = uiState.activeHeadsUpNotification,
                                    onOpenNotification = { notif ->
                                        viewModel.dismissHeadsUpNotification()
                                        notif.articleId?.let { id ->
                                            articles.find { it.id == id }?.let {
                                                viewModel.openArticle(it)
                                            }
                                        }
                                    },
                                    onDismiss = { viewModel.dismissHeadsUpNotification() }
                                )
                            }
                        }
                    }
                }

                // Modal Dialogs
                if (uiState.isCitySelectorOpen) {
                    CitySelectorDialog(
                        cities = uiState.availableCities,
                        selectedCity = uiState.selectedCity,
                        onCitySelected = { viewModel.selectCity(it) },
                        onDismiss = { viewModel.openCitySelector(false) }
                    )
                }

                if (uiState.isNotificationCenterOpen) {
                    NotificationCenterDialog(
                        notifications = notifications,
                        onNotificationClick = { notif ->
                            viewModel.openNotificationCenter(false)
                            notif.articleId?.let { id ->
                                articles.find { it.id == id }?.let { viewModel.openArticle(it) }
                            }
                        },
                        onDismiss = { viewModel.openNotificationCenter(false) }
                    )
                }

                if (uiState.isNotificationSettingsOpen) {
                    NotificationSettingsDialog(
                        initialPreferences = uiState.notificationPreferences,
                        onSave = { viewModel.updateNotificationPreferences(it) },
                        onTestNotification = { viewModel.triggerTestNotification() },
                        onDismiss = { viewModel.openNotificationSettings(false) }
                    )
                }

                // First India Premium VIP Membership Dialog
                if (uiState.isPremiumOpen) {
                    PremiumMembershipDialog(
                        isPremiumUser = uiState.isPremiumUser,
                        onTogglePlan = { viewModel.setPremium(it) },
                        onDismiss = { viewModel.openPremium(false) }
                    )
                }

                // Favorite Topics Customization Dialog
                if (uiState.isFavoriteTopicsOpen) {
                    val allCats = listOf("सभी", "शीर्ष समाचार", "मेरा शहर", "खेल", "बिज़नेस", "मनोरंजन", "टेक", "राशिफल", "सम्पादकीय")
                    FavoriteTopicsDialog(
                        allCategories = allCats,
                        favoriteTopics = uiState.favoriteTopics,
                        onToggleTopic = { viewModel.toggleFavoriteTopic(it) },
                        onDismiss = { viewModel.openFavoriteTopics(false) }
                    )
                }

                // Rate App Dialog
                if (uiState.isRatingDialogOpen) {
                    RateAppDialog(
                        currentRating = uiState.userRating,
                        onSubmit = { stars -> viewModel.submitRating(stars) },
                        onDismiss = { viewModel.openRatingDialog(false) }
                    )
                }

                // Logout Confirmation Dialog
                if (uiState.isLogoutDialogOpen) {
                    LogoutConfirmationDialog(
                        onConfirm = { viewModel.logout() },
                        onDismiss = { viewModel.openLogoutDialog(false) }
                    )
                }
            }
        }
    }
}
