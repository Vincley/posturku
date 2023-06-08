package com.capstone.posturku.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.posturku.databinding.ActivityWelcome1Binding
import com.capstone.posturku.ui.login.LoginActivity
import com.capstone.posturku.ui.signup.SignupActivity1


class WelcomeActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityWelcome1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcome1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()

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

    private fun setupAction() {
        binding.constraintLayout5.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.constraintLayout3.setOnClickListener {
            startActivity(Intent(this, SignupActivity1::class.java))
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.constraintLayout5, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.constraintLayout3, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }


        AnimatorSet().apply {
            playSequentially(title, together)
            start()
        }
    }

}