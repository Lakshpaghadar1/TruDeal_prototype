package com.trudeals.di.module

import com.trudeals.di.PerFragment
import com.trudeals.ui.base.BaseBottomSheet
import dagger.Module
import dagger.Provides

@Module
class BottomSheetModule(private val baseBottomSheetDialog: BaseBottomSheet<*>) {

    @Provides
    @PerFragment
    internal fun provideBaseBottomSheetDialog(): BaseBottomSheet<*> {
        return baseBottomSheetDialog
    }
}