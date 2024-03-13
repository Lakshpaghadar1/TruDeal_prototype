package com.trudeals.data


import okhttp3.HttpUrl

/**
 * Created by hlink21 on 11/5/17.
 */

object URLFactory {

    // server details
    private const val IS_LOCAL = true
    private const val SCHEME = "http"
    private val HOST = if (IS_LOCAL) "35.82.209.194" else "skkyn.com"
    private val API_PATH = if (IS_LOCAL) "api/v1/" else "websitedata/api/v2/"
    private val PORT = if (IS_LOCAL) 8500 else 8082

    fun provideHttpUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .port(PORT)
            .addPathSegments(API_PATH)
            .build()
    }

    // API Methods
    object Method {
        private const val AUTH = "auth/"

        const val COUNTRY_LIST = AUTH + "country_list"


        const val LOGIN = "user/login"
    }

    object ResponseCode {
        const val INVALID_OR_FAILURE = 0
        const val SUCCESS = 1
        const val NO_DATA_FOUND = 2
    }
}
