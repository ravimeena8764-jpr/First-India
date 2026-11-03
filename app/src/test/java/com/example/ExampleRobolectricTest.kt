package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.NewsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("First India New", appName)
  }

  @Test
  fun `video news contains featured image thumbnails`() {
    val videos = NewsRepository.getVideoNews()
    assertTrue(videos.isNotEmpty())
    videos.forEach { video ->
      assertTrue(video.thumbnailUrl.startsWith("http"))
    }
  }

  @Test
  fun `cities list provides default cities`() {
    val cities = NewsRepository.getCitiesList()
    assertTrue(cities.size >= 5)
    assertTrue(cities.any { it.hindiName == "जयपुर" })
  }

  @Test
  fun `articles dataset contains 10 articles in each category`() {
    val dataset = com.example.data.repository.NewsDataProvider.getInitialNewsDataset()
    assertEquals(80, dataset.size)
    val categories = listOf("शीर्ष समाचार", "मेरा शहर", "खेल", "बिज़नेस", "मनोरंजन", "टेक", "राशिफल", "सम्पादकीय")
    categories.forEach { cat ->
      val count = dataset.count { it.category == cat }
      assertEquals("Category $cat should have 10 articles", 10, count)
    }
  }

  @Test
  fun `web stories dataset is populated with slides`() {
    val stories = com.example.data.repository.WebStoryDataProvider.getWebStories()
    assertTrue("Web stories should not be empty", stories.isNotEmpty())
    stories.forEach { story ->
      assertTrue("Story should have title", story.title.isNotBlank())
      assertTrue("Story should have at least 2 slides", story.slides.size >= 2)
      assertTrue("Cover image should start with http", story.coverImageUrl.startsWith("http"))
    }
  }

  @Test
  fun `public poll calculates percentage correctly`() {
    val poll = com.example.data.repository.PollDataProvider.getDefaultPublicPoll()
    assertTrue("Poll question should be in Hindi", poll.questionHindi.isNotBlank())
    assertEquals(3, poll.options.size)
    val total = poll.totalVotes
    assertTrue(total > 0)
    val yesPercentage = poll.getPercentageFor("opt_yes")
    assertTrue("Yes option should have positive percentage", yesPercentage in 50..90)
  }
}
