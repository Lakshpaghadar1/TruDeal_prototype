package com.trudeals.utils.countrycodepicker

import com.google.gson.annotations.SerializedName

class CountryWithFlag(
    @SerializedName("name")
    val name: String,

    @SerializedName("flag")
    val flag: String,

    @SerializedName("dialCode")
    val dialCode: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("nationality")
    val nationality: String
)