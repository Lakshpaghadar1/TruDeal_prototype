package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.MapFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment

class MapFragment: BaseFragment<MapFragmentBinding>() {
    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): MapFragmentBinding {
        return MapFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() { }

    override fun setUpToolbar() = with(toolbar){
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_location))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }
}