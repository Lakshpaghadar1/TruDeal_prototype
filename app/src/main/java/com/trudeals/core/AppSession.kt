package com.trudeals.core

import android.content.Context
import android.provider.Settings

import com.google.gson.Gson
import com.trudeals.data.pojo.User
import com.trudeals.utils.UserType


import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by hlink21 on 11/7/16.
 */
@Singleton
class AppSession @Inject
constructor(
    private val appPreferences: AppPreferences,
    private val context: Context,
    @param:Named("api-key") override var apiKey: String
) : Session {

    private val gson: Gson = Gson()

    override var user: User? = null
        get() {
            if (field == null) {
                val userJSON = appPreferences.getString(USER_JSON)
                field = gson.fromJson(userJSON, User::class.java)
            }
            return field
        }
        set(value) {
            field = value
            val userJson = gson.toJson(value)
            if (userJson != null)
                appPreferences.putString(USER_JSON, userJson)
        }


    override var userSession: String
        get() = appPreferences.getString(Session.USER_SESSION)
        set(userSession) = appPreferences.putString(Session.USER_SESSION, userSession)


    override var userId: String
        get() = appPreferences.getString(Session.USER_ID)
        set(userId) = appPreferences.putString(Session.USER_ID, userId)


    override/* open below comment after Firebase integration *///token = FirebaseInstanceId.getInstance().getToken();
    val deviceId: String
        get() {
            var token = ""
            if (token.isEmpty())
                token =
                    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

            return token
        }

    override//  return StringUtils.equalsIgnoreCase(appPreferences.getString(Common.LANGUAGE), "ar") ? LANGUAGE_ARABIC : LANGUAGE_ENGLISH;
    val language: String
        get() = "en"

    override var isInitial: Boolean
        get() = appPreferences.getBoolean(Session.IS_INITIAL, default = true)
        set(value) = appPreferences.putBoolean(Session.IS_INITIAL, value)

    override var isLoggedIn: Boolean
        get() = appPreferences.getBoolean(Session.IS_LOGGED_IN)
        set(value) = appPreferences.putBoolean(Session.IS_LOGGED_IN, value)

    override var isCustomerUser: Boolean
        get() = appPreferences.getBoolean(Session.IS_CUSTOMER_USER)
        set(value) = appPreferences.putBoolean(Session.IS_CUSTOMER_USER, value)

    override var isRealEstateUser: Boolean
        get() = appPreferences.getBoolean(Session.IS_REAL_ESTATE_USER)
        set(value) = appPreferences.putBoolean(Session.IS_REAL_ESTATE_USER, value)

    override var isBusinessOwnerUser: Boolean
        get() = appPreferences.getBoolean(Session.IS_BUSINESS_OWNER_USER)
        set(value) = appPreferences.putBoolean(Session.IS_BUSINESS_OWNER_USER, value)

    override var isGuestUser: Boolean
        get() = appPreferences.getBoolean(Session.IS_GUEST_USER, default = false)
        set(value) = appPreferences.putBoolean(Session.IS_GUEST_USER, value)

    override var userType: UserType
        get() = appPreferences.getParcelable(Session.USER_TYPE, UserType::class.java)
        set(value) = appPreferences.putParcelable(Session.USER_TYPE, value)

    override var isREUSubscribed: Boolean
        get() = appPreferences.getBoolean(Session.IS_REU_SUBSCRIBED)
        set(value) = appPreferences.putBoolean(Session.IS_REU_SUBSCRIBED, value)

    override var isBUSubscribed: Boolean
        get() = appPreferences.getBoolean(Session.IS_BU_SUBSCRIBED)
        set(value) = appPreferences.putBoolean(Session.IS_BU_SUBSCRIBED, value)

    override var isLoggedOut: Boolean
        get() = appPreferences.getBoolean(Session.IS_LOGGED_OUT)
        set(isLoggedOut) = appPreferences.putBoolean(Session.IS_LOGGED_OUT, isLoggedOut)

    override fun clearSession() {
        appPreferences.clearAll()
    }

    companion object {
        const val USER_JSON = "user_json"
    }


}
