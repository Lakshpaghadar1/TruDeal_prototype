package com.trudeals.ui.auth.signin

import androidx.lifecycle.viewModelScope
import com.trudeals.data.pojo.User
import com.trudeals.data.pojo.request.LoginRequest
import com.trudeals.data.repository.AuthRepository
import com.trudeals.ui.base.APILiveData
import com.trudeals.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val userRepository: AuthRepository
) : BaseViewModel() {

    val loginLiveData = APILiveData<User>()
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            loginLiveData.value = userRepository.login(request)
        }
    }


    /**
     * @URL http://35.82.209.194:8500/api/v1/auth/country_list
     * @param
     */
    val countryList = APILiveData<Any>()
    fun countryList() {
        viewModelScope.launch {
            countryList.value = userRepository.countryList()
        }
    }
}