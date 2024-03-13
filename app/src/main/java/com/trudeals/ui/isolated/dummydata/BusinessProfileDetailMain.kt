package com.trudeals.ui.isolated.dummydata

import android.os.Parcelable
import com.trudeals.utils.imagepicker.OutPutFileAny
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessProfileDetailMain(
    var businessProfileDetail: BusinessProfileDetail? = null,
    var businessMediaDetail: BusinessMediaDetail? = null,
    var businessSocialMediaDetail: BusinessSocialMediaDetail? = null,
    var listOfAvailability: ArrayList<SelectBusinessTimeSlots>? = arrayListOf()
) : Parcelable


@Parcelize
data class BusinessProfileDetail(
    var name: String? = "",
    var description: String? = "",
    var category: String? = "",
    var address: String? = "",
    var city: String? = "",
    var state: String? = "",
    var zipCode: String? = "",
    var landmarks: String? = "",
) : Parcelable

@Parcelize
data class BusinessMediaDetail(
    var businessLogo: String? = "",
    var listOfBrochures: ArrayList<OutPutFileAny>? = arrayListOf(),
    var listOfMenu: ArrayList<AddPropertyImages>? = arrayListOf(),
    var listOfBusinessSlider: ArrayList<AddPropertyImages>? = arrayListOf()
) : Parcelable

@Parcelize
data class BusinessSocialMediaDetail(
    var emailAddress: String? = "",
    var countryCode: String? = "",
    var phoneNumber: String? = "",
    var facebookLink: String? = "",
    var twitterLink: String? = "",
    var googleProfile: String = "",
    var instagramLink: String? = "",
    var yelpLink: String? = "",
    var metaKeyword: String? = ""
) : Parcelable
