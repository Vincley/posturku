package com.capstone.posturku.ui.history

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.posturku.R
import com.capstone.posturku.databinding.ActivityHistoryBinding
import com.capstone.posturku.ui.history.temp.Singer
import com.capstone.posturku.ui.history.temp.SingerAdapter2
import com.capstone.posturku.ui.history.temp.SingerRepo
import xyz.sangcomz.stickytimelineview.callback.SectionCallback
import xyz.sangcomz.stickytimelineview.model.SectionInfo

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    val icFinkl: Drawable? by lazy {
        AppCompatResources.getDrawable(this, R.drawable.ic_finkl)
    }
    val icBuzz: Drawable? by lazy {
        AppCompatResources.getDrawable(this, R.drawable.ic_buzz)
    }
    val icWannaOne: Drawable? by lazy {
        AppCompatResources.getDrawable(this, R.drawable.ic_wannaone)
    }
    val icGirlsGeneration: Drawable? by lazy {
        AppCompatResources.getDrawable(this, R.drawable.ic_girlsgeneration)
    }
    val icSolo: Drawable? by lazy {
        AppCompatResources.getDrawable(this, R.drawable.ic_solo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

        initVerticalRecyclerView()

    }

    private fun initVerticalRecyclerView() {
        val singerList = getSingerList()
        val rv = binding.verticalRecyclerView

        rv.adapter = SingerAdapter2(singerList)

        //Currently only LinearLayoutManager is supported.
        rv.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        rv.addItemDecoration(getSectionCallback(singerList))
    }

    //Get data method
    private fun getSingerList(): List<Singer> = SingerRepo().singerList


    //Get SectionCallback method
    private fun getSectionCallback(singerList: List<Singer>): SectionCallback {
        return object : SectionCallback {
            //In your data, implement a method to determine if this is a section.
            override fun isSection(position: Int): Boolean =
                singerList[position].debuted != singerList[position - 1].debuted

            //Implement a method that returns a SectionHeader.
            override fun getSectionHeader(position: Int): SectionInfo? {
                val singer = singerList[position]
                return SectionInfo(singer.debuted, singer.group)
            }

        }
    }

    private fun getSectionCallbackWithDrawable(singerList: List<Singer>): SectionCallback {
        return object : SectionCallback {
            //In your data, implement a method to determine if this is a section.
            override fun isSection(position: Int): Boolean =
                singerList[position].debuted != singerList[position - 1].debuted

            //Implement a method that returns a SectionHeader.
            override fun getSectionHeader(position: Int): SectionInfo? {
                val singer = singerList[position]
                val dot: Drawable? = when (singer.group) {
                    "FIN.K.L" -> {
                        icFinkl
                    }
                    "Girls' Generation" -> {
                        icGirlsGeneration
                    }
                    "Buzz" -> {
                        icBuzz
                    }
                    "Wanna One" -> {
                        icWannaOne
                    }
                    else -> icSolo
                }
                return SectionInfo(singer.debuted, singer.group, dot)
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
}