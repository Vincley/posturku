package com.capstone.posturku.ui.signup

import android.content.Context
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
import com.capstone.posturku.databinding.ActivitySignup1Binding
import com.capstone.posturku.model.UserModel
import com.capstone.posturku.ui.custom.EmailEditTextCustom
import com.capstone.posturku.ui.custom.PassEditTextCustom
import com.capstone.posturku.ui.custom.RePassEditTextCustom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignupActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivitySignup1Binding
    private lateinit var signupViewModel: SignupViewModel
    private lateinit var authRepository: AuthRepository

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth


    private var isEmailValid = false
    private var isPasssValid = false
    private var isRePassValid = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()

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

        val Password = binding.editTextTextPersonName2;
        val rePassword = binding.editTextRePassword;

        Password.setAfterTextChangedCallback(object : PassEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
                binding.textInputLayoutPassword.error = null
                isPasssValid = true
                rePassword.setOriginalPassword(Password.text.toString())
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

        rePassword.setAfterTextChangedCallback(object : RePassEditTextCustom.AfterTextChangedCallback {
            override fun onSuccess() {
                binding.textInputLayoutRePassword.error = null
                isRePassValid = true
            }

            override fun onFailure(errorMessage: String) {
                binding.textInputLayoutRePassword.error = errorMessage
                isRePassValid = false
            }

            override fun onEmpty() {
                binding.textInputLayoutRePassword.error = null
                isRePassValid = false
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
        binding.button3.setOnClickListener {
            binding.progressBarRegister.visibility = View.VISIBLE

            val name = binding.editTextTextPersonName3.text.toString()
            val email = binding.emailEditText1.text.toString()
            val password = binding.editTextTextPersonName2.text.toString()

            when {
                name.isEmpty() -> {
                    binding.tvSignUpName.error = getString(R.string.enter_name)
                   binding.progressBarRegister.visibility = View.GONE
                }
                email.isEmpty() -> {
                    binding.textInputLayoutPersonName.error = getString(R.string.enter_email)
                    binding.progressBarRegister.visibility = View.GONE
                }
                password.isEmpty() -> {
                    binding.textInputLayoutRePassword.error = getString(R.string.enter_password)
                    binding.progressBarRegister.visibility = View.GONE
                }
                !isEmailValid || !isPasssValid || !isRePassValid ->{
                    binding.progressBarRegister.visibility = View.GONE
                }

                else -> {
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
}