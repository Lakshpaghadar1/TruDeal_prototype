package com.trudeals.ui.isolated.dummydata

data class BusinessHours(
    var day: String,
    var listOfTimeSlots: ArrayList<String>? = arrayListOf(),
    var isClosed: Boolean = false,
    var isHrsDiffer: Boolean = false,
    var reasonForDifferHrs: String? = null
)
