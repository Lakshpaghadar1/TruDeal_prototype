package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.trudeals.R
import com.trudeals.databinding.NewsDetailsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.isolated.dummydata.NewsList

class NewsDetailsFragment : BaseFragment<NewsDetailsFragmentBinding>() {
    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): NewsDetailsFragmentBinding {
        return NewsDetailsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {

    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_news_details))
            .build()
    }

    companion object {
        private const val DATA = "DATA"
        fun createBundle(data: NewsList) = bundleOf(DATA to data)
    }
}