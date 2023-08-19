package com.anushka.newsapiclient

import NewsScreen
import NewsSearchBar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.core.internal.deps.guava.base.Joiner.on
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.presentation.News2Fragment
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class NewsComposablesTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }





    @Test
    fun newsSearchBar_displaysCorrectly() {
        val searchQuery = "Test Query"

        composeTestRule.setContent {
            NewsSearchBar(searchQuery = searchQuery, onQueryChanged = {})
        }

        // Provera da li ikona pretrage ispravno prikazuje
        composeTestRule.onNodeWithContentDescription("Search Icon").assertExists()

        // Provera da li se tekstualno polje ispravno prikazuje sa zadatim upitom
        composeTestRule.onNodeWithText(searchQuery).assertExists()
    }
    @Test
    fun testLoadingStateDisplaysCircularProgressIndicator() {
        val viewModel = mock(NewsViewModel::class.java)
        val liveData = MutableLiveData<Resource<APIResponse>>()
        liveData.value = Resource.Loading()

        Mockito.`when`(viewModel.newsHeadLines).thenReturn(liveData)

        composeTestRule.setContent {
            NewsScreen(viewModel) { /* Do nothing for article click */ }
        }

        composeTestRule.onNode(hasTestTag("Progress indicator")).assertIsDisplayed()
    }
    @Test
    fun testErrorStateDisplaysErrorMessage() {
        val errorMessage = "An error occurred"
        val viewModel = mock(NewsViewModel::class.java)
        val liveData = MutableLiveData<Resource<APIResponse>>()
        liveData.value = Resource.Error(errorMessage)

        Mockito.`when`(viewModel.newsHeadLines).thenReturn(liveData)

        composeTestRule.setContent {
            NewsScreen(viewModel) { /* Do nothing for article click */ }
        }

        composeTestRule.onNode(hasText(errorMessage)).assertIsDisplayed()
    }
    @Test
    fun testSuccessStateDisplaysNews() {
        val articles = listOf(
            Article(id = 1, author = "Author 1", title = "Title 1", description = "Description 1", urlToImage = null, publishedAt = "Today", content = "", source = null, url = ""),
            Article(id = 2, author = "Author 2", title = "Title 2", description = "Description 2", urlToImage = null, publishedAt = "Today", content = "", source = null, url = "")
        )
        val apiResponse = APIResponse(articles, "OK", 2)

        val viewModel = mock(NewsViewModel::class.java)
        val liveData = MutableLiveData<Resource<APIResponse>>()
        liveData.value = Resource.Success(apiResponse)

        Mockito.`when`(viewModel.newsHeadLines).thenReturn(liveData)

        composeTestRule.setContent {
            NewsScreen(viewModel) { /* Do nothing for article click */ }
        }

        composeTestRule.onNode(hasText("Title 1")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Title 2")).assertIsDisplayed()
    }







}

