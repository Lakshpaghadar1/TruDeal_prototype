package com.trudeals.utils.otpverificationtextwatcher

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.trudeals.R
import com.trudeals.ui.base.BaseActivity

class GenericTextWatcher internal constructor(private val currentView: View,
                                              private val nextView: View?,
                                              private val activity: BaseActivity
) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.textInputEditTextOtp1 -> if (text.length == 1) {
                nextView!!.requestFocus()
                val edt = currentView.findViewById<AppCompatEditText>(R.id.textInputEditTextOtp1)
            }
            R.id.textInputEditTextOtp2 -> if (text.length == 1) {
                nextView!!.requestFocus()
                val edt = currentView.findViewById<AppCompatEditText>(R.id.textInputEditTextOtp2)
            }
            R.id.textInputEditTextOtp3 -> if (text.length == 1) {
                nextView!!.requestFocus()
                val edt = currentView.findViewById<AppCompatEditText>(R.id.textInputEditTextOtp3)
            }
            R.id.textInputEditTextOtp4 -> if (text.length == 1) {
                activity.hideKeyboard()
                val edt = currentView.findViewById<AppCompatEditText>(R.id.textInputEditTextOtp4)
            }
            //You can use EditText6 same as above to hide the keyboard
        }
    }

    override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
    ) {
    }

    override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
    ) {
    }

}