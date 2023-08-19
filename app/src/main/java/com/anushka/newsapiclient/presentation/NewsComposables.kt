import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.anushka.newsapiclient.R
import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.presentation.viewmodel.NewsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter

@Composable
fun NewsScreen(viewModel: NewsViewModel, onArticleClick: (Article) -> Unit) {
    // For maintaining search state
    // Obtains the ViewModel from the current backstack entry
    val newsData: Resource<APIResponse>? by viewModel.newsHeadLines.observeAsState()
    var searchQuery by remember { mutableStateOf("") }


    // Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.layout_background))
            .padding(5.dp)
    ) {
        NewsSearchBar(
            searchQuery = searchQuery,
            onQueryChanged = { query ->
                searchQuery = query
                viewModel.searchNews("us", query, 1) // Adjust as per your requirements
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        when (newsData) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize().testTag("Progress indicator"))

            }
            is Resource.Success -> {
                val articles = (newsData as Resource.Success<APIResponse>).data?.articles
                if (articles != null) {
                    NewsList(articles, onArticleClick)
                } else {
                    Text("No articles available")
                }
            }
            is Resource.Error -> {
                Text(text = (newsData as Resource.Error<APIResponse>).message ?: "An error occurred", color = Color.Red)
            }
        }
    }
}

@Composable
fun NewsSearchBar(searchQuery: String, onQueryChanged: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.search_background),
                shape = RoundedCornerShape(4.dp)
            )
            .height(56.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 5.dp)
        )
        BasicTextField(
            value = searchQuery,
            onValueChange = onQueryChanged,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black), // Adjust text color to your preference
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun NewsList(news: List<Article>, onArticleClick: (Article) -> Unit) {
    LazyColumn {
        items(news) { article ->
            NewsItem(article, onArticleClick)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun NewsItem(article: Article, onArticleClick: (Article) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onArticleClick(article) }
            .background(colorResource(id = R.color.list_background))
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = article.title ?: "",
                color = colorResource(id = R.color.list_text),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberImagePainter(data = article.urlToImage),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = article.description?.substring(0..10) ?: "",
                        color = colorResource(id = R.color.list_text),
                        fontSize = 12.sp,
                        maxLines = 5,
                        modifier = Modifier.weight(3f)
                    )

                    Text(
                        text = article.publishedAt ?: "",
                        color = colorResource(id = R.color.list_text)
                    )

                    Text(
                        text = article.source?.name ?: "",
                        color = colorResource(id = R.color.list_text),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


