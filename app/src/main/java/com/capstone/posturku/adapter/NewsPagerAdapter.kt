package com.capstone.posturku.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.posturku.ViewModelRoomFactory
import com.capstone.posturku.ui.news.FavoriteViewModel
import com.capstone.posturku.ui.news.fragment.FavoriteFragment
import com.capstone.posturku.ui.news.fragment.NewsFragment

class NewsPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    val factory = ViewModelRoomFactory.getInstance(activity.application)
    val favoriteViewModel = ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    val favoriteFragment = FavoriteFragment(favoriteViewModel)

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = NewsFragment()
            1 -> fragment = favoriteFragment
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}