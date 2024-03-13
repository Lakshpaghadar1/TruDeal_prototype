package com.trudeals.ui.home.dummydata

import com.trudeals.utils.RealEstateOrBusinessChipType

data class RealEstateOrBusinessHomeChip(
    val icon: Int,
    val title: String,
    val color: Int,
    var isSelected: Boolean = false,
    var badgeCount: Int? = null,
    var chip: RealEstateOrBusinessChipType
)