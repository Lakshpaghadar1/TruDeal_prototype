package com.trudeals.ui.isolated.dummydata

import com.trudeals.utils.UserType

data class UserSelection(
    var icon: Int,
    var user: String,
    var userType: UserType,
    var isSelected: Boolean = false
)
