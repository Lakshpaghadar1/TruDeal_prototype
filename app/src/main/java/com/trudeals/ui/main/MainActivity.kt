package com.trudeals.ui.main

import android.os.Bundle
import android.view.View
import com.trudeals.R
import com.trudeals.databinding.MainActivityBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        binding = MainActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()

       // load(HomeFragment::class.java).replace(false)
    }

    private fun setUpToolbar() = with(binding) {
        setToolbar(customToolbar)
    }
}