package com.trudeals.ui.splash

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.trudeals.R
import com.trudeals.databinding.SplashActivityBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.auth.AuthActivity
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.tutorial.TutorialActivity

/**
 * Created by Hlink 44.
 */
class SplashActivity : BaseActivity() {

    //Data store on after user login
    lateinit var splashActivityBinding: SplashActivityBinding
    override fun findFragmentPlaceHolder(): Int {
        return 0
    }

    override fun createViewBinding(): View {
        splashActivityBinding = SplashActivityBinding.inflate(layoutInflater)
        return splashActivityBinding.root
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this).asGif().load(R.drawable.ic_loading_indicator_splash_screen).listener(
            object : RequestListener<GifDrawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1)
                    resource?.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)
                            Log.e(
                                "TAG",
                                "onAnimationEnd: CU:${session.isCustomerUser}, " +
                                        "REU: ${session.isRealEstateUser}, " +
                                        "BU: ${session.isBusinessOwnerUser}, " +
                                        "login: ${session.isLoggedIn}, " +
                                        "REU SUBS: ${session.isREUSubscribed}, " +
                                        "BU SUBS: ${session.isBUSubscribed} " +
                                        "guest user ${session.isGuestUser}"
                            )
                            when {
                                session.isInitial -> {
                                    loadActivity(TutorialActivity::class.java).byFinishingCurrent()
                                        .start()
                                }
                                session.isGuestUser || (session.isCustomerUser && session.isLoggedIn) -> {
                                    loadActivity(HomeActivityCU::class.java).byFinishingCurrent()
                                        .start()
                                }
                                session.isRealEstateUser && session.isLoggedIn && session.isREUSubscribed-> {
                                    loadActivity(HomeActivityREU::class.java).byFinishingCurrent()
                                        .start()
                                }
                                session.isBusinessOwnerUser && session.isLoggedIn && session.isBUSubscribed-> {
                                    loadActivity(HomeActivityBU::class.java).byFinishingCurrent()
                                        .start()
                                }
                                session.isLoggedOut -> {
                                    loadActivity(AuthActivity::class.java).byFinishingCurrent()
                                        .start()
                                }
                                else -> {
                                    loadActivity(AuthActivity::class.java).byFinishingCurrent()
                                        .start()
                                }
                            }
                        }
                    })
                    return false
                }
            }
        ).into(splashActivityBinding.imageViewLoading)
    }
}