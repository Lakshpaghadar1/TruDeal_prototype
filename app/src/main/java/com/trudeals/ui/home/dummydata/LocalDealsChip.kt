package com.trudeals.ui.home.dummydata

import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.TabType

data class LocalDealsChip(
    val icon: Int,
    val title: String,
    val color: Int,
    var isSelected: Boolean = false,
    var currentTabType: TabType,
    val mainCategoryType: MainCategoryType = MainCategoryType.LOCAL_DEALS
)