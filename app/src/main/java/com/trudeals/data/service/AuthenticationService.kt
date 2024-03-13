package com.trudeals.data.service

import com.trudeals.data.URLFactory
import com.trudeals.data.pojo.ResponseBody
import com.trudeals.data.pojo.User
import com.trudeals.data.pojo.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST(URLFactory.Method.COUNTRY_LIST)
    suspend fun countryList(): ResponseBody<Any>

    @POST(URLFactory.Method.LOGIN)
    suspend fun login(@Body request: LoginRequest): ResponseBody<User>
}