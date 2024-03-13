package com.trudeals.utils.countrycodepicker

import com.google.gson.annotations.SerializedName

class CountryCode(
    @SerializedName("country")
    var countries: List<CountryWithFlag>
)