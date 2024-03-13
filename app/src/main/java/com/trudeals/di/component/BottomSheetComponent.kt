package com.trudeals.di.component

import com.trudeals.di.PerFragment
import com.trudeals.di.module.BottomSheetModule
import com.trudeals.ui.isolated.businessuser.fragment.ContactOptionsBottomsheet
import com.trudeals.ui.isolated.customeruser.bottomsheet.*
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [(BottomSheetModule::class)])
interface BottomSheetComponent {
    fun inject(bottomSheetComponent: BottomSheetComponent)
    fun inject(bottomSheetComponent: SelectDateAndTimeBottomsheet)
    fun inject(addYourDetailsBottomsheet: AddYourDetailsBottomsheet)
    fun inject(advanceFilterBottomsheet: AdvanceFilterBottomsheet)
    fun inject(sortByBottomsheet: SortByBottomsheet)
    fun inject(chatMoreOptionBottomsheet: ChatMoreOptionBottomsheet)
    fun inject(contactOptionsBottomsheet: ContactOptionsBottomsheet)

}