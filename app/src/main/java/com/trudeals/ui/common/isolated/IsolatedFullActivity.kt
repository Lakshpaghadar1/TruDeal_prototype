package com.trudeals.ui.common.isolated

import android.os.Bundle
import android.view.View
import com.trudeals.R
import com.trudeals.databinding.IsolatedAcitivtyFullBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.manager.ActivityStarter
import io.sentry.Sentry

class IsolatedFullActivity : BaseActivity() {

    lateinit var isolatedFullActivityBinding: IsolatedAcitivtyFullBinding

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        isolatedFullActivityBinding = IsolatedAcitivtyFullBinding.inflate(layoutInflater)
        return isolatedFullActivityBinding.root
    }


    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        Sentry.captureMessage(getString(R.string.app_name))
        if (savedInstanceState == null) {
            val page =
                intent.getSerializableExtra(ActivityStarter.ACTIVITY_FIRST_PAGE) as Class<BaseFragment<*>>
            load(page)
                .setBundle(intent.extras!!)
                .replace(false)
        }
    }

    private fun setUpToolbar() = with(isolatedFullActivityBinding) {
        setToolbar(customToolbar)
    }
}