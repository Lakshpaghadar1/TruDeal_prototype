package com.trudeals.ui.home.dummydata

import com.trudeals.utils.TabType

data class RealEstateTabREU(
    val title: String,
    var currentTabType: TabType,
    var isSelected: Boolean = false
)
