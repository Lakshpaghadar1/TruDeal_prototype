package com.trudeals.ui.isolated.customeruser.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.databinding.DialogRedeemNowBinding
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment

class DialogRedeemNow : BaseDialogFragment<DialogRedeemNowBinding>(),
    View.OnClickListener {

    private var onCLickOfPositive: (() -> Unit)? = null

    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): DialogRedeemNowBinding {
        return DialogRedeemNowBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        clickListeners()
    }

    private fun clickListeners() = with(binding) {
        buttonPositive.setOnClickListener(this@DialogRedeemNow)
        buttonNegative.setOnClickListener(this@DialogRedeemNow)
        imageViewCancel.setOnClickListener(this@DialogRedeemNow)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonPositive -> {
                onCLickOfPositive?.invoke()
            }
            buttonNegative -> {
                this@DialogRedeemNow.dismiss()
            }
            imageViewCancel -> {
                this@DialogRedeemNow.dismiss()
            }
        }
    }

    fun setOnPositiveClick(onCLickOfPositive: () -> Unit) {
        this.onCLickOfPositive = onCLickOfPositive
    }
}