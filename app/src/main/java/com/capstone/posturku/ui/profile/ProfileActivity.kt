package com.capstone.posturku.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.capstone.posturku.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var personalinfo: LinearLayout
    private lateinit var experience: LinearLayout
    private lateinit var review: LinearLayout
    private lateinit var personalinfobtn: TextView
    private lateinit var experiencebtn: TextView
    private lateinit var reviewbtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        personalinfo = findViewById(R.id.personalinfo)
        experience = findViewById(R.id.experience)
        review = findViewById(R.id.review)
        personalinfobtn = findViewById(R.id.personalinfobtn)
        experiencebtn = findViewById(R.id.experiencebtn)
        reviewbtn = findViewById(R.id.reviewbtn)

        // Making personal info visible
        personalinfo.visibility = View.VISIBLE
        experience.visibility = View.GONE
        review.visibility = View.GONE

        personalinfobtn.setOnClickListener {
            personalinfo.visibility = View.VISIBLE
            experience.visibility = View.GONE
            review.visibility = View.GONE
            personalinfobtn.setTextColor(resources.getColor(R.color.blue))
            experiencebtn.setTextColor(resources.getColor(R.color.grey))
            reviewbtn.setTextColor(resources.getColor(R.color.grey))
        }

        experiencebtn.setOnClickListener {
            personalinfo.visibility = View.GONE
            experience.visibility = View.VISIBLE
            review.visibility = View.GONE
            personalinfobtn.setTextColor(resources.getColor(R.color.grey))
            experiencebtn.setTextColor(resources.getColor(R.color.blue))
            reviewbtn.setTextColor(resources.getColor(R.color.grey))
        }

        reviewbtn.setOnClickListener {
            personalinfo.visibility = View.GONE
            experience.visibility = View.GONE
            review.visibility = View.VISIBLE
            personalinfobtn.setTextColor(resources.getColor(R.color.grey))
            experiencebtn.setTextColor(resources.getColor(R.color.grey))
            reviewbtn.setTextColor(resources.getColor(R.color.blue))
        }
    }
}