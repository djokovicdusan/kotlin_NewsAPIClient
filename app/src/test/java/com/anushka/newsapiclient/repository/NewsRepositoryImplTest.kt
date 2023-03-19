import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.repository.NewsRepositoryImpl
import com.anushka.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.anushka.newsapiclient.data.util.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

class NewsRepositoryImplTest {

    private lateinit var newsRemoteDataSource: NewsRemoteDataSource
    private lateinit var newsLocalDataSource: NewsLocalDataSource
    private lateinit var newsRepository: NewsRepositoryImpl

    @Before
    fun setup() {
        newsRemoteDataSource = mock(NewsRemoteDataSource::class.java)
        newsLocalDataSource = mock(NewsLocalDataSource::class.java)
        newsRepository = NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

    @Test
    fun `getNewsHeadlines returns success`() = runBlocking {
        // given
        val country = "us"
        val page = 1
        val apiResponse = APIResponse(emptyList(), "", 0)
        val response = Response.success(apiResponse)

        `when`(newsRemoteDataSource.getTopHeadlines(country, page)).thenReturn(response)

        // when
        val result = newsRepository.getNewsHeadlines(country, page)

        // then
        assertTrue(result is Resource.Success)
        assertEquals(apiResponse, result.data)
    }

    @Test
    fun `getNewsHeadlines returns error`() = runBlocking {
        // given
        val country = "us"
        val page = 1
        val errorBody = ResponseBody.create(null, "Error")
        val response = Response.error<APIResponse>(400, errorBody)

        `when`(newsRemoteDataSource.getTopHeadlines(country, page)).thenReturn(response)

        // when
        val result = newsRepository.getNewsHeadlines(country, page)

        // then
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `getSearchedNews returns success`() = runBlocking {
        // given
        val country = "us"
        val searchQuery = "bitcoin"
        val page = 1
        val apiResponse = APIResponse(emptyList(), "", 0)
        val response = Response.success(apiResponse)

        `when`(
            newsRemoteDataSource.getSearchedNews(
                country,
                searchQuery,
                page
            )
        ).thenReturn(response)

        // when
        val result = newsRepository.getSearchedNews(country, searchQuery, page)

        // then
        assertTrue(result is Resource.Success)
        assertEquals(apiResponse, result.data)
    }
}

