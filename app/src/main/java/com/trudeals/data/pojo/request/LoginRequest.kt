package com.trudeals.data.pojo.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("device_id")
    var deviceId: String = "",

    @SerializedName("device_type")
    var deviceType: String = "",

    @SerializedName("social_id")
    var socialId: String = "",

    @SerializedName("country_code")
    var countryCode: String = "",

    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("latitude")
    var latitude: String = "",

    @SerializedName("longitude")
    var longitude: String = "",

    @SerializedName("language")
    var language: String = "",

    @SerializedName("app_version")
    var appVersion: String = "",

    @SerializedName("uuid")
    var uuid: String = "",

    @SerializedName("os_version")
    var osVersion: String = "",

    @SerializedName("version_name")
    var versionName: String = "",

    @SerializedName("device_name")
    var deviceName: String = "",

    @SerializedName("model_name")
    var modelName: String = "",

    @SerializedName("ip")
    var ip: String = "",
)