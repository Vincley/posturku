//package com.capstone.posturku.ui.news
//
//import android.graphics.Color
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.view.WindowInsets
//import android.view.WindowManager
//import android.widget.EditText
//import android.widget.SearchView
//import androidx.annotation.StringRes
//import androidx.viewpager2.widget.ViewPager2
//import com.capstone.posturku.R
//import com.capstone.posturku.adapter.NewsPagerAdapter
//import com.capstone.posturku.databinding.ActivityNews1Binding
//import com.google.android.material.tabs.TabLayout
//import com.google.android.material.tabs.TabLayoutMediator
//
//class NewsActivity1 : AppCompatActivity() {
//    private lateinit var binding: ActivityNews1Binding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityNews1Binding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupView()
//        val sectionsPagerAdapter = NewsPagerAdapter(this)
//        val viewPager: ViewPager2 = binding.viewPager
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = binding.tabs
//        TabLayoutMediator(tabs, viewPager)
//        { tab, position ->
//            tab.text = resources.getString(TAB_TITLES[position])
//        }.attach()
//    }
//
//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//
//    }
//
//    companion object {
//        @StringRes
//        private val TAB_TITLES = intArrayOf(
//            R.string.tab_text_1,
//            R.string.tab_text_2
//        )
//    }
//}