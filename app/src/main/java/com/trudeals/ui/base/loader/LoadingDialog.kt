package com.trudeals.ui.base.loader

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.trudeals.databinding.LoadingDialogBinding

class LoadingDialog(
    private val setOnCancelListener: () -> Unit
) : DialogFragment() {

    private lateinit var binding: LoadingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)

        binding = LoadingDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        dialog?.apply {
            // requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setOnCancelListener {
            setOnCancelListener()
        }
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingDialogBinding.inflate(LayoutInflater.from(context))
    }
}

/*

class LoadingDialog(context: Context, private val setOnCancelListener: () -> Unit) :
    Dialog(context) {

    private lateinit var binding: LoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingDialogBinding.inflate(LayoutInflater.from(context))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        setOnCancelListener {
            setOnCancelListener()
        }
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        //window?.setDimAmount(0f)
    }
}*/
