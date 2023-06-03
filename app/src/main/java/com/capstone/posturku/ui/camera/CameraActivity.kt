package com.capstone.posturku.ui.camera

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.posturku.R
import com.capstone.posturku.databinding.ActivityCameraBinding
import java.io.IOException

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraSource: CameraSource1

    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cameraSource = CameraSource1(this, binding, this, application)

//        binding.captureImage.setOnClickListener { cameraSource.takePhoto() }
//        binding.switchCamera.setOnClickListener { cameraSource.switchCamera() }

        binding.captureImage.setOnClickListener {
            if (!isReady) {
                mMediaPlayer?.prepareAsync()
            }
            else {
                if (mMediaPlayer?.isPlaying as Boolean)
                {
                    mMediaPlayer?.pause()
                }
                else
                {
                    mMediaPlayer?.start()}
            }
        }
        binding.switchCamera.setOnClickListener {
            if (mMediaPlayer?.isPlaying as Boolean || isReady)
            {
                mMediaPlayer?.stop()
                isReady = false
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        init()
        cameraSource.startCamera()
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

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer?.setAudioAttributes(attribute)
        val afd = applicationContext.resources.openRawResourceFd(R.raw.guitar_background)
        try {
            mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
        }
        mMediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }
}