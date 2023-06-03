package com.capstone.posturku.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.databinding.ActivityMain1Binding
import com.capstone.posturku.ui.camera.pose.PoseActivity
import com.capstone.posturku.ui.news.NewsActivity
import com.capstone.posturku.ui.welcome.WelcomeActivity1
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity1 : AppCompatActivity() {

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
        val firebaseUser = auth.currentUser
        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin && firebaseUser != null){
                //binding.nameTextView.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, WelcomeActivity1::class.java))
                finish()
            }
        })
    }

    private fun setupAction() {

        binding.footerHome.setOnClickListener{
//            val intent = Intent(this@MainActivity, ListCeritaActivity::class.java)
//            startActivity(intent)
        }

        binding.footerProfile.setOnClickListener {
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
           // startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.footerAboutUs.setOnClickListener{
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this@MainActivity, MapActivity::class.java)
//            startActivity(intent)
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
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
        }
        binding.mainFeedback.setOnClickListener {
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
        }
        binding.mainSetting.setOnClickListener{
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
        }

    }

    private fun SetupCamera(){
//        if (!allPermissionsGranted()) {
//            ActivityCompat.requestPermissions(
//                this,
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//            )
//        }
        binding.caremaButton.setOnClickListener { startCameraX() }
    }


    private fun startCameraX() {
//        val intent = Intent(this, CameraActivity::class.java)
//        val intent = Intent(this, PoseActivity::class.java)
//        launcherIntentCameraX.launch(intent)

        val intent = Intent(this, PoseActivity::class.java)
        startActivity(intent)
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (!allPermissionsGranted()) {
//                Toast.makeText(
//                    this,
//                    getString(R.string.permission_denied),
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }

//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }

//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERA_X_RESULT) {
//            val myFile = it.data?.getSerializableExtra("picture") as File
//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
//            val orientation = it.data?.getIntExtra("orientation", ExifInterface.ORIENTATION_UNDEFINED)
//
//            getFile = myFile
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(getFile?.path),
//                isBackCamera,
//            )
//            //binding.previewImageView.setImageBitmap(result)
//        }
//    }

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