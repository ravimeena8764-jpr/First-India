package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BookmarkEntity
import com.example.data.local.NewsDatabase
import com.example.data.local.OfflineDownloadEntity
import com.example.data.model.AppNotification
import com.example.data.model.City
import com.example.data.model.LiveTickerItem
import com.example.data.model.NewsArticle
import com.example.data.model.NotificationPreferences
import com.example.data.model.PublicPoll
import com.example.data.model.ReadingTextSize
import com.example.data.model.VideoNewsItem
import com.example.data.model.WebStory
import com.example.data.repository.NewsRepository
import com.example.data.repository.PollDataProvider
import com.example.data.repository.WebStoryDataProvider
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

enum class BottomNavTab(val labelHindi: String) {
    HOME("होम"),
    MY_CITIES("मेरा शहर"),
    VIDEO("वीडियो"),
    SHORTS("शॉर्ट्स"),
    PROFILE("प्रोफ़ाइल")
}

data class NewsUiState(
    val currentTab: BottomNavTab = BottomNavTab.HOME,
    val selectedCategory: String = "सभी",
    val selectedCity: City = NewsRepository.getCitiesList().first(),
    val availableCities: List<City> = NewsRepository.getCitiesList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val selectedArticle: NewsArticle? = null,
    val isCitySelectorOpen: Boolean = false,
    val isNotificationCenterOpen: Boolean = false,
    val isNotificationSettingsOpen: Boolean = false,
    val isBookmarksScreenOpen: Boolean = false,
    val isOfflineDownloadsScreenOpen: Boolean = false,
    val isSpeakingArticle: Boolean = false,
    val activeHeadsUpNotification: AppNotification? = null,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val textSize: ReadingTextSize = ReadingTextSize.MEDIUM,
    val notificationPreferences: NotificationPreferences = NotificationPreferences(),
    val isLiveVideoPlaying: Boolean = true,
    val isLiveVideoMuted: Boolean = false,
    val currentShortIndex: Int = 0,
    val isRefreshing: Boolean = false,
    val toastMessage: String? = null,
    val isSidebarOpen: Boolean = false,
    val isPremiumOpen: Boolean = false,
    val isFavoriteTopicsOpen: Boolean = false,
    val isRatingDialogOpen: Boolean = false,
    val isLogoutDialogOpen: Boolean = false,
    val isPremiumUser: Boolean = false,
    val favoriteTopics: Set<String> = setOf("शीर्ष समाचार", "मेरा शहर", "खेल", "बिज़नेस"),
    val userRating: Int = 5,
    val selectedWebStory: WebStory? = null,
    val activePublicPoll: PublicPoll = PollDataProvider.getDefaultPublicPoll()
)

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository
    private var textToSpeech: TextToSpeech? = null
    private var isTtsInitialized = false

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    val articles: StateFlow<List<NewsArticle>>
    val bookmarkedArticles: StateFlow<List<BookmarkEntity>>
    val offlineArticles: StateFlow<List<OfflineDownloadEntity>>
    val notifications: StateFlow<List<AppNotification>>
    val liveTickers: List<LiveTickerItem> = NewsRepository.getLiveTickers()
    val videosList: List<VideoNewsItem> = NewsRepository.getVideoNews()
    val webStories: List<WebStory> = WebStoryDataProvider.getWebStories()

    init {
        val db = NewsDatabase.getDatabase(application)
        repository = NewsRepository(db.newsDao())

        articles = repository.allArticles
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        bookmarkedArticles = repository.bookmarkedArticles
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        offlineArticles = repository.offlineArticles
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        notifications = repository.notifications
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }

        // Initialize TTS
        initTextToSpeech(application)

        // Periodic live breaking ticker & simulated breaking news notification
        startLiveUpdateSimulator()
    }

    private fun initTextToSpeech(context: Context) {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech?.setLanguage(Locale("hi", "IN"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    textToSpeech?.setLanguage(Locale.ENGLISH)
                }
                isTtsInitialized = true
            }
        }
    }

    fun selectBottomTab(tab: BottomNavTab) {
        stopAudioSpeaking()
        _uiState.value = _uiState.value.copy(
            currentTab = tab,
            selectedArticle = null,
            isBookmarksScreenOpen = false,
            isOfflineDownloadsScreenOpen = false,
            isNotificationCenterOpen = false
        )
    }

    fun selectCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun selectCity(city: City) {
        _uiState.value = _uiState.value.copy(
            selectedCity = city,
            isCitySelectorOpen = false,
            toastMessage = "शहर बदलकर ${city.hindiName} किया गया"
        )
    }

    fun openCitySelector(open: Boolean) {
        _uiState.value = _uiState.value.copy(isCitySelectorOpen = open)
    }

    fun openArticle(article: NewsArticle) {
        stopAudioSpeaking()
        _uiState.value = _uiState.value.copy(selectedArticle = article)
    }

    fun closeArticle() {
        stopAudioSpeaking()
        _uiState.value = _uiState.value.copy(selectedArticle = null)
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun toggleSearch(active: Boolean) {
        _uiState.value = _uiState.value.copy(isSearching = active, searchQuery = if (active) _uiState.value.searchQuery else "")
    }

    fun toggleBookmark(article: NewsArticle) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
            val isNowBookmarked = !article.isBookmarked
            val msg = if (isNowBookmarked) "समाचार बुकमार्क में सहेजा गया" else "बुकमार्क से हटाया गया"
            _uiState.value = _uiState.value.copy(toastMessage = msg)
        }
    }

    fun toggleOfflineDownload(article: NewsArticle) {
        viewModelScope.launch {
            repository.toggleOfflineDownload(article)
            val isNowDownloaded = !article.isDownloadedOffline
            val msg = if (isNowDownloaded) "समाचार ऑफ़लाइन पढ़ने हेतु सहेजा गया" else "ऑफ़लाइन स्टोरेज से हटाया गया"
            _uiState.value = _uiState.value.copy(toastMessage = msg)
        }
    }

    fun clearAllOfflineDownloads() {
        viewModelScope.launch {
            repository.clearAllOffline()
            _uiState.value = _uiState.value.copy(toastMessage = "सभी ऑफ़लाइन समाचार हटा दिए गए")
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        _uiState.value = _uiState.value.copy(themeMode = mode)
    }

    fun setTextSize(size: ReadingTextSize) {
        _uiState.value = _uiState.value.copy(textSize = size)
    }

    fun updateNotificationPreferences(preferences: NotificationPreferences) {
        _uiState.value = _uiState.value.copy(
            notificationPreferences = preferences,
            toastMessage = "नोटिफिकेशन सेटिंग्स अपडेट की गईं"
        )
    }

    fun openNotificationCenter(open: Boolean) {
        _uiState.value = _uiState.value.copy(isNotificationCenterOpen = open)
        if (open) {
            viewModelScope.launch {
                repository.markAllNotificationsRead()
            }
        }
    }

    fun openNotificationSettings(open: Boolean) {
        _uiState.value = _uiState.value.copy(isNotificationSettingsOpen = open)
    }

    fun openBookmarksScreen(open: Boolean) {
        _uiState.value = _uiState.value.copy(isBookmarksScreenOpen = open)
    }

    fun openOfflineDownloadsScreen(open: Boolean) {
        _uiState.value = _uiState.value.copy(isOfflineDownloadsScreenOpen = open)
    }

    fun dismissHeadsUpNotification() {
        _uiState.value = _uiState.value.copy(activeHeadsUpNotification = null)
    }

    fun triggerTestNotification() {
        viewModelScope.launch {
            val title = "⚡ फर्स्ट इंडिया ब्रेकिंग लाइव"
            val body = "राजस्थान बजट 2026: युवाओं के लिए 50,000 नई सरकारी नौकरियों की घोषणा, किसानों को मुफ्त बिजली।"
            val notification = AppNotification(
                id = System.currentTimeMillis(),
                title = title,
                body = body,
                category = "ब्रेकिंग",
                articleId = "art_1",
                timestamp = System.currentTimeMillis()
            )
            repository.postNotification(title, body, "ब्रेकिंग", "art_1")
            _uiState.value = _uiState.value.copy(
                activeHeadsUpNotification = notification,
                toastMessage = "पुश नोटिफिकेशन प्राप्त हुआ"
            )

            // Auto dismiss banner after 5 seconds
            delay(5000)
            if (_uiState.value.activeHeadsUpNotification?.id == notification.id) {
                _uiState.value = _uiState.value.copy(activeHeadsUpNotification = null)
            }
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            delay(1200)
            _uiState.value = _uiState.value.copy(
                isRefreshing = false,
                toastMessage = "ताज़ा खबरें अपडेट हो गई हैं (LIVE)"
            )
        }
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }

    fun openSidebar(open: Boolean) {
        _uiState.value = _uiState.value.copy(isSidebarOpen = open)
    }

    fun openPremium(open: Boolean) {
        _uiState.value = _uiState.value.copy(isPremiumOpen = open)
    }

    fun setPremium(isPremium: Boolean) {
        _uiState.value = _uiState.value.copy(
            isPremiumUser = isPremium,
            isPremiumOpen = false,
            toastMessage = if (isPremium) "फर्स्ट इंडिया प्रीमियम सक्रिय हो गया है!" else "फर्स्ट इंडिया बेसिक प्लान"
        )
    }

    fun openFavoriteTopics(open: Boolean) {
        _uiState.value = _uiState.value.copy(isFavoriteTopicsOpen = open)
    }

    fun toggleFavoriteTopic(topic: String) {
        val current = _uiState.value.favoriteTopics.toMutableSet()
        if (current.contains(topic)) {
            if (current.size > 1) {
                current.remove(topic)
            }
        } else {
            current.add(topic)
        }
        _uiState.value = _uiState.value.copy(favoriteTopics = current)
    }

    fun openRatingDialog(open: Boolean) {
        _uiState.value = _uiState.value.copy(isRatingDialogOpen = open)
    }

    fun submitRating(stars: Int) {
        _uiState.value = _uiState.value.copy(
            userRating = stars,
            isRatingDialogOpen = false,
            toastMessage = "फर्स्ट इंडिया न्यूज़ को $stars स्टार रेटिंग देने के लिए धन्यवाद!"
        )
    }

    fun openLogoutDialog(open: Boolean) {
        _uiState.value = _uiState.value.copy(isLogoutDialogOpen = open)
    }

    fun logout() {
        _uiState.value = _uiState.value.copy(
            isLogoutDialogOpen = false,
            toastMessage = "सफलतापूर्वक लॉग आउट किया गया"
        )
    }

    fun navigateToLiveTv() {
        stopAudioSpeaking()
        _uiState.value = _uiState.value.copy(
            currentTab = BottomNavTab.VIDEO,
            selectedArticle = null,
            isBookmarksScreenOpen = false,
            isOfflineDownloadsScreenOpen = false,
            isNotificationCenterOpen = false
        )
    }

    // Video controls
    fun toggleLiveVideoPlay() {
        _uiState.value = _uiState.value.copy(isLiveVideoPlaying = !_uiState.value.isLiveVideoPlaying)
    }

    fun toggleLiveVideoMute() {
        _uiState.value = _uiState.value.copy(isLiveVideoMuted = !_uiState.value.isLiveVideoMuted)
    }

    // Shorts navigation
    fun nextShort(maxItems: Int) {
        stopAudioSpeaking()
        val next = (_uiState.value.currentShortIndex + 1) % maxItems.coerceAtLeast(1)
        _uiState.value = _uiState.value.copy(currentShortIndex = next)
    }

    fun previousShort(maxItems: Int) {
        stopAudioSpeaking()
        val prev = if (_uiState.value.currentShortIndex - 1 < 0) maxItems - 1 else _uiState.value.currentShortIndex - 1
        _uiState.value = _uiState.value.copy(currentShortIndex = prev)
    }

    // Web Stories
    fun openWebStory(story: WebStory?) {
        stopAudioSpeaking()
        _uiState.value = _uiState.value.copy(selectedWebStory = story)
    }

    // Public Poll
    fun voteInPoll(optionId: String) {
        val currentPoll = _uiState.value.activePublicPoll
        if (currentPoll.userSelectedOptionId == optionId) return

        val previousSelectedId = currentPoll.userSelectedOptionId
        val updatedOptions = currentPoll.options.map { option ->
            when (option.id) {
                optionId -> option.copy(initialVotes = option.initialVotes + 1)
                previousSelectedId -> option.copy(initialVotes = (option.initialVotes - 1).coerceAtLeast(0))
                else -> option
            }
        }
        val updatedPoll = currentPoll.copy(
            options = updatedOptions,
            userSelectedOptionId = optionId
        )
        _uiState.value = _uiState.value.copy(
            activePublicPoll = updatedPoll,
            toastMessage = "धन्यवाद! आपका जनमत दर्ज कर लिया गया है।"
        )
    }

    fun resetPollVote() {
        val currentPoll = _uiState.value.activePublicPoll
        val previousSelectedId = currentPoll.userSelectedOptionId ?: return
        val updatedOptions = currentPoll.options.map { option ->
            if (option.id == previousSelectedId) {
                option.copy(initialVotes = (option.initialVotes - 1).coerceAtLeast(0))
            } else {
                option
            }
        }
        _uiState.value = _uiState.value.copy(
            activePublicPoll = currentPoll.copy(
                options = updatedOptions,
                userSelectedOptionId = null
            ),
            toastMessage = "आप अपनी राय दोबारा चुन सकते हैं"
        )
    }

    // Audio News / TTS
    fun toggleAudioReading(textToRead: String) {
        if (_uiState.value.isSpeakingArticle) {
            stopAudioSpeaking()
        } else {
            speakText(textToRead)
        }
    }

    private fun speakText(text: String) {
        if (isTtsInitialized && textToSpeech != null) {
            _uiState.value = _uiState.value.copy(isSpeakingArticle = true)
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "NEWS_TTS")
        } else {
            _uiState.value = _uiState.value.copy(toastMessage = "ऑडियो प्लेयर लोड हो रहा है...")
        }
    }

    private fun stopAudioSpeaking() {
        if (_uiState.value.isSpeakingArticle) {
            textToSpeech?.stop()
            _uiState.value = _uiState.value.copy(isSpeakingArticle = false)
        }
    }

    private fun startLiveUpdateSimulator() {
        viewModelScope.launch {
            // Wait 12 seconds after launch to trigger a realistic simulated live breaking push notification
            delay(12000)
            if (_uiState.value.notificationPreferences.breakingNews) {
                val notif = AppNotification(
                    id = System.currentTimeMillis(),
                    title = "🔴 फर्स्ट इंडिया ब्रेकिंग लाइव",
                    body = "जयपुर: हेरिटेज चारदीवारी में नया ई-वाहन कॉरिडोर शुरू, सीएम ने दिखाई हरी झंडी।",
                    category = "मेरा शहर",
                    articleId = "art_2",
                    timestamp = System.currentTimeMillis()
                )
                repository.postNotification(notif.title, notif.body, notif.category, notif.articleId)
                _uiState.value = _uiState.value.copy(activeHeadsUpNotification = notif)
                delay(6000)
                if (_uiState.value.activeHeadsUpNotification?.id == notif.id) {
                    _uiState.value = _uiState.value.copy(activeHeadsUpNotification = null)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}
