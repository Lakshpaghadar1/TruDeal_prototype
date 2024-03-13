package com.trudeals.ui.auth

import android.os.Bundle
import android.view.View
import com.trudeals.R
import com.trudeals.databinding.AuthActivityBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.auth.usertype.UserTypeFragment
import com.trudeals.ui.base.BaseActivity
import io.sentry.Sentry

class AuthActivity : BaseActivity() {
    private lateinit var binding: AuthActivityBinding

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        binding = AuthActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sentry.captureMessage(getString(R.string.app_name))
        setUpToolbar()
        load(UserTypeFragment::class.java).replace(false)
    }

    private fun setUpToolbar() = with(binding) {
        setToolbar(customToolbar)
    }
}