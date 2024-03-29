package com.vivek.nytimes.data.remote

import com.vivek.nytimes.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.HttpUrl



class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api-key", BuildConfig.API_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}