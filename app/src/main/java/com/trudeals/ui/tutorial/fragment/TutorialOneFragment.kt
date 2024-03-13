package com.trudeals.ui.tutorial.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.trudeals.databinding.TutorialFragmentOneBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment

class TutorialOneFragment: BaseFragment<TutorialFragmentOneBinding>() {
    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): TutorialFragmentOneBinding {
        return TutorialFragmentOneBinding.inflate(layoutInflater)
    }

    override fun bindData() {

    }

    override fun setUpToolbar() {}
}