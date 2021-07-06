package com.example.graphql.data

import com.example.graphql.repositories.HeaderRepository
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val headerRepository: HeaderRepository
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept", "*/*")
        builder.addHeader("Accept-Language", "en-US,en;q=0.5")
        builder.addHeader("Accept", "*/*")
        builder.addHeader("X-Token",headerRepository.getTocken())

        return chain.proceed(builder.build())
    }


}
