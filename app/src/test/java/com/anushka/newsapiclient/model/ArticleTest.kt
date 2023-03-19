import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.model.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleTest {

    @Test
    fun `article should have correct properties`() {
        // Given
        val id = 123
        val author = "John Smith"
        val content = "Lorem ipsum dolor sit amet"
        val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        val publishedAt = "2022-03-19T12:34:56Z"
        val source = Source("news-source", "News Source")
        val title = "Lorem ipsum"
        val url = "https://example.com/article"
        val urlToImage = "https://example.com/article-image.jpg"

        // When
        val article = Article(id, author, content, description, publishedAt, source, title, url, urlToImage)

        // Then
        assertEquals(id, article.id)
        assertEquals(author, article.author)
        assertEquals(content, article.content)
        assertEquals(description, article.description)
        assertEquals(publishedAt, article.publishedAt)
        assertEquals(source, article.source)
        assertEquals(title, article.title)
        assertEquals(url, article.url)
        assertEquals(urlToImage, article.urlToImage)
    }

}
