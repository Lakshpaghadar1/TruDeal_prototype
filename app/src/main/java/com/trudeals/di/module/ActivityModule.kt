package com.trudeals.di.module

import com.trudeals.di.PerActivity
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.manager.FragmentHandler
import com.trudeals.ui.manager.Navigator
import com.trudeals.utils.imagepicker.MediaSelectHelper

import javax.inject.Named

import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

/**
 * Created by hlink21 on 9/5/16.
 */
@Module
class ActivityModule {
    @Provides
    @PerActivity
    internal fun navigator(activity: BaseActivity): Navigator {
        return activity
    }

    @Provides
    @PerActivity
    internal fun fragmentManager(baseActivity: BaseActivity): androidx.fragment.app.FragmentManager {
        return baseActivity.supportFragmentManager
    }

    @Provides
    @PerActivity
    @Named("placeholder")
    internal fun placeHolder(baseActivity: BaseActivity): Int {
        return baseActivity.findFragmentPlaceHolder()
    }

    @Provides
    @PerActivity
    internal fun fragmentHandler(fragmentManager: com.trudeals.ui.manager.FragmentManager): FragmentHandler {
        return fragmentManager
    }

    @Provides
    @PerActivity
    internal fun provideImagePicker(baseActivity: BaseActivity): MediaSelectHelper {
        return MediaSelectHelper(WeakReference(baseActivity))
    }
}
