package com.trudeals.ui.auth.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneNumberData(
    var countryCode: String? = "",
    var phoneNumber: String? = ""
) : Parcelable
