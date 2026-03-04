package com.xxx.base_mvvm.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TokenProvider — interface cho core:data set token mà không cần biết OkHttp
 */
interface TokenProvider {
    fun setToken(token: String)
    fun clearToken()
}

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor, TokenProvider {
    private var token: String = ""

    override fun setToken(token: String) { this.token = token }
    override fun clearToken() { this.token = "" }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (token.isNotBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else chain.request()
        return chain.proceed(request)
    }
}
