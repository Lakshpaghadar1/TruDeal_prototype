package com.trudeals.ui.home.businessuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.trudeals.R
import com.trudeals.databinding.IsolatedListFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.adapter.BusinessHomeAdapter
import com.trudeals.ui.isolated.businessuser.fragment.BusinessDealDetailFragment
import com.trudeals.ui.isolated.dummydata.BusinessDeal
import com.trudeals.utils.DataUtils

class MyFavOrViewAllListFragmentBU : BaseFragment<IsolatedListFragmentBinding>() {

    private val businessListAdapter by lazy { BusinessHomeAdapter() }

    private val title by lazy { arguments?.getString(TITLE) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): IsolatedListFragmentBinding {
        return IsolatedListFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setFavListRecyclerView()
        favListAdapterClickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
        title?.let { setToolbarTitle(it) }
        setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setFavListRecyclerView() = with(binding.recyclerViewList) {
        when (title) {
            //************************************ fav list set data start ************************************//
            getString(R.string.label_my_deals) -> {
                businessListAdapter.setItems(DataUtils.businessFavList(), 1)
            }
            //************************************ fav list set data end *************************************//

            //********************************* view all list set data start *********************************//
            //on view all -> bundle -> title "Automotive" is temporary - as it will be based on business profile
            //so lets create else condition to manage click of view all
            else -> {
                businessListAdapter.setItems(DataUtils.businessList(), 1)
            }
            //********************************* view all list set data end *********************************//
        }

        layoutManager = LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.RecyclerView.VERTICAL,
            false
        )
        adapter = businessListAdapter
    }

    private fun favListAdapterClickListener() {
        businessListAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                BusinessDealDetailFragment::class.java
            ).addBundle(
                BusinessDealDetailFragment.createBundle(
                    item, BusinessDeal(),  //temporary managed 2 data class that is why - passing 2 data class here - will manage at API time
                    MyFavOrViewAllListFragmentBU::class.java.simpleName
                )
            ).start()
        }
    }

    companion object {
        private const val TITLE = "TITLE"

        fun createBundle(title: String) = bundleOf(TITLE to title)
    }
}