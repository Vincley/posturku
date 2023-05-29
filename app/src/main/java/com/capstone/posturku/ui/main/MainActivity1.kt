package com.capstone.posturku.ui.main

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelFactory
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.databinding.ActivityMain1Binding
import com.capstone.posturku.databinding.ActivityMainBinding
import com.capstone.posturku.ui.camera.pose.PoseActivity
import com.capstone.posturku.ui.welcome.WelcomeActivity1
import com.capstone.posturku.utils.rotateBitmap
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity1 : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMain1Binding

    // For Camera
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        setupMenu()
        SetupCamera()
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

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin){
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
            mainViewModel.logout()
        }

    }

    private fun setupMenu() {

        binding.mainTip.setOnClickListener {
            Toast.makeText(this, "Still developing", Toast.LENGTH_SHORT).show()
        }
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
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.caremaButton.setOnClickListener { startCameraX() }
    }

    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
//        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
//        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(500)
//        val map = ObjectAnimator.ofFloat(binding.mapButton, View.ALPHA, 1f).setDuration(500)
//        val storybtn = ObjectAnimator.ofFloat(binding.storyButton, View.ALPHA, 1f).setDuration(500)
//
//        AnimatorSet().apply {
//            playSequentially(name, message, storybtn, map, logout)
//            startDelay = 500
//        }.start()
    }

    private fun startCameraX() {
//        val intent = Intent(this, CameraActivity::class.java)
        val intent = Intent(this, PoseActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val orientation = it.data?.getIntExtra("orientation", ExifInterface.ORIENTATION_UNDEFINED)

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera,
            )
            //binding.previewImageView.setImageBitmap(result)
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}