package com.trudeals.data.datasource

import com.trudeals.data.pojo.DataWrapper
import com.trudeals.data.pojo.User
import com.trudeals.data.pojo.request.LoginRequest
import com.trudeals.data.repository.AuthRepository
import com.trudeals.data.service.AuthenticationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLiveDataSource @Inject constructor(private val authenticationService: AuthenticationService) : BaseDataSource(), AuthRepository {

    override suspend fun countryList(): DataWrapper<Any> {
        return  execute { authenticationService.countryList() }
    }

    override suspend fun login(request: LoginRequest): DataWrapper<User> {
        return execute { authenticationService.login(request) }
    }
}
