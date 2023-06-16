package com.alvintio.calcuwaste.api

import com.alvintio.calcuwaste.api.response.LoginResponse
import com.alvintio.calcuwaste.api.response.NewsResponse
import com.alvintio.calcuwaste.api.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    @FormUrlEncoded
    fun requestLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("register")
    @FormUrlEncoded
    fun requestRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("news")
    fun getAllNews(
        @Header("Authorization") token: String
    ): Call<NewsResponse>
}