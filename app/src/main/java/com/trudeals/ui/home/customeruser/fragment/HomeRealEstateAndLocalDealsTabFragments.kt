package com.trudeals.ui.home.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.HomeRealEstateAndLocalDealsTabsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.adapter.CustomerHomeAdapter
import com.trudeals.ui.isolated.businessuser.fragment.ContactOptionsBottomsheet
import com.trudeals.ui.isolated.customeruser.fragment.LocalDealDetailFragment
import com.trudeals.ui.isolated.customeruser.fragment.RealEstateDetailFragmentCU
import com.trudeals.utils.DataUtils.getHomeListBasedOnCategory
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.OnClick
import com.trudeals.utils.SubCategoryType
import com.trudeals.utils.TabType

class HomeRealEstateAndLocalDealsTabFragments :
    BaseFragment<HomeRealEstateAndLocalDealsTabsFragmentBinding>() {

    private val currentTab by lazy {
        arguments?.getParcelable<TabType>(CURRENT_TAB_TYPE)
    }

    private val subTab by lazy {
        arguments?.getParcelable<SubCategoryType>(SUB_CATEGORY_TYPE)
    }

    private val mainTab by lazy {
        arguments?.getParcelable<MainCategoryType>(MAIN_CATEGORY_TYPE)
    }

    private val customerHomeAdapter by lazy {
        mainTab?.let { currentTab?.let { it1 -> CustomerHomeAdapter(it1, it) } }
    }

    private val contactOptionsDialog by lazy { ContactOptionsBottomsheet() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HomeRealEstateAndLocalDealsTabsFragmentBinding {
        return HomeRealEstateAndLocalDealsTabsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
        subTab?.getHomeListBasedOnCategory(currentTab!!)
            ?.let { customerHomeAdapter?.setItems(it, 1) }
    }

    override fun setUpToolbar() {}

    private fun setRecyclerView() = with(binding.recyclerViewTabsList) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = customerHomeAdapter

    }

    private fun clickListeners() = with(binding) {
        customerHomeAdapter?.setOnItemClickPositionListener { item, _ ->
            when (mainTab) {
                MainCategoryType.REAL_ESTATE -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        RealEstateDetailFragmentCU::class.java
                    )
                        .addBundle(
                            RealEstateDetailFragmentCU.createBundle(
                                item,
                                MainCategoryType.REAL_ESTATE,
                                currentTab!!
                            )
                        ).start()
                }
                MainCategoryType.LOCAL_DEALS -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        LocalDealDetailFragment::class.java
                    )
                        .addBundle(
                            LocalDealDetailFragment.createBundle(
                                item,
                                currentTab!!,
                                MainCategoryType.LOCAL_DEALS
                            )
                        ).start()
                }
                MainCategoryType.BOTH -> {

                }
                else -> {}
            }
        }

        customerHomeAdapter?.setOnClickOfView { _, _, onClick ->
            when (onClick) {
                OnClick.HEART -> {}
                OnClick.CONTACT -> {
                    contactOptionsDialog.show(
                        childFragmentManager,
                        ContactOptionsBottomsheet::class.java.simpleName
                    )
                }
                else -> {}
            }
        }
    }

    companion object {
        private const val CURRENT_TAB_TYPE = "CURRENT_TAB_TYPE"
        private const val SUB_CATEGORY_TYPE = "SUB_CATEGORY_TYPE"
        private const val MAIN_CATEGORY_TYPE = "MAIN_CATEGORY_TYPE"
        fun createBundle(
            currentTabType: TabType,
            subCategoryType: SubCategoryType,
            mainCategoryType: MainCategoryType
        ) =
            bundleOf(
                CURRENT_TAB_TYPE to currentTabType,
                SUB_CATEGORY_TYPE to subCategoryType,
                MAIN_CATEGORY_TYPE to mainCategoryType
            )
    }
}