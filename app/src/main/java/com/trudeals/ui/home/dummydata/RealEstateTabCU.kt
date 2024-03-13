package com.trudeals.ui.home.dummydata

import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.SubCategoryType
import com.trudeals.utils.TabType

data class RealEstateTabCU(
    val title: String,
    val currentTabType: TabType,
    val subCategoryType: SubCategoryType = SubCategoryType.NONE,
    val mainCategoryType: MainCategoryType = MainCategoryType.REAL_ESTATE,
    var isSelected: Boolean = false
)
