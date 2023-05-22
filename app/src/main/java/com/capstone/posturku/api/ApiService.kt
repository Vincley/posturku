package com.capstone.posturku.api

import com.capstone.posturku.model.LoginRequest
import com.capstone.posturku.model.LoginResponse
import com.capstone.posturku.model.RegisterRequest
import com.capstone.posturku.model.RegisterResponse
import com.capstone.posturku.model.cerita.CeritaResponse
import com.capstone.posturku.model.cerita.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login")
    fun Login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun Register(@Body request: RegisterRequest): Call<RegisterResponse>


    @GET("stories")
    fun GetAllStories(
        @Header("Authorization") token: String
    ): Call<CeritaResponse>

    @GET("stories")
    suspend fun GetAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): CeritaResponse

    @GET("/v1/stories")
    fun GetStoriesByLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ): Call<CeritaResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<UploadResponse>
}