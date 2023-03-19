package com.anushka.newsapiclient.repository

import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.model.Source
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class FakeNewsRepository : NewsRepository {

    private val articles = mutableListOf<Article>()

    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {
        // return a default success response for testing purposes
        return Resource.Success(APIResponse(articles = listOf(Article(
            1,
            "Author",
            "Content",
            "Description",
            "asdf",
            Source("name", "name"),
            "asda",
            "asda",
            "asd"
        )),"asd",1))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        // return a default success response for testing purposes
        return Resource.Success(APIResponse(articles = listOf(Article(
            1,
            "Author",
            "Content",
            "Description",
            "asdf",
            Source("name", "name"),
            "asda",
            "asda",
            "asd"
        )),"asd",1))
    }

    override suspend fun saveNews(article: Article) {
        articles.add(article)
    }

    override suspend fun deleteNews(article: Article) {
        articles.remove(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return flow { emit(articles) }
    }
}
