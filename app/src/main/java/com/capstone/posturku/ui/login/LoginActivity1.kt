package com.capstone.posturku.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.databinding.ActivityLogin1Binding
import com.capstone.posturku.model.LoginResult
import com.capstone.posturku.model.UserModel
import com.capstone.posturku.ui.custom.EmailEditTextCustom
import com.capstone.posturku.ui.custom.PassEditTextCustom
import com.capstone.posturku.ui.main.MainActivity1
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity1 : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
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
        hideSystemUI()

        setupView()
        setupViewModel()
        setupAction()
        setupLoginByGoogle()
    }

    private fun setupView() {
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
                isPasssValid = false
            }
        })

        binding.emailEditText1.setAfterTextChangedCallback(object : EmailEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
                binding.textInputLayoutPersonName.error = null
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
            when {
                email.isEmpty() -> {
                   binding.textInputLayoutPersonName.error = "Masukkan email"
                    binding.progressBarLogin.visibility = View.GONE
                }
                password.isEmpty() -> {
                    binding.textInputLayoutPersonName.error = "Masukkan password"
                    binding.progressBarLogin.visibility = View.GONE
                }
                !isEmailValid || !isPasssValid -> {
                    binding.progressBarLogin.visibility = View.GONE
                }

                else -> {
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