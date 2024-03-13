package com.trudeals.utils.extension

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.Resources
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.trudeals.R
import com.trudeals.utils.constants.CurrencyConstant
import com.trudeals.utils.constants.RegexConstant
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun View.isVisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.isInVisible(isInVisible: Boolean) {
    if (isInVisible) {
        this.visibility = View.INVISIBLE
    } else {
        this.visibility = View.VISIBLE
    }
}

fun View.isInVisible() = this.visibility == View.INVISIBLE

fun View.setDrawable(drawable: Int?) {
    drawable?.let {
        this.background = ResourcesCompat.getDrawable(
            resources,
            drawable,
            null
        )
    } ?: run {
        this.background = null
    }
}

fun AppCompatImageView.loadImageFromServerAny(path: Any?) {
    Glide.with(context)
        .load(path)
        .into(this)
}

fun AppCompatTextView.setGradientTextColor(startColor: Int, endColor: Int) {
    paint.shader = LinearGradient(
        55f,
        0f,
        0f,
        textSize,
        context.getColor(startColor),
        context.getColor(
            endColor
        ),
        Shader.TileMode.CLAMP
    )
}

@SuppressLint("SimpleDateFormat")
fun View.datePicker(textView: TextView) {
    val calender = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        this.context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            String.format("%02d %02d, %d", month + 1, day, year).also {
                var date = it
                var simpleDateFormat =
                    SimpleDateFormat(
                        context.getString(R.string.date_format_MM_space_dd_coma_yyyy),
                        Locale.ENGLISH
                    )
                val parseDate = simpleDateFormat.parse(date)
                simpleDateFormat =
                    SimpleDateFormat(context.getString(R.string.date_format_MMM_space_dd_coma_yyyy))
                date = simpleDateFormat.format(parseDate!!)
                textView.text = date
            }
        },
        calender.get(Calendar.YEAR),
        calender.get(Calendar.MONTH),
        calender.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.datePicker.minDate = System.currentTimeMillis()
    datePicker.show()
}

fun TextView.applyFilter(
    blockSpecialChar: Boolean = false,
    blockSpecialWithAtChar: Boolean = false,
    blockNumbersChar: Boolean = false,
    applyEmojiFilter: Boolean = false,
    ignoreFirstWhiteSpace: Boolean = true
): InputFilter {
    return InputFilter { source, start, end, dest, dstart, dend ->
        for (i in start until end) {

            if (ignoreFirstWhiteSpace) {
                if (Character.isWhitespace(source[start])) {
                    if (dstart == 0) return@InputFilter ""
                }
            }

            if (blockSpecialChar) {
                if (RegexConstant.BLOCK_CHARSET.contains(source[i]))
                    return@InputFilter (source.toString().replace(source[i].toString(), ""))
            }

            if (blockSpecialWithAtChar) {
                if (RegexConstant.BLOCK_CHARSET_WITH_AT_SIGN.contains(source[i]))
                    return@InputFilter (source.toString().replace(source[i].toString(), ""))
            }

            if (blockNumbersChar) {
                /* if (Character.isDigit(source[i])) {
                     return@InputFilter ""
                 }*/
                if (RegexConstant.BLOCK_NUMBER_SET.contains(source[i]))
                    return@InputFilter (source.toString().replace(source[i].toString(), ""))
            }

            if (applyEmojiFilter) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.NON_SPACING_MARK.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    /* if (i == (end-1)) {
                         return@InputFilter (source.substring(0, i-2))
                     } else {
                         return@InputFilter (source.substring(0, i) + source.substring(i + 2, end))
                     }*/
                    return@InputFilter (source.toString()
                        .replace(source[i].toString() + source[i + 1].toString(), ""))
                }
            }
        }
        null
    }
}

fun AppCompatEditText.filter(
    blockSpecialChar: Boolean = true,
    blockNumbersChar: Boolean = true,
    applyEmojiFilter: Boolean = true,
    ignoreFirstWhiteSpace: Boolean = true
) {
    doAfterTextChanged {
        it?.let { editable ->
            text?.let {
                if (ignoreFirstWhiteSpace && text!!.firstOrNull() != null) {
                    if (Character.isWhitespace(text!!.first())) {
                        setCursorPosition()
                    }
                }

                if (blockSpecialChar && text!!.lastOrNull() != null) {
                    if (RegexConstant.BLOCK_CHARSET.contains(text!!.last())) {
                        setCursorPosition()
                    }
                }

                if (blockNumbersChar && text!!.lastOrNull() != null) {
                    if (RegexConstant.BLOCK_NUMBER_SET.contains(text!!.last())) {
                        setCursorPosition()
                    }
                }

                if (applyEmojiFilter && text!!.lastOrNull() != null) {
                    val type = Character.getType(text!!.last())
                    if (type == Character.SURROGATE.toInt() || type == Character.NON_SPACING_MARK.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                        setCursorPosition()
                    }
                }
            }
        }
    }
}

private fun AppCompatEditText.setCursorPosition() {
    setText(text!!.dropLast(1).toString())
    setSelection(text!!.length)
}


fun AppCompatEditText.setTextConstraint(
    allowAlphanumeric: Boolean = false,
    allowSpace: Boolean = true
) {
    val regex = when {
        allowAlphanumeric && allowSpace -> {
            "[a-zA-Z0-9 ]*"
        }
        allowAlphanumeric && !allowSpace -> {
            "[a-zA-Z0-9]*"
        }
        !allowAlphanumeric && allowSpace -> {
            "[a-zA-Z-.' ]*"
        }
        else -> {
            "[a-zA-Z]*"
        }
    }

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if (!p0.toString().matches(Regex(regex))) {
                text?.let {
                    setText(it.dropLast(1).toString())
                }
                text?.length?.let { setSelection(it) }
            }
        }
    })
}

fun View.setZipCodeFormat(editText: EditText) {
    editText.addTextChangedListener(object : TextWatcher {
        private var current = ""
        private val nonDigits = Regex("[^\\d]")
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (s.toString() != current) {
                if (s.length < 10) {
                    val userInput = s.toString().replace(nonDigits, "")
                    if (userInput.length <= 9) {
                        current = userInput.chunked(5).joinToString("-")
                        s.filters = arrayOf(InputFilter.LengthFilter(10))
                    }
                    s.replace(0, s.length, current, 0, current.length)

                }
            }
        }
    })
}

val TextView.trimmedText get() = text.toString().trim()
val TextView.textWithoutTrim get() = text.toString()

val TextView.empty get() = text.toString().isEmpty()
val TextView.notEmpty get() = text.toString().isNotEmpty()

val TextView.textToStringIsEmpty get() = text.toString().isEmpty()
val TextView.textToStringIsNotEmpty get() = text.toString().isNotEmpty()

fun Number?.numberFormatter(
    prefix: String = "",
    format: String = "#0.00",
    formatWithoutSuffix: String = "#,##0.00"
): String {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val numValue = this?.toLong() ?: 0
    val value = floor(log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        (DecimalFormat(if (prefix == "") format else "$prefix $format").format(
            numValue / 10.0.pow((base * 3).toDouble())
        ) + suffix[base]).also { return it }
    } else {
        DecimalFormat(if (prefix == "") formatWithoutSuffix else "$prefix $formatWithoutSuffix").format(
            this ?: 0.00
        )
    }
}

fun Number?.currencyNumberFormatter(
    format: String = "#0.00",
    formatWithoutSuffix: String = "#,##0.00"
): String {
    return this.numberFormatter(
        prefix = CurrencyConstant.CURRENCY,
        format = format,
        formatWithoutSuffix = formatWithoutSuffix
    )
}

/**
 * This method is used to format number.
 * Example: $ 5.0k
 * */
fun AppCompatTextView.numberFormatter(
    number: Number?,
    prefix: String = "",
    format: String = "#0.00",
    formatWithoutSuffix: String = "#,##0.00"
) {
    text = number.numberFormatter(prefix, format, formatWithoutSuffix)
}

fun AppCompatTextView.numberFormatterWithoutDecimal(number: Number?, prefix: String = "") {
    text = number.numberFormatter(format = "#0", formatWithoutSuffix = "#,##0", prefix = prefix)
}

fun ArrayList<String>.commaSeparatedString(): String {
    return this.joinToString().replace(" ", "")
}

val Int.booleanValue get() = this != 0

/**
 * This method is used to set icon. Do not pass any arguments if you want to remove icon.
 * */
fun TextView.setIcon(
    @DrawableRes startIcon: Int = 0,
    @DrawableRes topIcon: Int = 0,
    @DrawableRes endIcon: Int = 0,
    @DrawableRes bottomIcon: Int = 0
) {
    setCompoundDrawablesWithIntrinsicBounds(
        startIcon,
        topIcon,
        endIcon,
        bottomIcon
    )
}

fun TextView.removeIcon() {
    setIcon()
}

fun TextView.setColorToText(@ColorRes color: Int) {
    setTextColor(
        ResourcesCompat.getColor(
            resources,
            color,
            null
        )
    )
}

/*fun Resources.dpToPx(dp: Int): Int {
    return Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics
        )
    )
}*/

fun Resources.dpToPx(dp: Int): Int {
    return (displayMetrics.density * getDimension(dp)).toInt()
}

inline fun <reified V : ViewBinding> ViewGroup.toBinding(): V {
    return V::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, LayoutInflater.from(context), this, false) as V
}

fun AppCompatEditText.setPasswordConstraint(editTextPassword: AppCompatEditText) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(str: Editable?) {
            val tempString = str.toString()
            if (tempString.isNotEmpty() && tempString.contains(" ")) {
                editTextPassword.setText(editTextPassword.trimmedText)
                editTextPassword.text?.let { editTextPassword.setSelection(it.length) }
            }
        }
    })
}


@SuppressLint("ClickableViewAccessibility")
fun AppCompatEditText.scrollableText() {
    setOnTouchListener { v, event ->
        if (v?.id == this.id) {
            v.parent?.requestDisallowInterceptTouchEvent(true)
            when (event!!.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        false
    }
}

fun setCounterTextWatcher(editText: AppCompatEditText, counterText: AppCompatTextView) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            setCounter(counterText, p0?.length ?: 0)
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}

@SuppressLint("SetTextI18n")
fun setCounter(view: AppCompatTextView, count: Int) {
    view.text = "$count/8000"
}

fun setMobileNumberEditText(bottomLine: AppCompatTextView, editText: AppCompatEditText) {
    bottomLine.isSelected = false
    editText.onFocusChangeListener =
        android.view.View.OnFocusChangeListener { _, isFocus ->
            bottomLine.isSelected = isFocus
        }
}


