package com.capstone.posturku.api

import com.capstone.posturku.model.LoginRequest
import com.capstone.posturku.model.LoginResponse
import com.capstone.posturku.model.RegisterRequest
import com.capstone.posturku.model.RegisterResponse
import com.capstone.posturku.model.news.ArticleResponse
import com.capstone.posturku.model.news.entities.Article
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//
//    @POST("login")
//    fun Login(@Body request: LoginRequest): Call<LoginResponse>
//
//    @POST("register")
//    fun Register(@Body request: RegisterRequest): Call<RegisterResponse>
//
//
//    @GET("stories")
//    fun GetAllStories(
//        @Header("Authorization") token: String
//    ): Call<Article>

    @GET("articles")
    fun GetArticles(
        //@Header("Authorization") token: String,
        @Query("keyword") keyword: String,
//        @Query("size") size: Int
    ): Call<ArticleResponse>
//
//    @GET("/v1/stories")
//    fun GetStoriesByLocation(
//        @Header("Authorization") token: String,
//        @Query("location") location: Int
//    ): Call<Article>
//
//    @Multipart
//    @POST("stories")
//    fun uploadImage(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//    ): Call<Article>
}