package com.trudeals.ui.home.dummydata

import android.os.Parcelable
import com.trudeals.utils.StatusType
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerHomeListItem(
    var image: Int? = 0,
    var status: StatusType? = StatusType.NONE,
    var discount: String? = " ",
    var title: String? = " ",
    var location: String? = " ",
    var description: String? = null,
    var numberOfAmenities: Amenity? = null,
    var amount: String? = " ",
    var isLiked: Boolean = false
) : Parcelable
