package com.trudeals.ui.isolated.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageLibrary(
    var profileImage: Int,
    var profileName: String,
    var isChecked: Boolean = false
) : Parcelable