package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.FilterListFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.adapter.CustomerHomeAdapter
import com.trudeals.ui.isolated.customeruser.bottomsheet.AdvanceFilterBottomsheet
import com.trudeals.ui.isolated.customeruser.bottomsheet.SortByBottomsheet
import com.trudeals.utils.DataUtils
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.OnClick
import com.trudeals.utils.TabType

class FilterListFragment : BaseFragment<FilterListFragmentBinding>(), View.OnClickListener {

    private val tabType by lazy {
        arguments?.getParcelable<TabType>(CURRENT_TAB)
    }

    private val mainType by lazy {
        arguments?.getParcelable<MainCategoryType>(MAIN_CATEGORY_TYPE)
    }
    private var currentTab = TabType.AUTOMOTIVE  //default is automotive
    private var mainCategoryType = MainCategoryType.LOCAL_DEALS  //default is local deals

    //private var subCategory = LinkedCategoryType.DEALS_AND_REAL_ESTATE //default is deals and real estate

    private val filterListAdapter by lazy {
        CustomerHomeAdapter(currentTab, mainCategoryType)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FilterListFragmentBinding {
        return FilterListFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_filter))
            .setToolbarElevation(R.dimen._1sdp).build()
    }

    private fun init() {
        when (mainType) {
            MainCategoryType.REAL_ESTATE -> {
                mainCategoryType = MainCategoryType.REAL_ESTATE
                filterListAdapter.setItems(DataUtils.realEstateFilteredList(), 1)
            }
            MainCategoryType.LOCAL_DEALS -> {
                mainCategoryType = MainCategoryType.LOCAL_DEALS
                filterListAdapter.setItems(DataUtils.localDealsFilteredList(), 1)
            }
           /* MainCategoryType.BOTH -> {
                mainCategoryType = MainCategoryType.BOTH

            }*/
            else -> {}
        }

        when (tabType) {
            TabType.ALL -> {
                currentTab = TabType.ALL
                //real estate each tab data - currently set same data
            }
            TabType.HOME_FOR_SALE -> {
                currentTab = TabType.HOME_FOR_SALE
            }
            TabType.RENTALS -> {
                currentTab = TabType.RENTALS
            }
            TabType.VACATION_RENTALS -> {
                currentTab = TabType.VACATION_RENTALS
            }
            TabType.AUTOMOTIVE -> {
                currentTab = TabType.AUTOMOTIVE
            }
            TabType.BEAUTY_AND_SPA -> {
                currentTab = TabType.BEAUTY_AND_SPA
            }
            TabType.FOOD_AND_DRINK -> {
                currentTab = TabType.FOOD_AND_DRINK
            }
            TabType.HEALTH_AND_FITNESS -> {
                currentTab = TabType.HEALTH_AND_FITNESS
            }
            TabType.HOME_AND_GARDEN -> {
                currentTab = TabType.HOME_AND_GARDEN
            }
            TabType.MEDICAL_SERVICE -> {
                currentTab = TabType.MEDICAL_SERVICE
            }
            TabType.SERVICES -> {
                currentTab = TabType.SERVICES
            }
            TabType.THINGS_TO_DO -> {
                currentTab = TabType.THINGS_TO_DO
            }
            TabType.TRAVEL -> {
                currentTab = TabType.TRAVEL
            }
            else -> {}
        }
    }

    private fun setRecyclerView() = with(binding.recyclerViewFilter) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = filterListAdapter
    }

    private fun clickListeners() = with(binding) {
        onClickOfItem()
        onClickOfItemView()
        onClickOfFilter()
        textInputEditTextSearch.setOnClickListener(this@FilterListFragment)
    }

    private fun onClickOfItem() {
        filterListAdapter.setOnItemClickPositionListener { item, _ ->
            when (mainCategoryType) {
                MainCategoryType.REAL_ESTATE -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        RealEstateDetailFragmentCU::class.java
                    ).addBundle(
                        RealEstateDetailFragmentCU.createBundle(
                            item, MainCategoryType.REAL_ESTATE, currentTab
                        )
                    ).start()
                }
                MainCategoryType.LOCAL_DEALS -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        LocalDealDetailFragment::class.java
                    ).addBundle(
                        LocalDealDetailFragment.createBundle(
                            item, currentTab, MainCategoryType.LOCAL_DEALS,
                        )
                    ).start()
                }
                /*MainCategoryType.BOTH -> {
                    when(subCategory) {
                        LinkedCategoryType.BOTH -> {
                            //open detail screen based on type of item (item type that means - real estate or local deal)
                        }
                        LinkedCategoryType.REAL_ESTATE -> {
                            navigator.loadActivity(
                                IsolatedFullActivity::class.java,
                                RealEstateDetailFragment::class.java
                            ).addBundle(
                                RealEstateDetailFragment.createBundle(
                                    item, MainCategoryType.REAL_ESTATE, currentTab
                                )
                            ).start()
                        }
                        LinkedCategoryType.LOCAL_DEALS -> {
                            navigator.loadActivity(
                                IsolatedFullActivity::class.java,
                                LocalDealDetailFragment::class.java
                            ).addBundle(
                                LocalDealDetailFragment.createBundle(
                                    item, MainCategoryType.LOCAL_DEALS, currentTab
                                )
                            ).start()
                        }
                    }
                }
            }*/
                else -> {}
            }
        }
    }

    private fun onClickOfItemView() {
        filterListAdapter.setOnClickOfView { _, _, onClick ->
            when (onClick) {
                OnClick.HEART -> {}
                OnClick.CONTACT -> {
                    showMessage("OPEN NATIVE DIAL PAD")
                }
                else -> {}
            }
        }
    }

    private fun onClickOfFilter() = with(binding) {
        textInputLayoutSearch.setEndIconOnClickListener {
            when (mainCategoryType) {
                MainCategoryType.REAL_ESTATE -> {
                    val bottomsheetDialog = AdvanceFilterBottomsheet()
                    bottomsheetDialog.sourceScreen = FilterListFragment::class.java.simpleName
                    bottomsheetDialog.show(
                        childFragmentManager,
                        AdvanceFilterBottomsheet::class.java.simpleName
                    )
                }
                MainCategoryType.LOCAL_DEALS -> {
                    val bottomsheetDialog = SortByBottomsheet()
                    bottomsheetDialog.sourceScreen = FilterListFragment::class.java.simpleName
                    bottomsheetDialog.show(
                        childFragmentManager,
                        SortByBottomsheet::class.java.simpleName
                    )
                }
                MainCategoryType.BOTH -> {
                    //right now fow both - no filter screen is there
                    // - sort by bottomsheet can be used as common filter bottomsheet
                    val bottomsheetDialog = SortByBottomsheet()
                    bottomsheetDialog.sourceScreen = FilterListFragment::class.java.simpleName
                    bottomsheetDialog.show(
                        childFragmentManager,
                        SortByBottomsheet::class.java.simpleName
                    )
                }
            }
        }
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            textInputEditTextSearch -> {
                navigator.loadActivity(IsolatedFullActivity::class.java, SearchFragment::class.java)
                    .start()
            }
        }
    }

    companion object {
        private const val CURRENT_TAB = "CURRENT_TAB"

        //private const val SUB_CATEGORY = "SUB_CATEGORY"
        private const val MAIN_CATEGORY_TYPE = "MAIN_CATEGORY_TYPE"
        fun createBundle(
            currentTab: TabType,
            //subCategory: LinkedCategoryType,
            mainCategoryType: MainCategoryType
        ) =
            bundleOf(
                CURRENT_TAB to currentTab,
                //SUB_CATEGORY to subCategory,
                MAIN_CATEGORY_TYPE to mainCategoryType
            )
    }
}