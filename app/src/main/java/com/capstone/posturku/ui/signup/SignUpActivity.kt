package com.capstone.posturku.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.databinding.ActivitySignUpBinding
import com.capstone.posturku.model.UserModel
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.data.repository.AuthRepository
import com.capstone.posturku.model.RegisterRequest
import com.capstone.posturku.ui.custom.EmailEditTextCustom
import com.capstone.posturku.ui.custom.PassEditTextCustom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signupViewModel: SignupViewModel
    private lateinit var authRepository: AuthRepository

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth


    private var isEmailValid = false
    private var isPasssValid = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()

        // Initialising auth object
        auth = Firebase.auth

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
        binding.progressBarRegister.visibility = View.GONE

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

    }

    private fun setupViewModel() {
        authRepository = AuthRepository.getInstance()

        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignupViewModel::class.java]
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            binding.progressBarRegister.visibility = View.VISIBLE

            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = getString(R.string.enter_name)
                    binding.progressBarRegister.visibility = View.GONE
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = getString(R.string.enter_email)
                    binding.progressBarRegister.visibility = View.GONE
                }
                !isEmailValid ->{
                    binding.progressBarRegister.visibility = View.GONE
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = getString(R.string.enter_password)
                    binding.progressBarRegister.visibility = View.GONE
                }
                !isEmailValid -> {
                    binding.progressBarRegister.visibility = View.GONE
                }
                !isPasssValid -> {
                    binding.progressBarRegister.visibility = View.GONE
                }
                else -> {
                    val request = RegisterRequest(name, email, password)
//                    authRepository.Register(request, object : AuthRepository.DefaultCallback {
//                        override fun onSuccess() {
//                            signupViewModel.saveUser(UserModel(name, email, password, false, ""))
//                            binding.progressBarRegister.visibility = View.GONE
//                            callAlert()
//                        }
//                        override fun onFailure(errorMessage: String) {
//                            Toast.makeText(this@SignupActivity, errorMessage, Toast.LENGTH_SHORT).show()
//                            binding.progressBarRegister.visibility = View.GONE
//
//                        }
//                    })

                    // Register with Firebase
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                        binding.progressBarRegister.visibility = View.GONE

                        if (it.isSuccessful) {
                            signupViewModel.saveUser(UserModel(name, email, password, false, ""))
                            //Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                            callAlert()
                        } else {
                            Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun callAlert(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akunnya sudah jadi nih. Yuk, login dan mencari konveksi di kokibe.")
            setPositiveButton("Lanjut") { _, _ ->
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
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }
}