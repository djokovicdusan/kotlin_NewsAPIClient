package com.anushka.newsapiclient.presentation

import NewsScreen
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anushka.newsapiclient.MainActivity
import com.anushka.newsapiclient.R

@AndroidEntryPoint
class News2Fragment : Fragment() {

    private lateinit var viewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).viewModel
        return ComposeView(requireContext()).apply {
            setContent {
                NewsScreen(viewModel) { article ->
                    // Handle the article click event
                    onArticleClicked(article)
                }
            }
        }
    }

    private fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("selected_article", article)
        }
        findNavController().navigate(
            R.id.action_newsFragment_to_infoFragment, bundle
        )
    }
}
