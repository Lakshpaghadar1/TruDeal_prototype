package com.trudeals.ui.base.basedialogfragment
import com.trudeals.di.PerFragment
import com.trudeals.ui.isolated.customeruser.dialog.DialogAppointmentReqSuccess
import com.trudeals.ui.isolated.customeruser.dialog.DialogRedeemNow
import com.trudeals.ui.isolated.customeruser.dialog.DialogSelectUser
import com.trudeals.ui.isolated.customeruser.dialog.TimePickerDialog
import com.trudeals.ui.isolated.realestateuser.dialog.DialogDeletePropertyDetails
import com.trudeals.ui.isolated.realestateuser.dialog.ReasonToCancelDialog
import com.trudeals.ui.isolated.realestateuser.dialog.YearPickerDialog
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [(BaseDialogModule::class)])
interface BaseDialogComponent {

    fun baseDialog(): BaseDialogFragment<*>
    fun inject(dialogAppointmentReqSuccess: DialogAppointmentReqSuccess)
    fun inject(dialogAppointmentReqSuccess: DialogRedeemNow)
    fun inject(dialogSelectUser: DialogSelectUser)
    fun inject(timePickerDialog: TimePickerDialog)
    fun inject(yearPickerDialog: YearPickerDialog)
    fun inject(dialogDeletePropertyDetails: DialogDeletePropertyDetails)
    fun inject(reasonToCancelDialog: ReasonToCancelDialog)


}