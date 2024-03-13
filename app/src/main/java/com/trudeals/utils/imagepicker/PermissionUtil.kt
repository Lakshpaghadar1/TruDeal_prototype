package com.trudeals.utils.imagepicker

import android.content.pm.PackageManager

object PermissionUtil {
    fun verifyPermissions(grantResults: IntArray): Boolean {

        if (grantResults.isEmpty()) {
            return false
        }

        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}