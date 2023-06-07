package com.capstone.posturku.ui.profile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.R
import com.capstone.posturku.ViewModelRoomFactory
import com.capstone.posturku.data.room.profile.ProfileDb
import com.capstone.posturku.databinding.ActivityProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileActivity : AppCompatActivity() {
    private lateinit var personalinfo: LinearLayout
    private lateinit var experience: LinearLayout
    private lateinit var review: LinearLayout
    private lateinit var personalinfobtn: TextView
    private lateinit var experiencebtn: TextView
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        profileViewModel = obtainViewModel(this@ProfileActivity)

        initializeViews()
        setInitialVisibility()
        setButtonClickListeners()
        setAboutMeClickListener()
        setContactClickListener()
        setInterestsClickListener()
        setUI()
    }

    override fun onDestroy() {
        val profile = ProfileDb(
            aboutMe = binding.tvProfileAboutmeContent.text.toString(),
            name = binding.tvProfileName.text.toString(),
            phone = binding.tvProfilePhone.text.toString(),
            email = binding.tvProfileEmail.text.toString(),
            address = binding.tvProfileAddress.text.toString(),
            skill = binding.tvProfileSkill.text.toString(),
            hobby = binding.tvProfileHobby.text.toString()
        )
        profileViewModel.setData(profile)

        super.onDestroy()

    }

    private fun initializeViews() {
        personalinfo = findViewById(R.id.personalinfo)
        experience = findViewById(R.id.experience)
        review = findViewById(R.id.review)
        personalinfobtn = findViewById(R.id.personalinfobtn)
        experiencebtn = findViewById(R.id.experiencebtn)
//        reviewbtn = findViewById(R.id.reviewbtn)
    }

    private fun setInitialVisibility() {
        personalinfo.visibility = View.VISIBLE
        experience.visibility = View.GONE
        review.visibility = View.GONE
    }

    private fun setButtonClickListeners() {
        personalinfobtn.setOnClickListener {
            showPersonalInfo()
        }

        experiencebtn.setOnClickListener {
            showExperience()
        }

//        reviewbtn.setOnClickListener {
//            showReview()
//        }
    }

    private fun showPersonalInfo() {
        personalinfo.visibility = View.VISIBLE
        experience.visibility = View.GONE
        review.visibility = View.GONE
        personalinfobtn.setTextColor(resources.getColor(R.color.colorAccent))
        experiencebtn.setTextColor(resources.getColor(R.color.grey))
//        reviewbtn.setTextColor(resources.getColor(R.color.grey))
    }

    private fun showExperience() {
        personalinfo.visibility = View.GONE
        experience.visibility = View.VISIBLE
        review.visibility = View.GONE
        personalinfobtn.setTextColor(resources.getColor(R.color.grey))
        experiencebtn.setTextColor(resources.getColor(R.color.colorAccent))
//        reviewbtn.setTextColor(resources.getColor(R.color.grey))
    }

    private fun showReview() {
        personalinfo.visibility = View.GONE
        experience.visibility = View.GONE
        review.visibility = View.VISIBLE
        personalinfobtn.setTextColor(resources.getColor(R.color.grey))
        experiencebtn.setTextColor(resources.getColor(R.color.grey))
//        reviewbtn.setTextColor(resources.getColor(R.color.colorAccent))
    }

    private fun setAboutMeClickListener() {
        binding.tvProfileAboutme.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.profile_content_aboutme)

            val nameEditText: EditText? = bottomSheetDialog.findViewById(R.id.nameEditText)
            val saveButton: Button? = bottomSheetDialog.findViewById(R.id.saveButton)

            val current = binding.tvProfileAboutmeContent.text.toString()
            nameEditText?.setText(current)

            saveButton?.setOnClickListener {
                val new = nameEditText?.text.toString()

                binding.tvProfileAboutmeContent.text = new
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }

    private fun setContactClickListener() {
        binding.tvProfileContact.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.profile_content_contact)

            val name: EditText? = bottomSheetDialog.findViewById(R.id.et_profile_contact_name)
            val phone: EditText? = bottomSheetDialog.findViewById(R.id.et_profile_contact_phone)
            val address: EditText? = bottomSheetDialog.findViewById(R.id.et_profile_contact_address)
            val save: Button? = bottomSheetDialog.findViewById(R.id.et_profile_contact_save)

            val currentName = binding.tvProfileName.text.toString()
            val currentPhone = binding.tvProfilePhone.text.toString()
            val currentAddress = binding.tvProfileAddress.text.toString()

            name?.setText(currentName)
            phone?.setText(currentPhone)
            address?.setText(currentAddress)

            save?.setOnClickListener {
                val newName = name?.text.toString()
                val newPhone = phone?.text.toString()
                val newAddress = address?.text.toString()

                binding.tvProfileName.text = newName
                binding.tvProfilePhone.text = newPhone
                binding.tvProfileAddress.text = newAddress
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }

    private fun setInterestsClickListener() {
        binding.tvProfileInterest.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.profile_content_interest)

            val skill: EditText? = bottomSheetDialog.findViewById(R.id.et_profile_interest_skill)
            val hobby: EditText? = bottomSheetDialog.findViewById(R.id.et_profile_interest_hobby)
            val save: Button? = bottomSheetDialog.findViewById(R.id.et_profile_interest_save)

            val currentSkill = binding.tvProfileSkill.text.toString()
            val currentHobby = binding.tvProfileHobby.text.toString()

            skill?.setText(currentSkill)
            hobby?.setText(currentHobby)

            save?.setOnClickListener {
                val newSkill = skill?.text.toString()
                val newHobby = hobby?.text.toString()

                binding.tvProfileSkill.text = newSkill
                binding.tvProfileHobby.text = newHobby
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }

    private fun setUI(){
        profileViewModel.getProfile().observe(this) { data ->
            if(data != null){
                // AboutMe
                binding.tvProfileAboutmeContent.text = data.aboutMe

                // Contact
                binding.tvProfileName.text = data.name
                binding.tvProfilePhone.text = data.phone
                binding.tvProfileEmail.text = data.email
                binding.tvProfileAddress.text = data.address

                // Interest
                binding.tvProfileSkill.text = data.skill
                binding.tvProfileHobby.text = data.hobby
            }
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

    private fun obtainViewModel(activity: AppCompatActivity): ProfileViewModel {
        val factory = ViewModelRoomFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ProfileViewModel::class.java)
    }
}