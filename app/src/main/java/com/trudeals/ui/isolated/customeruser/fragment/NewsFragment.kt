package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.NewsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.adapter.NewsAdapter
import com.trudeals.utils.DataUtils

class NewsFragment : BaseFragment<NewsFragmentBinding>() {

    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): NewsFragmentBinding {
        return NewsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_news))
            .build()
    }

    private fun setRecyclerView() = with(binding.recyclerViewNews) {
        newsAdapter.setItems(DataUtils.newsList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = newsAdapter
    }

    private fun clickListener() {
        newsAdapter.setOnItemClickPositionListener { _, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                NewsDetailsFragment::class.java
            ).start()
        }
    }
}