package com.anushka.newsapiclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.anushka.newsapiclient.data.db.ArticleDAO
import com.anushka.newsapiclient.data.db.ArticleDatabase
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.model.Source
import com.google.common.truth.Truth
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()



    private lateinit var dao: ArticleDAO
    private lateinit var database: ArticleDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).build()
        dao = database.getArticleDAO()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveArticleTest() = runBlocking {
        val article = Article(
            1,
            "Author",
            "Content",
            "Description",
            "asdf",
            Source("name", "name"),
            "asda",
            "asda",
            "asd"
        )
        dao.insert(article)
        val articlesFlow = dao.getAllArticles()
        var articleFromList: Article? = null
        val testArticleList = articlesFlow.take(1).toList().get(0);

        Truth.assertThat(testArticleList[0]).isEqualTo(article)
    }

    @Test
    fun deleteArticleTest() = runBlocking {
        val article = Article(
            1,
            "Author",
            "Content",
            "Description",
            "asdf",
            Source("name", "name"),
            "asda",
            "asda",
            "asd"
        )
        dao.insert(article)
        dao.deleteArticle(article)
        dao.getAllArticles().test {
            val list = awaitItem()
            assert(list.size == 0)
            cancel()
        }


    }


    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

}