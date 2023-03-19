package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.model.Source
import com.anushka.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSavedNewsUseCaseTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Test
    fun `execute should return a list of saved articles from the repository`() = runBlocking {
        // Given
        val savedArticles = listOf(
            (Article(
                1,
                "Author",
                "Content",
                "Description",
                "asdf",
                Source("name", "name"),
                "asda",
                "asda",
                "asd"
            )),
            (Article(
                1,
                "Author",
                "Content",
                "Description",
                "asdf",
                Source("name", "name"),
                "asda",
                "asda",
                "asd"
            ))
        )
        Mockito.`when`(newsRepository.getSavedNews()).thenReturn(flowOf(savedArticles))

        // When
        val result = GetSavedNewsUseCase(newsRepository).execute().toList()

        // Then
        assertEquals(savedArticles, result[0])
    }
}
