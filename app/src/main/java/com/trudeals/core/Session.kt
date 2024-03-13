package com.trudeals.core


import com.trudeals.data.pojo.User
import com.trudeals.utils.UserType


/**
 * Created by hlink21 on 11/7/16.
 */
interface Session {

    var apiKey: String

    var userSession: String

    var userId: String

    val deviceId: String

    var user: User?

    val language: String

    var isInitial: Boolean

    var isLoggedIn: Boolean

    var isCustomerUser: Boolean

    var isRealEstateUser: Boolean

    var isBusinessOwnerUser: Boolean

    var isGuestUser: Boolean

    var userType: UserType

    var isREUSubscribed: Boolean

    var isBUSubscribed: Boolean

    var isLoggedOut: Boolean

    fun clearSession()

    companion object {
        const val API_KEY = "api_key"
        const val USER_SESSION = "token"
        const val USER_ID = "USER_ID"
        const val DEVICE_TYPE = "A"
        const val LANGUAGE = "accept-language"
        const val IS_INITIAL = "isInitial"
        const val IS_LOGGED_IN = "isLoggedIn"
        const val IS_CUSTOMER_USER = "isCustomerUser"
        const val IS_REAL_ESTATE_USER = "isRealEstateUser"
        const val IS_BUSINESS_OWNER_USER = "isBusinessOwnerUser"
        const val IS_GUEST_USER = "isGuestUser"
        const val IS_REU_SUBSCRIBED = "isREUSubscribed"
        const val IS_BU_SUBSCRIBED = "isBUSubscribed"
        const val IS_LOGGED_OUT = "isLoggedOut"
        const val USER_TYPE = "userType"
    }
}
