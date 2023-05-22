package com.capstone.posturku.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.data.repository.AuthRepository
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.databinding.ActivityLoginBinding
import com.capstone.posturku.model.UserModel
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.model.LoginRequest
import com.capstone.posturku.model.LoginResult
import com.capstone.posturku.ui.custom.EmailEditTextCustom
import com.capstone.posturku.ui.custom.PassEditTextCustom
import com.capstone.posturku.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    private var isEmailValid = false
    private var isPasssValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()
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
        binding.progressBarLogin.visibility = View.GONE

        binding.passwordEditText.setAfterTextChangedCallback(object : PassEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
                binding.passwordEditTextLayout.error = null
                isPasssValid = true
            }

            override fun onFailure(errorMessage: String) {
                binding.passwordEditTextLayout.error = errorMessage
                isPasssValid = false
            }
        })

        binding.emailEditText.setAfterTextChangedCallback(object : EmailEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
                binding.emailEditTextLayout.error = null
                isEmailValid = true
            }

            override fun onFailure(errorMessage: String) {
                binding.emailEditTextLayout.error = errorMessage
                isEmailValid = false
            }
        })

    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[LoginViewModel::class.java]
        authRepository = AuthRepository.getInstance()

        loginViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            binding.progressBarLogin.visibility = View.VISIBLE
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val passwordError = binding.passwordEditText.error;
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                    binding.progressBarLogin.visibility = View.GONE
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                    binding.progressBarLogin.visibility = View.GONE
                }
                !isEmailValid -> {
                    binding.progressBarLogin.visibility = View.GONE
                }
                !isPasssValid -> {
                    binding.progressBarLogin.visibility = View.GONE
                }

                else -> {
                    val request = LoginRequest(email, password)
//                    authRepository.login(request, object : AuthRepository.LoginCallback {
//                        override fun onSuccess(result: LoginResult) {
//                            loginViewModel.login(result)
//                            binding.progressBarLogin.visibility = View.GONE
//                            callAlert()
//                        }
//                        override fun onFailure(errorMessage: String) {
//                            Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
//                        }
//                    })

                    // Login with Firebase
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                        binding.progressBarLogin.visibility = View.GONE
                        if (it.isSuccessful) {
                            val result = LoginResult(email, password, "")
                            loginViewModel.login(result)
                            callAlert()
                            //Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun callAlert(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, message, emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout, login)
            startDelay = 500
        }.start()
    }


}