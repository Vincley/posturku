package com.capstone.posturku.ui.news

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelRoomFactory
import com.capstone.posturku.data.room.ArticleDb
import com.capstone.posturku.databinding.ActivityNewsReadBinding
import com.capstone.posturku.model.news.entities.Article

class NewsReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsReadBinding
    private lateinit var newsWebView: WebView
    private var isFavorite = false
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var article: Article


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        favoriteViewModel = obtainViewModel(this@NewsReadActivity)
        newsWebView = findViewById(R.id.news_webview)

        val article = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Article>("DATA", Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Article>("DATA")
        }
        if (article != null) {
            if(article.url != null){
                this.article = article
                newsWebView?.apply {
                    webViewClient = WebViewClient()
                    loadUrl(article.url)
                }
            }
        }

        setFavorite()
    }


    private fun setFavorite(){
        favoriteViewModel.getFavoriteByUrl(article.url).observe(this) { favorite ->
            if (favorite != null) {
                isFavorite = true;
                binding.fab.setImageResource(R.drawable.ic_favorite)
            }
            else{
                binding.fab.setImageResource(R.drawable.ic_unfavorite)
            }
        }


        binding.fab.setOnClickListener{
            if(article.url != null){
                val articleDb = convertTo(article)
                if(isFavorite){
                    favoriteViewModel.delete(articleDb)
                    isFavorite = false
                }else{
                    favoriteViewModel.insert(articleDb)
                    isFavorite = true
                }
            }
        }
    }




    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

    }

    private fun convertTo(article: Article): ArticleDb {
        return ArticleDb(
            id=article.id,
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url ?: "",
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
            category = article.category,
            sourceName = article.source?.name ?: ""
        )
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelRoomFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}