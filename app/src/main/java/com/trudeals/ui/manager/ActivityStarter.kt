package com.trudeals.ui.manager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.trudeals.di.PerActivity
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Created by hlink21 on 11/5/16.
 */
@PerActivity
class ActivityStarter @Inject
internal constructor(private val context: BaseActivity) {
    private var intent: Intent? = null
    private var activity: Class<out Activity>? = null
    private var shouldAnimate = true
    var startForResult: ActivityResultLauncher<Intent>? = null

    fun make(activityClass: Class<out BaseActivity>): ActivityBuilder {
        activity = activityClass
        intent = Intent(context, activityClass)
        startForResult = null

        return Builder()
    }

    private inner class Builder : ActivityBuilder {
        private var bundle: Bundle? = null
        private var activityOptionsBundle: Bundle? = null
        private var isToFinishCurrent: Boolean = false
        private var requestCode: Int = 0

        override fun start() {
            if (bundle != null)
                intent!!.putExtras(bundle!!)

            if (!shouldAnimate)
                intent!!.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            if (startForResult == null) {

                if (activityOptionsBundle == null)
                    context.startActivity(intent)
                else
                    context.startActivity(intent, activityOptionsBundle)

            } else {
                val currentFragment = context.getCurrentFragment<BaseFragment<*>>()
                if (currentFragment != null)
                //currentFragment.startActivityForResult(intent, requestCode)
                    startForResult?.launch(intent)
                else
                    startForResult?.launch(intent)
                //   context.startActivityForResult(intent, requestCode)
            }

            /*if (shouldAnimate)
                context.overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);*/

            if (isToFinishCurrent)
                context.finish()
        }

        override fun addBundle(bundle: Bundle): ActivityBuilder {
            if (this.bundle != null)
                this.bundle!!.putAll(bundle)
            else
                this.bundle = bundle
            return this
        }

        override fun addSharedElements(pairs: List<Pair<View, String>>): ActivityBuilder {
            val optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context, *pairs.toTypedArray())
            activityOptionsBundle = optionsCompat.toBundle()
            return this
        }

        override fun byFinishingCurrent(): ActivityBuilder {
            isToFinishCurrent = true
            return this
        }

        override fun byFinishingAll(): ActivityBuilder {
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            return this
        }

        override fun <T : BaseFragment<*>> setPage(page: Class<T>): ActivityBuilder {
            intent!!.putExtra(ACTIVITY_FIRST_PAGE, page)
            return this
        }

        override fun forResult(requestCode: Int): ActivityBuilder {
            this.requestCode = requestCode
            return this
        }

        override fun shouldAnimate(isAnimate: Boolean): ActivityBuilder {
            shouldAnimate = isAnimate
            return this
        }

        override fun onResultActivity(startForResult: ActivityResultLauncher<Intent>): ActivityBuilder {
            this@ActivityStarter.startForResult = startForResult
            return this
        }
    }

    companion object {
        const val ACTIVITY_FIRST_PAGE = "first_page"
    }
}