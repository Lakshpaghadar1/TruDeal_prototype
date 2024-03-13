package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.IsolatedListFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.adapter.CustomerHomeAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.OnClick
import com.trudeals.utils.TabType

class LocalDealsSelectedCategoryChipListFragment :
    BaseFragment<IsolatedListFragmentBinding>() {

    private val tabType by lazy {
        arguments?.getParcelable<TabType>(CURRENT_TAB_TYPE)
    }

    private var currentTab = TabType.AUTOMOTIVE  //default is automotive
    private var mainCategoryType = MainCategoryType.LOCAL_DEALS  //default is real estate

    private val localDealCategoryListAdapter by lazy {
        CustomerHomeAdapter(currentTab, mainCategoryType)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): IsolatedListFragmentBinding {
        return IsolatedListFragmentBinding.inflate(inflater)
    }

    override fun bindData() {
        init()
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(arguments?.getString(TITLE) ?: "")
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun init() {
        when (tabType) {
            TabType.AUTOMOTIVE -> {
                currentTab = TabType.AUTOMOTIVE
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealAutomotiveCategoryData(),
                    1
                )
            }
            TabType.BEAUTY_AND_SPA -> {
                currentTab = TabType.BEAUTY_AND_SPA
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealBeautyAndSpaCategoryData(),
                    1
                )
            }
            TabType.FOOD_AND_DRINK -> {
                currentTab = TabType.FOOD_AND_DRINK
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealAutomotiveCategoryData(),
                    1
                )
            }
            TabType.HEALTH_AND_FITNESS -> {
                currentTab = TabType.HEALTH_AND_FITNESS
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealBeautyAndSpaCategoryData(),
                    1
                )
            }
            TabType.HOME_AND_GARDEN -> {
                currentTab = TabType.HOME_AND_GARDEN
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealAutomotiveCategoryData(),
                    1
                )
            }
            TabType.MEDICAL_SERVICE -> {
                currentTab = TabType.MEDICAL_SERVICE
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealBeautyAndSpaCategoryData(),
                    1
                )
            }
            TabType.SERVICES -> {
                currentTab = TabType.SERVICES
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealAutomotiveCategoryData(),
                    1
                )
            }
            TabType.THINGS_TO_DO -> {
                currentTab = TabType.THINGS_TO_DO
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealBeautyAndSpaCategoryData(),
                    1
                )
            }
            TabType.TRAVEL -> {
                currentTab = TabType.TRAVEL
                localDealCategoryListAdapter.setItems(
                    DataUtils.localDealAutomotiveCategoryData(),
                    1
                )
            }
            else -> {}
        }
    }

    private fun setRecyclerView() = with(binding.recyclerViewList) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = localDealCategoryListAdapter
    }

    private fun clickListeners() = with(binding) {
        localDealCategoryListAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                LocalDealDetailFragment::class.java
            ).addBundle(
                LocalDealDetailFragment.createBundle(
                    item, currentTab, MainCategoryType.LOCAL_DEALS
                )
            ).start()
        }

        localDealCategoryListAdapter.setOnClickOfView { _, _, onClick ->
            when (onClick) {
                OnClick.HEART -> {}
                OnClick.CONTACT -> {
                    showMessage("OPEN NATIVE DIAL PAD")
                }
                else -> {}
            }
        }
    }

    companion object {
        private const val TITLE = "TITLE"
        private const val CURRENT_TAB_TYPE = "CURRENT_TAB_TYPE"
        private const val MAIN_CATEGORY_TYPE = "MAIN_CATEGORY_TYPE"
        fun createBundle(
            title: String,
            currentTabType: TabType,
            mainCategoryType: MainCategoryType
        ) =
            bundleOf(
                TITLE to title,
                CURRENT_TAB_TYPE to currentTabType,
                MAIN_CATEGORY_TYPE to mainCategoryType
            )
    }
}