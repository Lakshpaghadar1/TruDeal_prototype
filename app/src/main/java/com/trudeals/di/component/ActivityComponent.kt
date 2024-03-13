package com.trudeals.di.component

import com.trudeals.di.PerActivity
import com.trudeals.di.module.ActivityModule
import com.trudeals.di.module.BottomSheetModule
import com.trudeals.di.module.FragmentModule
import com.trudeals.ui.auth.AuthActivity
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogModule
import com.trudeals.ui.common.isolated.FullScreenActivity
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.main.MainActivity
import com.trudeals.ui.manager.Navigator
import com.trudeals.ui.splash.SplashActivity
import com.trudeals.ui.tutorial.TutorialActivity
import dagger.BindsInstance
import dagger.Component

/**
 * Created by hlink21 on 9/5/16.
 */
@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun activity(): BaseActivity

    fun navigator(): Navigator

    operator fun plus(fragmentModule: FragmentModule): FragmentComponent
    operator fun plus(baseDialogModule: BaseDialogModule): BaseDialogComponent
    operator fun plus(baseBottomSheetModule: BottomSheetModule): BottomSheetComponent

    fun inject(mainActivity: SplashActivity)
    fun inject(isolatedFullActivity: IsolatedFullActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(authActivity: AuthActivity)
    fun inject(fullScreenActivity: FullScreenActivity)
    fun inject(tutorialActivity: TutorialActivity)
    fun inject(homeActivity: HomeActivityCU)
    fun inject(homeActivityREU: HomeActivityREU)
    fun inject(homeActivityBU: HomeActivityBU)

    @Component.Builder
    interface Builder {
        fun bindApplicationComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun bindActivity(baseActivity: BaseActivity): Builder

        fun build(): ActivityComponent
    }
}
