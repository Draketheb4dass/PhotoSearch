package com.jephte.photosearch.data.remote

import com.jephte.photosearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.FLICKR_API_KEY)
            .build()
        requestBuilder.url(url)

        return chain.proceed(requestBuilder.build())
    }
}