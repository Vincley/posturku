package com.capstone.posturku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.posturku.api.ApiConfig
import com.capstone.posturku.model.*
import com.capstone.posturku.model.news.entities.Article
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AuthRepository() {


    fun login(request: LoginRequest, callback: LoginCallback) {
        val client = ApiConfig.getApiService().Login(request)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>)
            {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val t = responseBody!!.loginResult
                        callback.onSuccess(t)
                    }
                } else {
                    callback.onFailure("Login failed: ${response.code()}")
                    Log.e(AuthRepository.TAG, "onFailure - not success: ${response.isSuccessful}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onFailure("Login failed: ${t.message}")
                Log.e(AuthRepository.TAG, "onFailure - bad: ${t.message}")
            }
        })
    }

    fun Register(request: RegisterRequest, callback: DefaultCallback) {
        val client = ApiConfig.getApiService().Register(request)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>)
            {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback.onSuccess()
                    }
                } else {
                    callback.onFailure("Register failed: ${response.code()}")
                    Log.e(AuthRepository.TAG, "onFailure - not success: ${response.isSuccessful}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback.onFailure("Register failed: ${t.message}")
                Log.e(AuthRepository.TAG, "onFailure - bad: ${t.message}")
            }
        })
    }

    fun GetAllStories(token:String, callback: ListCeritaCallback) {
        val client = ApiConfig.getApiService().GetAllStories(token)
        client.enqueue(object : Callback<Article> {
            override fun onResponse(
                call: Call<Article>,
                response: Response<Article>)
            {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        val value = responseBody?.listStory
//                        if(value != null){
//                            callback.onSuccess(value!!)
//                        }
                    }
                } else {
                    callback.onFailure("get data failed: ${response.code()}")
                    Log.e(AuthRepository.TAG, "onFailure - not success: ${response.isSuccessful}")
                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                callback.onFailure("get data failed: ${t.message}")
                Log.e(AuthRepository.TAG, "onFailure - bad: ${t.message}")
            }
        })
    }

    fun GetAllStories(token:String) : LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(token)
            }
        ).liveData
    }


    fun GetStoriesByLocation(token:String, location:Int, callback: ListCeritaCallback) {
        val client = ApiConfig.getApiService().GetStoriesByLocation(token, location)
        client.enqueue(object : Callback<Article> {
            override fun onResponse(
                call: Call<Article>,
                response: Response<Article>)
            {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        val value = responseBody?.listStory
//                        if(value != null){
//                            callback.onSuccess(value!!)
//                        }
                    }
                } else {
                    callback.onFailure("get data failed: ${response.code()}")
                    Log.e(AuthRepository.TAG, "onFailure - not success: ${response.isSuccessful}")
                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                callback.onFailure("get data failed: ${t.message}")
                Log.e(AuthRepository.TAG, "onFailure - bad: ${t.message}")
            }
        })
    }

    fun upload(token:String, file: File, description:String, callback: DefaultCallback) {
        var descriptionRequestBody = description
        if (description.isEmpty()) {
            descriptionRequestBody = "Default Description"
        }
        val description = descriptionRequestBody.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )
        val service = ApiConfig.getApiService().uploadImage(token, imageMultipart, description)

        service.enqueue(object : Callback<Article> {
            override fun onResponse(
                call: Call<Article>,
                response: Response<Article>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
//                    if (responseBody != null && !responseBody.error) {
//                        callback.onSuccess()
//                    }
                } else {
                    callback.onFailure(response.message())
                    Log.e(AuthRepository.TAG, "onFailure - not success: ${response.isSuccessful}")
                }
            }
            override fun onFailure(call: Call<Article>, t: Throwable) {
                callback.onFailure("Gagal instance Retrofid: ${t.message}")
                Log.e(AuthRepository.TAG, "onFailure - bad: ${t.message}")
            }
        })
    }

    interface LoginCallback {
        fun onSuccess(result: LoginResult)
        fun onFailure(errorMessage: String)
    }

    interface DefaultCallback {
        fun onSuccess()
        fun onFailure(errorMessage: String)
    }

    interface ListCeritaCallback {
        fun onSuccess(listStory: List<Article>)
        fun onFailure(errorMessage: String)
    }

    companion object{
        private const val TAG = "AuthRepository"

        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(): AuthRepository {
            if (INSTANCE == null) {
                INSTANCE = AuthRepository()
            }
            return INSTANCE as AuthRepository
        }
    }
}