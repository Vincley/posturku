//package com.capstone.posturku.ml
//
//import android.content.Context
//import android.graphics.PointF
//import android.util.Log
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.capstone.posturku.data.pose.BodyPart
//import com.capstone.posturku.data.pose.Device
//import org.junit.Before
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class MoveNetThunderTest{
//    companion object {
//        private const val TEST_INPUT_IMAGE1 = "image1.png"
//        private const val TEST_INPUT_IMAGE2 = "image2.jpg"
//        private const val ACCEPTABLE_ERROR = 15f
//    }
//
//    private lateinit var poseDetector: PoseDetector
//    private lateinit var appContext: Context
//    private lateinit var expectedDetectionResult: List<Map<BodyPart, PointF>>
//
//    @Before
//    fun setup() {
//        appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        poseDetector = MoveNet.create(appContext, Device.CPU, ModelType.Thunder)
//        expectedDetectionResult =
//            EvaluationUtils.loadCSVAsset("pose_landmark_truth.csv")
//    }
//}