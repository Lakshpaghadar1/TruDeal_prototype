package com.trudeals.ui.isolated.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectTimeSlots(
    var startTime: String? = "",
    var endTime: String? = ""
) : Parcelable
