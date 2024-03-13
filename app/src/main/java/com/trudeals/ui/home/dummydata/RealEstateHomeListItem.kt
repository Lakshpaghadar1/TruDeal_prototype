package com.trudeals.ui.home.dummydata

import android.os.Parcelable
import com.trudeals.utils.StatusType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RealEstateHomeListItem(
    var image: Int? = 0,
    var status: StatusType? = StatusType.NONE,
    var title: String? = " ",
    var location: String? = " ",
    var numberOfAmenities: Amenity? = null,
    var amount: String? = " ",
    var isLiked: Boolean = false
) : Parcelable
