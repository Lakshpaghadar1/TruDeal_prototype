package com.trudeals.ui.home.dummydata

import com.trudeals.utils.SubCategoryType
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.TabType

data class ChipBothLinked(
    var chip: String,
    var isSelected: Boolean = false,
    var currentTabType: TabType = TabType.NONE,
    var subCategoryType: SubCategoryType = SubCategoryType.DEALS_AND_REAL_ESTATE,
    val mainCategoryType: MainCategoryType = MainCategoryType.BOTH
)
