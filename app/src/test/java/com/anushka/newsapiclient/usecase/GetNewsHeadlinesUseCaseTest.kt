package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNewsHeadlinesUseCaseTest {

    @Mock
    lateinit var newsRepository: NewsRepository

    lateinit var getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase

    @Before
    fun setup() {
        getNewsHeadlinesUseCase = GetNewsHeadlinesUseCase(newsRepository)
    }

    @Test
    fun `execute should return a success resource`() = runBlocking {
        // Given
        val country = "us"
        val page = 1
        val response = APIResponse(emptyList(),"a",1)
        `when`(newsRepository.getNewsHeadlines(country, page)).thenReturn(Resource.Success(response))

        // When
        val result = getNewsHeadlinesUseCase.execute(country, page)

        // Then
        assertEquals(Resource.Success(response).data, result.data)
    }

    @Test
    fun `execute should return an error resource`() = runBlocking {
        // Given
        val country = "us"
        val page = 1
        val errorMessage = "An error occurred"
        `when`(newsRepository.getNewsHeadlines(country, page)).thenReturn(Resource.Error(errorMessage))

        // When
        val result = getNewsHeadlinesUseCase.execute(country, page)

        // Then
        @Suppress("UNCHECKED_CAST")
        val expected = Resource.Error<APIResponse>(errorMessage)
        assertEquals(expected.data, result.data)
    }

}
