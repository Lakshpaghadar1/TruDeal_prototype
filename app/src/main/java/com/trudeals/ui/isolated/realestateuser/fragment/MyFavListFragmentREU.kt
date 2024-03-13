package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.HomeRealEstateListingFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.realestateuser.adapter.MyFavListAdapterREU
import com.trudeals.utils.DataUtils
import com.trudeals.utils.TabType

class MyFavListFragmentREU : BaseFragment<HomeRealEstateListingFragmentBinding>() {

    private val myFavListAdapter by lazy { MyFavListAdapterREU() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HomeRealEstateListingFragmentBinding {
        return HomeRealEstateListingFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_my_listings))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setRecyclerView() = with(binding.recyclerViewTabsList) {
        myFavListAdapter.setItems(DataUtils.realEstateFavList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = myFavListAdapter
    }

    private fun clickListeners() {
        myFavListAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                RealEstateDetailFragmentREU::class.java
            ).addBundle(
                RealEstateDetailFragmentREU.createBundle(
                    item,
                    TabType.ALL
                )
            ).start()
        }
    }
}