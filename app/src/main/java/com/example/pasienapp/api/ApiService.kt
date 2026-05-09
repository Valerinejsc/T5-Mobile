package com.example.pasienapp.api

import com.example.pasienapp.model.LoginRequest
import com.example.pasienapp.model.LoginResponse
import com.example.pasienapp.model.PasienResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("api/pasien")
    suspend fun getPasien(
        @Header("Authorization") token: String
    ): Response<PasienResponse>
}