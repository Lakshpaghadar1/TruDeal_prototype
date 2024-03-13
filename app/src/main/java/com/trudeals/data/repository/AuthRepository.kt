package com.trudeals.data.repository

import com.trudeals.data.pojo.DataWrapper
import com.trudeals.data.pojo.User
import com.trudeals.data.pojo.request.LoginRequest

interface AuthRepository {
    suspend fun countryList(): DataWrapper<Any>
    suspend fun login(request: LoginRequest): DataWrapper<User>
}