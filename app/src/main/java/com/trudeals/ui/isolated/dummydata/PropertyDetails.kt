package com.trudeals.ui.isolated.dummydata

import com.trudeals.utils.PropertyDetailsTag

data class PropertyDetails(
    var title: String,
    var tag: PropertyDetailsTag,
    var featuresList: ArrayList<String>? = null,
    var areaAndSpecifications: String? = null,
    var imageForVideo: Int? = null,
    var imagesList: ArrayList<Int>? = null
)
