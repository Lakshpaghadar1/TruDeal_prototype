package com.trudeals.ui.isolated.dummydata

import com.trudeals.utils.PropertyListTag

data class PropertyListTab(
    var tab: String,
    var tag: PropertyListTag,
    var isSelected: Boolean = false
)
