package com.trudeals.ui.isolated.dummydata

import android.os.Parcelable
import com.trudeals.R
import com.trudeals.ui.home.dummydata.Amenity
import com.trudeals.utils.StatusType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RealEstatePropertyList(
    var title: String = "",
    var image: Int = R.drawable.dummy_image_listing,
    /*var listOfImages: ArrayList<AddPropertyImages> = arrayListOf(),*/
    var video: String = "",
    var listOfBrochures: ArrayList<AddPropertyImages> = arrayListOf(),
    var amenities: Amenity = Amenity(),
    var builtYear: String = "",
    var propertyType: String = "",
    var address: String = "",
    var description: String = "",
    var price: String = "",
    var status: StatusType = StatusType.FOR_SALE,
    var isFeaturedDeal: Boolean = false
) : Parcelable
