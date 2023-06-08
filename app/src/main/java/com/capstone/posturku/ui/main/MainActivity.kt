package com.capstone.posturku.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.data.preferences.UserPreference
import com.capstone.posturku.databinding.ActivityMain1Binding
import com.capstone.posturku.ui.aboutMe.AboutUsActivity
import com.capstone.posturku.ui.camera.pose.PoseActivity
import com.capstone.posturku.ui.history.HistoryActivity
import com.capstone.posturku.ui.news.NewsActivity
import com.capstone.posturku.ui.profile.ProfileActivity
import com.capstone.posturku.ui.welcome.WelcomeActivity1
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMain1Binding
    private lateinit var auth: FirebaseAuth

    // For Camera
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        InitAppCheck()
        setupView()
        setupViewModel()
        setupAction()
        setupMenu()
        SetupCamera()
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

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        auth = Firebase.auth
        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin){
//                 FirebaseAuth.AuthStateListener { t ->
//                     val firebaseUser = auth.currentUser
//                     if(firebaseUser != null){
//
//                     }
//                 }
                binding.tvMainBackgroundTop1.text = user.name
            } else {
                startActivity(Intent(this, WelcomeActivity1::class.java))
                finish()
            }
        })
    }

    private fun setupAction() {

        binding.footerHome.setOnClickListener{

        }

        binding.footerProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.footerAboutUs.setOnClickListener{
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        binding.footerLogout.setOnClickListener {
            auth.signOut()
            mainViewModel.logout()
        }

    }

    private fun setupMenu() {
        binding.mainTip.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)        }
        binding.mainHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)         }
        binding.mainFeedback.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.item_feedback, null)
//            val editText1 = dialogView.findViewById<EditText>(R.id.et_feedback)

            MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Submit") { dialog, which ->
                    // Respond to positive button press
                }
                .show()
        }
        binding.mainSetting.setOnClickListener{
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
        }

    }

    private fun SetupCamera(){
        binding.caremaButton.setOnClickListener { startCameraX() }
    }


    private fun startCameraX() {

        val intent = Intent(this, PoseActivity::class.java)
        startActivity(intent)
    }

    private fun InitAppCheck(){
        Firebase.initialize(context = this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}