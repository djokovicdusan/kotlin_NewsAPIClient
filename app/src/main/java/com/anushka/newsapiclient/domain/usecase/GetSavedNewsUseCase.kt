package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }
}