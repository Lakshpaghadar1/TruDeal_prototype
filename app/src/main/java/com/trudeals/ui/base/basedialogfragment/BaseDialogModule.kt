package com.trudeals.ui.base.basedialogfragment

import com.trudeals.di.PerFragment
import dagger.Module
import dagger.Provides

@Module
class BaseDialogModule(private val baseDialog: BaseDialogFragment<*>) {

    @Provides
    @PerFragment
    internal fun provideBaseDialog(): BaseDialogFragment<*> {
        return baseDialog
    }

}