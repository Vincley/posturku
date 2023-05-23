package com.capstone.posturku.ml

import android.graphics.Bitmap
import com.capstone.posturku.data.pose.Person

interface PoseDetector : AutoCloseable {

    fun estimatePoses(bitmap: Bitmap): List<Person>

    fun lastInferenceTimeNanos(): Long
}
