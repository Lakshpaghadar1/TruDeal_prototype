package com.trudeals.ui.home.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Amenity(
    var numberOfBeds: String? = null,
    var numberOfBath: String? = null,
    var numberOfGarage: String? = null,
    var numberOfSqFt: String? = null,
) : Parcelable