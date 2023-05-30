package com.capstone.posturku.ui.news

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.model.news.Hero
import com.capstone.posturku.model.news.NewsModel
import com.capstone.posturku.utils.news.Constants.NEWS_CONTENT
import com.capstone.posturku.utils.news.Constants.NEWS_DESCRIPTION
import com.capstone.posturku.utils.news.Constants.NEWS_IMAGE_URL
import com.capstone.posturku.utils.news.Constants.NEWS_PUBLICATION_TIME
import com.capstone.posturku.utils.news.Constants.NEWS_SOURCE
import com.capstone.posturku.utils.news.Constants.NEWS_TITLE
import com.capstone.posturku.utils.news.Constants.NEWS_URL
import java.util.ArrayList

class NewsReadActivity : AppCompatActivity() {
    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsModel>
    private lateinit var hero: Hero



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_read)
        setupView()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        newsWebView = findViewById(R.id.news_webview)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        //loading data into list
        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(NEWS_URL)

        val hero = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Hero>("DATA", Hero::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Hero>("DATA")
        }
        if (hero != null) {
            val text = "Name : ${hero.name.toString()},\nEmail : ${hero.description}"
              Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }


//        val newsContent =
//            intent.getStringExtra(NEWS_CONTENT) + ". get paid version to hear full news. "
//        newsData.add(
//            NewsModel(
//                intent.getStringExtra(NEWS_TITLE)!!,
//                intent.getStringExtra(NEWS_IMAGE_URL),
//                intent.getStringExtra(NEWS_DESCRIPTION),
//                newsUrl,
//                intent.getStringExtra(NEWS_SOURCE),
//                intent.getStringExtra(NEWS_PUBLICATION_TIME),
//                newsContent
//            )
//        )
//
//        // Webview
//        newsWebView.apply {
//            settings.apply {
//                domStorageEnabled = true
//                loadsImagesAutomatically = true
//                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//                javaScriptEnabled = true
//            }
//            webViewClient = WebViewClient()
//            webChromeClient = WebChromeClient()
//        }
//
//
//        if (newsUrl != null) {
//            newsWebView.loadUrl(newsUrl)
//        }
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