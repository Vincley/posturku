package com.capstone.posturku.ui.news.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.posturku.adapter.NewsAdapter
import com.capstone.posturku.data.room.ArticleDb
import com.capstone.posturku.databinding.FragmentNewsBinding
import com.capstone.posturku.model.news.entities.Article
import com.capstone.posturku.model.news.entities.Source
import com.capstone.posturku.ui.news.FavoriteViewModel
import com.capstone.posturku.ui.news.NewsReadActivity

class FavoriteFragment(favoriteViewModel: FavoriteViewModel) : Fragment() {
    private var binding: FragmentNewsBinding? = null
    val viewModel = favoriteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { response ->
            if(response != null){
                binding?.progressBarNews?.visibility = View.GONE

                val listArticles = convertToArticleList(response)
                val newsAdapter = NewsAdapter(listArticles)
                binding?.rvNews?.setHasFixedSize(true)
                binding?.rvNews?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding?.rvNews?.adapter = newsAdapter

                newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Article) {
                        showSelectedArticle(data)
                    }
                })
            }
        })
    }

    private fun showSelectedArticle(data: Article) {
        val intentToDetail = Intent(context, NewsReadActivity::class.java)
        intentToDetail.putExtra("DATA", data)
        startActivity(intentToDetail)
    }

    private fun convertToArticleList(articleDbList: List<ArticleDb>): List<Article> {
        val articleList = mutableListOf<Article>()

        for (articleDb in articleDbList) {
            val source = Source("", articleDb.sourceName)
            val article = Article(
                articleDb.id,
                source,
                articleDb.author,
                articleDb.title,
                articleDb.description,
                articleDb.url,
                articleDb.urlToImage,
                articleDb.publishedAt,
                articleDb.content,
                articleDb.category
            )
            articleList.add(article)
        }
        return articleList
    }
}