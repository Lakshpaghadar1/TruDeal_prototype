package com.trudeals.ui.home.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.HomeRealEstateListingFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.realestateuser.adapter.RealEstateHomeAdapter
import com.trudeals.ui.isolated.realestateuser.fragment.RealEstateDetailFragmentREU
import com.trudeals.utils.DataUtils
import com.trudeals.utils.TabType

class HomeRealEstateTabFragments :
    BaseFragment<HomeRealEstateListingFragmentBinding>() {

    private val currentTab by lazy { arguments?.getParcelable<TabType>(CURRENT_TAB_TYPE) }
    private var currentTabType = TabType.AUTOMOTIVE  //default is all
    private val realEstateHomeAdapter by lazy { RealEstateHomeAdapter(currentTabType) }

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
        init()
        setRecyclerView()
        clickListeners()
    }

    private fun init() {
        when (currentTab) {
            TabType.ALL -> {
                currentTabType = TabType.ALL
                //real estate each tab data - currently set same data
                realEstateHomeAdapter.setItems(DataUtils.realEstateHomeAllTabsData(), 1)
            }
            TabType.HOME_FOR_SALE -> {
                currentTabType = TabType.HOME_FOR_SALE
                realEstateHomeAdapter.setItems(DataUtils.realEstateHomeAllTabsData(), 1)
            }
            TabType.RENTALS -> {
                currentTabType = TabType.RENTALS
                realEstateHomeAdapter.setItems(DataUtils.realEstateHomeAllTabsData(), 1)
            }
            TabType.VACATION_RENTALS -> {
                currentTabType = TabType.VACATION_RENTALS
                realEstateHomeAdapter.setItems(DataUtils.realEstateHomeAllTabsData(), 1)
            }
            else -> {}
        }
    }

    override fun setUpToolbar() {}

    private fun setRecyclerView() = with(binding.recyclerViewTabsList) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = realEstateHomeAdapter

    }

    private fun clickListeners() {
        realEstateHomeAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                RealEstateDetailFragmentREU::class.java
            ).addBundle(
                    RealEstateDetailFragmentREU.createBundle(
                        item,
                        currentTabType)
                ).start()
        }
    }

    companion object {
        private const val CURRENT_TAB_TYPE = "CURRENT_TAB_TYPE"
        fun createBundle(currentTabType: TabType) =
            bundleOf(CURRENT_TAB_TYPE to currentTabType)
    }
}