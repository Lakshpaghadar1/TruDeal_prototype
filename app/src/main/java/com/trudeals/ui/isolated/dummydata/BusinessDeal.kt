package com.trudeals.ui.isolated.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessDeal(
    var businessName: String? = "",
    var dealName: String? = "",
    var discount: String? = "",
    var startSaleDate: String? = "",
    var startSaleTime: String? = "",
    var endSaleDate: String? = "",
    var endSaleTime: String? = "",
    var views: String? ="",
    var redeems: String? = "",
    var shares: String? = ""
) : Parcelable