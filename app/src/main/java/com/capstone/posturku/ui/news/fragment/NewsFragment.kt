package com.capstone.posturku.ui.news.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.posturku.ViewModelRepoFactory
import com.capstone.posturku.adapter.NewsAdapter
import com.capstone.posturku.data.repository.NewsRepository
import com.capstone.posturku.data.repository.PosturkuRepository
import com.capstone.posturku.databinding.FragmentNewsBinding
import com.capstone.posturku.model.news.Resource
import com.capstone.posturku.model.news.entities.Article
import com.capstone.posturku.ui.news.NewsReadActivity
import com.capstone.posturku.ui.news.NewsViewModel

class NewsFragment : Fragment() {
    private var binding: FragmentNewsBinding? = null
    private lateinit var viewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GetDataArticles()
        //getData()

    }

    private fun GetDataArticles(){
        PosturkuRepository.getInstance().GetAllArticles("", object : PosturkuRepository.IListArticle{
            override fun onSuccess(listStory: List<Article>) {
                binding?.progressBarNews?.visibility = View.GONE

                val newsAdapter = NewsAdapter(listStory)
                binding?.rvNews?.setHasFixedSize(true)
                binding?.rvNews?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding?.rvNews?.adapter = newsAdapter

                newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Article) {
                        showSelectedArticle(data)
                    }
                })
            }

            override fun onFailure(errorMessage: String) {
                binding?.progressBarNews?.visibility = View.GONE
            }
        })
    }

    private fun getData(){
        viewModel = ViewModelProvider(this, ViewModelRepoFactory(NewsRepository.getInstance()))[NewsViewModel::class.java]

        viewModel.getNews(requireContext(), "", 1, true)
        //observing news response using live data in news viewModel
        viewModel.news.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    binding?.progressBarNews?.visibility = View.GONE

                    val newsAdapter = NewsAdapter(response.data!!)
                    binding?.rvNews?.setHasFixedSize(true)
                    binding?.rvNews?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding?.rvNews?.adapter = newsAdapter

                    newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Article) {
                            showSelectedArticle(data)
                        }
                    })
                }
                is Resource.Error -> {
                    binding?.progressBarNews?.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding?.progressBarNews?.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showSelectedArticle(data: Article) {
        val intentToDetail = Intent(context, NewsReadActivity::class.java)
        intentToDetail.putExtra("DATA", data)
        startActivity(intentToDetail)
    }
}