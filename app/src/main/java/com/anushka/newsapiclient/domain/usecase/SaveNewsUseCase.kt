package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
  suspend fun execute(article: Article)=newsRepository.saveNews(article)
}