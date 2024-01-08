package com.example.data.di

import com.example.data.network.AppConfiguration
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(
    private val appConfiguration: AppConfiguration
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer " + appConfiguration.getAccessToken())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
