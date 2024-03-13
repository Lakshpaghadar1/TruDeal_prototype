package com.trudeals.ui.isolated.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectBusinessTimeSlots(
    var isChecked: Boolean = true,
    var weekDay: String = "",
    var isClosed: Boolean = false,
    var listOfTimeSlots: ArrayList<SelectTimeSlots> = arrayListOf()
) : Parcelable