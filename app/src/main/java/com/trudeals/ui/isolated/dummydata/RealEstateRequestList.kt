package com.trudeals.ui.isolated.dummydata

import com.trudeals.utils.ButtonStatus
import com.trudeals.utils.StatusType

data class RealEstateRequestList(
    var userName: String,
    var userEmail: String,
    var userPhoneNumber: String,
    var date: String,
    var time: String,
    var notes: String? = null,
    var propertyImage: Int,
    var propertyType: String,
    var propertyLocation: String,
    var status: StatusType,
   var buttonStatus: ButtonStatus? = null
)