package com.capstone.posturku.ui.welcome

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.posturku.R
import com.capstone.posturku.databinding.ActivityWelcome1Binding
import com.capstone.posturku.databinding.ActivityWelcomeBinding
import com.capstone.posturku.ui.login.LoginActivity
import com.capstone.posturku.ui.signup.SignupActivity


class WelcomeActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityWelcome1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcome1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
       // playAnimation()

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
        binding.constraintLayout4.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.imageView3.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


        binding.constraintLayout3.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.constraintLayout2.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.imageView2.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

}