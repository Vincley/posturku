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
import com.capstone.posturku.databinding.ActivityNewsReadBinding
import com.capstone.posturku.model.news.entities.Article

class NewsReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsReadBinding
    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private var isFavorite = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

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

        setFavorite()
    }


    private fun setFavorite(){
        binding.fab.setOnClickListener{
            if(isFavorite){
                binding.fab.setImageResource(R.drawable.ic_favorite)
                isFavorite = false
            }else{
                binding.fab.setImageResource(R.drawable.ic_unfavorite)
                isFavorite = true
            }

        }
    }

//    private fun setFavorite(username: String, avatarUrl: String){
//        detailViewModel.getFavoriteUserByUsername(username).observe(this) { favorite ->
//            if (favorite != null) {
//                isFavorite = true;
//                binding.FavoriteButtonId.setImageResource(R.drawable.ic_favorite)
//            }
//            else{
//                binding.FavoriteButtonId.setImageResource(R.drawable.ic_unfavorite)
//            }
//        }
//
//        var favorite = FavoriteUser()
//        binding.FavoriteButtonId.setOnClickListener{
//            favorite.let {
//                favorite?.username = username
//                favorite?.avatarUrl = avatarUrl
//            }
//
//            if(isFavorite){
//                detailViewModel.delete(favorite as FavoriteUser)
//                showToast(getString(R.string.delete_favorite))
//                isFavorite = false
//            }else{
//                detailViewModel.insert(favorite as FavoriteUser)
//                showToast(getString(R.string.add_favorite))
//                isFavorite = true
//            }
//
//        }
//    }


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
}