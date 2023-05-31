//package com.capstone.posturku.model.cerita
//
//import android.os.Parcelable
//import com.google.gson.annotations.SerializedName
//import kotlinx.android.parcel.Parcelize
//
//
//
//@Parcelize
//data class CeritaResponse(
//
//    @field:SerializedName("listStory")
//    val listStory: List<ListStoryItem>,
//
//    @field:SerializedName("error")
//    val error: Boolean,
//
//    @field:SerializedName("message")
//    val message: String
//) : Parcelable
//
//@Parcelize
//data class ListStoryItem(
//
//    @field:SerializedName("name")
//    val name: String,
//
//    @field:SerializedName("photoUrl")
//    val photoUrl: String,
//
//
//    @field:SerializedName("description")
//    val description: String,
//
//    @field:SerializedName("id")
//    val id: String,
//
//    @field:SerializedName("lat")
//    val lat: Double,
//
//    @field:SerializedName("lon")
//    val lon: Double
//): Parcelable