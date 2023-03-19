import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.model.Source
import com.anushka.newsapiclient.data.repository.NewsRepositoryImpl
import com.anushka.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.domain.repository.NewsRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

class NewsRepositoryTest {

    private lateinit var repository: NewsRepository
    private val remoteDataSource: NewsRemoteDataSource = mock()
    private val localDataSource: NewsLocalDataSource = mock()

    private val article1 = Article(
        1,
        "title1",
        "description1",
        "url1",
        "urlToImage1",
        Source("name", "name"),
        "content1",
        "asd",
        "asd"
    )
    private val article2 = Article(
        2,
        "title2",
        "description2",
        "url2",
        "urlToImage2",
        Source("name", "name"),
        "content2",
        "asd",
        "asd"
    )
    private val apiResponse = APIResponse(listOf(article1, article2),"asd",1)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = NewsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun getNewsHeadlines_success_response() = runBlocking {
        `when`(remoteDataSource.getTopHeadlines("us", 1))
            .thenReturn(Response.success(apiResponse))

        val result = repository.getNewsHeadlines("us", 1)

        assertEquals(Resource.Success(apiResponse).data?.articles?.get(0), result.data?.articles?.get(0))
    }

    @Test
    fun getSearchedNews_success_response() = runBlocking {
        `when`(remoteDataSource.getSearchedNews("us", "bitcoin", 1))
            .thenReturn(Response.success(apiResponse))

        val result = repository.getSearchedNews("us", "bitcoin", 1)

        assertEquals(Resource.Success(apiResponse).data?.articles?.get(0), result.data?.articles?.get(0))
    }

    @Test
    fun saveNews_success() = runBlocking {
        repository.saveNews(article1)

        // Verify that the method was called on the local data source
        verify(localDataSource).saveArticleToDB(article1)
    }

    @Test
    fun deleteNews_success() = runBlocking {
        repository.deleteNews(article1)

        // Verify that the method was called on the local data source
        verify(localDataSource).deleteArticlesFromDB(article1)
    }

    @Test
    fun getSavedNews_success_response() = runBlocking {
        `when`(localDataSource.getSavedArticles())
            .thenReturn(flowOf(listOf(article1, article2)))

        val result = repository.getSavedNews()

        assertEquals(listOf(article1, article2), result.first())
    }
}
