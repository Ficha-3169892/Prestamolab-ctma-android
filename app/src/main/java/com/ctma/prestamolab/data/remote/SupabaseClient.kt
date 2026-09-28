package com.ctma.prestamolab.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient {

    private const val SUPABASE_URL = "https://tzwwpyfgxqpubsrixmbi.supabase.co/rest/v1/"
    private const val SUPABASE_API_KEY = "sb_publishable_Ce44BNo9XTi2hMT9V80BGQ_7oi_lWXk"

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("apikey", SUPABASE_API_KEY)
            .header("Authorization", "Bearer $SUPABASE_API_KEY")
            .header("Content-Type", "application/json")
            .header("Prefer", "return=representation")
            .build()
        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val apiService: PrestamoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(SUPABASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrestamoApiService::class.java)
    }
}
