package com.trudeals.utils.dialog

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.trudeals.R

object DialogUtils {
    fun showAlertDialog(
        context: Context,
        @StringRes message: Int,
        @StringRes positiveButtonText: Int = R.string.message_yes,
        @StringRes negativeButtonText: Int = R.string.message_no,
        onPositiveButtonClick: () -> Unit,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(message))
            .setPositiveButton(
                context.getString(positiveButtonText)
            ) { _, _ ->
                onPositiveButtonClick()
            }.setNegativeButton(context.getString(negativeButtonText)) { _, _ ->
                onNegativeButtonClick?.invoke()
            }.setIcon(android.R.drawable.ic_dialog_alert).show()
    }
}