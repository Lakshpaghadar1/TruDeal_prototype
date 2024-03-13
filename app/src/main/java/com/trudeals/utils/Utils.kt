package com.trudeals.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.trudeals.R
import com.trudeals.utils.extension.isInVisible
import com.trudeals.utils.extension.isVisible
import java.text.SimpleDateFormat
import java.util.*

fun showView(vararg viewToShow: View) {
    viewToShow.forEach {
        it.isVisible(true)
    }
}

fun hideView(vararg viewToHide: View) {
    viewToHide.forEach {
        it.isVisible(false)
    }
}

fun invisibleView(vararg viewToMakeInvisible: View) {
    viewToMakeInvisible.forEach {
        it.isInVisible(true)
    }
}

fun isEmailValid(email: CharSequence): Boolean {
    return Patterns.EMAIL_ADDRESS.pattern().toRegex().matches(email)
}

fun showHidePassword(editText: EditText, togglePasswordView: ImageView, view: View?) {
    if (view == togglePasswordView) {
        if (editText.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
            (view as AppCompatImageView).setImageResource(R.drawable.ic_password_show)

            //Show Password
            editText.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            editText.setSelection(editText.text?.length ?: 0)

        } else {
            (view as AppCompatImageView).setImageResource(R.drawable.ic_password_hide)
            //Hide Password
            editText.transformationMethod =
                PasswordTransformationMethod.getInstance()
            editText.setSelection(editText.text?.length ?: 0)
        }
    }
}

fun getCurrentDate(): String {
    val currentDate = Date()
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return format.format(currentDate)
}

fun getCurrentTime(): String {
    val currentTime = Date()
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(currentTime)
}

fun formatDate(calendar: Calendar): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

@SuppressLint("ConstantLocale")
val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
fun getTimeString(hour: Int, minute: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }
    return timeFormat.format(calendar.time)
}

fun formatTime(calendar: Calendar): String {
    return timeFormat.format(calendar.time)
}


/*fun generateImageFromPdf(pdfUri: Uri?, createImageFile: File) {
    val pageNumber = 0
    val pdfiumCore = PdfiumCore(requireContext())
    try {
        val fd: ParcelFileDescriptor =
            pdfUri?.let { activityactivity().contentResolver.openFileDescriptor(it, "r") }!!
        val pdfDocument: PdfDocument? = pdfiumCore.newDocument(fd)
        pdfiumCore.openPage(pdfDocument, pageNumber)
        val width: Int = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber)
        val height: Int = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber)
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height)
        saveImage(bmp, createImageFile)
        pdfiumCore.closeDocument(pdfDocument)
    } catch (_: Exception) {

    }
}

private fun saveImage(bmp: Bitmap, createImageFile: File) {
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(createImageFile)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
    } catch (_: Exception) {
    } finally {
        try {
            out?.close()
        } catch (_: Exception) {
        }
    }
}*/

