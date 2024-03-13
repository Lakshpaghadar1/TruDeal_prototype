package com.trudeals.ui.isolated.customeruser.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.databinding.DialogAppointmentReqSuccessBinding
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment

class DialogAppointmentReqSuccess : BaseDialogFragment<DialogAppointmentReqSuccessBinding>(),
    View.OnClickListener {
    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): DialogAppointmentReqSuccessBinding {
        return DialogAppointmentReqSuccessBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        clickListeners()
    }

    private fun clickListeners() = with(binding) {
        buttonContinue.setOnClickListener(this@DialogAppointmentReqSuccess)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonContinue -> {
                this@DialogAppointmentReqSuccess.dismiss()
            }
        }
    }
}