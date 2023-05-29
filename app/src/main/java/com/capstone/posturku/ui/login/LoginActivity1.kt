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
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.data.repository.AuthRepository
import com.capstone.posturku.databinding.ActivityLogin1Binding
import com.capstone.posturku.databinding.ActivityLoginBinding
import com.capstone.posturku.model.LoginRequest
import com.capstone.posturku.model.LoginResult
import com.capstone.posturku.model.UserModel
import com.capstone.posturku.ui.custom.EmailEditTextCustom
import com.capstone.posturku.ui.custom.PassEditTextCustom
import com.capstone.posturku.ui.main.MainActivity
import com.capstone.posturku.ui.main.MainActivity1
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity1 : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var binding: ActivityLogin1Binding
    private lateinit var user: UserModel

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    // Login by Google
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    private lateinit var firebaseAuth: FirebaseAuth

    private var isEmailValid = false
    private var isPasssValid = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()

        setupLoginByGoogle()
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

        binding.editTextTextPersonName2.setAfterTextChangedCallback(object : PassEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
                binding.textInputLayoutPassword.error = null
                isPasssValid = true
            }

            override fun onFailure(errorMessage: String) {
                binding.textInputLayoutPassword.error = errorMessage
                isPasssValid = false
            }

            override fun onEmpty() {
                binding.textInputLayoutPassword.error = null
                isEmailValid = false
            }
        })

        binding.emailEditText1.setAfterTextChangedCallback(object : EmailEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
               // binding.emailEditTextLayout.error = null
                isEmailValid = true
            }

            override fun onFailure(errorMessage: String) {
                binding.textInputLayoutPersonName.error = errorMessage
                isEmailValid = false
            }

            override fun OnEmpty() {
                binding.textInputLayoutPersonName.error = null
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

    private fun setupLoginByGoogle(){
        FirebaseApp.initializeApp(this)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)


        binding.button2.setOnClickListener{
            signInGoogle()
        }
    }

    private fun setupAction() {
        binding.button3.setOnClickListener {
            binding.progressBarLogin.visibility = View.VISIBLE
            val email = binding.emailEditText1.text.toString()
            val password = binding.editTextTextPersonName2.text.toString()
            //val passwordError = binding.passwordEditText.error;
            when {
                email.isEmpty() -> {
                   // binding.emailEditTextLayout.error = "Masukkan email"
                    binding.progressBarLogin.visibility = View.GONE
                }
                password.isEmpty() -> {
                   // binding.passwordEditTextLayout.error = "Masukkan password"
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

    private  fun signInGoogle(){

        val signInIntent: Intent =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
//            firebaseAuthWithGoogle(account!!)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                val result = LoginResult("", "", "")
                loginViewModel.login(result)
                callAlert()
            }
        } catch (e: ApiException){
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun callAlert(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity1::class.java)
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
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
//        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
//        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
//        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
//        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
//        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
//        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
//        val login2 = ObjectAnimator.ofFloat(binding.loginButton2, View.ALPHA, 1f).setDuration(500)
//
//        AnimatorSet().apply {
//            playSequentially(title, message, emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout, login, login2)
//            startDelay = 500
//        }.start()
    }
}