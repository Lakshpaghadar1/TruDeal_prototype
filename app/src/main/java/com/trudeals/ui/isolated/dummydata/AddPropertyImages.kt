package com.trudeals.ui.isolated.dummydata

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddPropertyImages(var imageUri: Uri? = null): Parcelable