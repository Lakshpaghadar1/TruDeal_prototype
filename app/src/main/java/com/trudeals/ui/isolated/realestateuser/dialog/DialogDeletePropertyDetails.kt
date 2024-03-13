package com.trudeals.ui.isolated.realestateuser.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.databinding.DialogDeletePropertyDetailsBinding
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment

class DialogDeletePropertyDetails : BaseDialogFragment<DialogDeletePropertyDetailsBinding>(),
    View.OnClickListener {

    private var onCLickOfPositive: (() -> Unit)? = null

    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): DialogDeletePropertyDetailsBinding {
        return DialogDeletePropertyDetailsBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        clickListeners()
    }

    private fun clickListeners() = with(binding) {
        buttonDelete.setOnClickListener(this@DialogDeletePropertyDetails)
        buttonCancel.setOnClickListener(this@DialogDeletePropertyDetails)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonDelete -> {
                onCLickOfPositive?.invoke()
            }
            buttonCancel -> {
                this@DialogDeletePropertyDetails.dismiss()
            }
        }
    }

    fun setOnPositiveClick(onCLickOfPositive: () -> Unit) {
        this.onCLickOfPositive = onCLickOfPositive
    }
}