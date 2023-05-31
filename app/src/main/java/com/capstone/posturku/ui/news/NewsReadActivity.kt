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
import com.capstone.posturku.R
import com.capstone.posturku.model.news.NewsModel
import com.capstone.posturku.model.news.entities.Article
import java.util.ArrayList

class NewsReadActivity : AppCompatActivity() {
    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsModel>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_read)
        setupView()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        newsWebView = findViewById(R.id.news_webview)

        val article = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Article>("DATA", Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Article>("DATA")
        }
        if (article != null) {
            if(article.url != null){
                newsWebView?.apply {
                    webViewClient = WebViewClient()
                    loadUrl(article.url)
                }
            }
        }
    }

    private fun setupView() {
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
}