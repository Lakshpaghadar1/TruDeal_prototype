package com.trudeals.ui.isolated.dummydata

import com.trudeals.utils.RequestCategoryType
import com.trudeals.utils.StatusType

data class RequestList(
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
    var requestType: RequestCategoryType = RequestCategoryType.REQUESTED,
    var isDealClosed: Boolean = false,
    var isScheduled: Boolean = false,
)