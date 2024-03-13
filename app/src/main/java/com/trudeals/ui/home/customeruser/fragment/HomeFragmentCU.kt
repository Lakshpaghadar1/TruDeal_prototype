package com.trudeals.ui.home.customeruser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.trudeals.R
import com.trudeals.databinding.HomeFragmentCustomerUserBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.adapter.LocalDealsChipAdapter
import com.trudeals.ui.home.customeruser.adapter.RealEstateTabsAdapterCU
import com.trudeals.ui.home.customeruser.adapter.ViewPagerFragmentAdapterCU
import com.trudeals.ui.isolated.customeruser.bottomsheet.AdvanceFilterBottomsheet
import com.trudeals.ui.isolated.customeruser.bottomsheet.SortByBottomsheet
import com.trudeals.ui.isolated.customeruser.fragment.*
import com.trudeals.utils.*
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.toolbar.MenuItem
import com.trudeals.utils.toolbar.MenuItemTag
import com.trudeals.utils.toolbar.MenuItemType
import com.trudeals.utils.toolbar.ShowAsAction

class HomeFragmentCU : BaseFragment<HomeFragmentCustomerUserBinding>(), View.OnClickListener {

    private val advanceFilterDialog by lazy { AdvanceFilterBottomsheet() }
    private val realEstateTabsAdapter by lazy { RealEstateTabsAdapterCU() }
    private val sortByDialog by lazy { SortByBottomsheet() }

    private var viewPagerAdapter: ViewPagerFragmentAdapterCU? = null
    private val tabList by lazy { DataUtils.realEstateTabData() }

    private val localDealsChipAdapter by lazy { LocalDealsChipAdapter() }

    var currentTabType = TabType.AUTOMOTIVE
    var mainCategoryType = MainCategoryType.LOCAL_DEALS

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HomeFragmentCustomerUserBinding {
        return HomeFragmentCustomerUserBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .setToolbarTitle(getString(R.string.label_select_location))
            .setAndDecorateToolbarTitle(title = getString(R.string.label_select_location)) {
                it.setRelativeSize(0.6f, getString(R.string.label_select_location))
                    .setTypeface(
                        ResourcesCompat.getFont(
                            requireContext(),
                            R.font.cerebri_sans_regular
                        )
                    )
                    .setTextColor(R.color.C_8D8D8D, getString(R.string.label_select_location))
            }

            .setToolbarLocation(getString(R.string.dummy_location))
            .setAndDecorateToolbarLocationText(text = getString(R.string.dummy_location)) {
                it.makeTextClickable(
                    colorResId = getColor(R.color.black),
                    underlineText = false, getString(R.string.dummy_location)
                ) { _, _ ->
                    showMessage("SELECT LOCATION")
                }
            }

        addMenuItems(
            MenuItem(
                menuItemType = MenuItemType.ICON,
                icon = R.drawable.ic_toolbar_calendar,
                tag = MenuItemTag.Request,
                showAsAction = ShowAsAction.ALWAYS,
            ),
            MenuItem(
                menuItemType = MenuItemType.ICON,
                icon = R.drawable.ic_chat,
                tag = MenuItemTag.Chat,
                showAsAction = ShowAsAction.ALWAYS,
            ),
            MenuItem(
                menuItemType = MenuItemType.ICON,
                icon = R.drawable.ic_notification,
                tag = MenuItemTag.Notification,
                showAsAction = ShowAsAction.ALWAYS,
            )
        )
            .showHamburgerIcon(true)
            .setToolbarElevation(R.dimen._1sdp).build()

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.tag) {
                MenuItemTag.Request -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        RequestFragment::class.java
                    ).start()
                }
                MenuItemTag.Chat -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ChatListFragment::class.java
                    ).start()
                }
                MenuItemTag.Notification -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        NotificationFragment::class.java
                    ).start()
                }
                else -> {}
            }
        }
    }

    private fun clickListeners() = with(binding) {
        buttonRealEstate.setOnClickListener(this@HomeFragmentCU)
        buttonLocalDeals.setOnClickListener(this@HomeFragmentCU)
        imageViewChain.setOnClickListener(this@HomeFragmentCU)
        textInputEditTextSearch.setOnClickListener(this@HomeFragmentCU)
        textViewViewAll.setOnClickListener(this@HomeFragmentCU)
        buttonSubDealsAndLocalEstate.setOnClickListener(this@HomeFragmentCU)
        buttonSubRealEstate.setOnClickListener(this@HomeFragmentCU)
        buttonSubDeals.setOnClickListener(this@HomeFragmentCU)

        clickOnFiler()
    }

    private fun clickOnFiler() = with(binding) {
        textInputLayoutSearch.setEndIconOnClickListener {
            when {
                buttonRealEstate.isSelected -> {
                    advanceFilterDialog.sourceScreen = HomeFragmentCU::class.java.simpleName
                    advanceFilterDialog.show(
                        childFragmentManager,
                        AdvanceFilterBottomsheet::class.java.simpleName
                    )
                }
                buttonLocalDeals.isSelected -> {
                    sortByDialog.sourceScreen = HomeFragmentCU::class.java.simpleName
                    sortByDialog.show(
                        childFragmentManager,
                        SortByBottomsheet::class.java.simpleName
                    )
                }
                imageViewChain.isSelected -> {
                    sortByDialog.sourceScreen = HomeFragmentCU::class.java.simpleName
                    sortByDialog.show(
                        childFragmentManager,
                        SortByBottomsheet::class.java.simpleName
                    )
                }
            }
        }
    }

    private fun init() = with(binding) {
        setRealEstateAsDefaultSelection()
        when {
            buttonLocalDeals.isSelected -> {
                hideEachLayout()
                showView(constraintLayoutLocalDeals)
            }
            buttonRealEstate.isSelected -> {
                hideEachLayout(isChildPlaceholderVisible = false)
                showView(constraintLayoutRealEstate)
            }
            imageViewChain.isSelected -> {
                hideEachLayout()
                showView(linearLayoutSubCategory)
            }
        }
    }

    private fun setRealEstateAsDefaultSelection() =
        with(binding) {
            buttonRealEstate.isSelected = true
            hideEachLayout(isChildPlaceholderVisible = false)
            showView(constraintLayoutRealEstate)
            setRealEstateTabs()
        }


    override fun onClick(v: View) = with(binding) {
        when (v) {
            imageViewChain -> {
                setMainCategorySelection(MainCategoryType.BOTH)
            }
            buttonRealEstate -> {
                setMainCategorySelection(MainCategoryType.REAL_ESTATE)
            }
            buttonLocalDeals -> {
                setMainCategorySelection(MainCategoryType.LOCAL_DEALS)
            }
            textInputEditTextSearch -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    SearchFragment::class.java
                ).start()
            }
            textViewViewAll -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AllCategoryChpFragment::class.java
                )
                    .addBundle(AllCategoryChpFragment.createBundle(HomeFragmentCU::class.java.simpleName))
                    .start()
            }
            buttonSubDealsAndLocalEstate -> {
                setSubCategorySelection(SubCategoryType.DEALS_AND_REAL_ESTATE)
            }
            buttonSubRealEstate -> {
                setSubCategorySelection(SubCategoryType.REAL_ESTATE)
            }
            buttonSubDeals -> {
                setSubCategorySelection(SubCategoryType.DEALS)
            }
        }
    }


    private fun hideEachLayout(
        isSubCategoryVisible: Boolean = false,
        isChildPlaceholderVisible: Boolean = true
    ) = with(binding) {
        linearLayoutSubCategory.isVisible(isSubCategoryVisible)
        childPlaceholder.isVisible(isChildPlaceholderVisible)
        hideView(constraintLayoutRealEstate, constraintLayoutLocalDeals)
    }

    /*=============================================== Main category selection on top start ===============================================*/
    private fun setMainCategorySelection(selection: MainCategoryType) = with(binding) {
        defaultMainCategorySelection(false)
        when (selection) {
            MainCategoryType.REAL_ESTATE -> {
                buttonRealEstate.isSelected = true
                hideEachLayout(isChildPlaceholderVisible = false)
                showView(constraintLayoutRealEstate)
                setRealEstateTabs()
            }
            MainCategoryType.LOCAL_DEALS -> {
                buttonLocalDeals.isSelected = true
                hideEachLayout()
                showView(constraintLayoutLocalDeals)
                setLocalDealTabs()
            }
            MainCategoryType.BOTH -> {
                defaultMainCategorySelection(true)
                hideEachLayout()
                showView(linearLayoutSubCategory)
                setDealsAndRealEstateAsDefault()
            }
        }
    }

    private fun defaultMainCategorySelection(isSelected: Boolean) = with(binding) {
        buttonRealEstate.isSelected = isSelected
        buttonLocalDeals.isSelected = isSelected
        imageViewChain.isSelected = isSelected
    }
    /*=============================================== Main category selection on top end ===============================================*/


    /*===================================================== Local Deals Only Layout start ====================================================*/
    private fun setLocalDealTabs() =
        with(binding.recyclerViewTab) {
            localDealsChipAdapter.setItems(DataUtils.localDealsTabList(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = localDealsChipAdapter

            setChildFragment(
                mainCategoryType = MainCategoryType.LOCAL_DEALS,
                subCategoryType = SubCategoryType.NONE,
                currentTabType = localDealsChipAdapter.items!![0].currentTabType
            )
            localDealsTabCLickListeners()
        }

    private fun setChildFragment(
        mainCategoryType: MainCategoryType,
        subCategoryType: SubCategoryType,
        currentTabType: TabType
    ) {
        loadFragment(
            R.id.childPlaceholder,
            HomeRealEstateAndLocalDealsTabFragments::class.java,
            true,
            HomeRealEstateAndLocalDealsTabFragments.createBundle(
                currentTabType = currentTabType,
                subCategoryType = subCategoryType,
                mainCategoryType = mainCategoryType
            )
        )
    }

    private fun localDealsTabCLickListeners() {
        localDealsChipAdapter.setOnItemClickPositionListener { item, _ ->
            setChildFragment(
                mainCategoryType = MainCategoryType.LOCAL_DEALS,
                subCategoryType = SubCategoryType.NONE,
                currentTabType = item.currentTabType
            )
            sortByDialog.currentTab = item.currentTabType
        }
    }
    /*===================================================== Local Deals Only Layout end ====================================================*/


    /*===================================================== Real Estate Only Layout start ====================================================*/
    private fun setRealEstateTabs() {
        setViewPager()
        setTabsRecyclerView()
    }

    private fun setViewPager() = with(binding.layoutRealEstate.viewPager) {
        viewPagerAdapter = ViewPagerFragmentAdapterCU(
            this@HomeFragmentCU, tabList
        )
        adapter = viewPagerAdapter
        isUserInputEnabled = true
        offscreenPageLimit = viewPagerAdapter?.itemCount!! - 1

        this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                realEstateTabsAdapter.changeSelection(position, true)
            }
        })
    }

    private fun setTabsRecyclerView() = with(binding.recyclerViewTabsList) {
        realEstateTabsAdapter.setItems(tabList, 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_4),
            RecyclerView.VERTICAL,
            false
        )
        adapter = realEstateTabsAdapter

        realEstateTabsAdapter.setOnItemClickPositionListener { _, position ->
            realEstateTabsAdapter.changeSelection(position, true)
            binding.layoutRealEstate.viewPager.currentItem = position
        }
    }
    /*===================================================== Real Estate Only Layout end ====================================================*/

    /*===================================================== Both Linked Layout start ====================================================*/
    private fun setSubCategorySelection(selection: SubCategoryType) =
        with(binding) {
            defaultSubCategorySelection()
            when (selection) {
                SubCategoryType.REAL_ESTATE -> {
                    buttonSubRealEstate.isSelected = true
                    hideEachLayout(isSubCategoryVisible = true, isChildPlaceholderVisible = false)
                    showView(constraintLayoutRealEstate)
                    setRealEstateTabs()
                }
                SubCategoryType.DEALS -> {
                    buttonSubDeals.isSelected = true
                    hideEachLayout(isSubCategoryVisible = true)
                    showView(constraintLayoutLocalDeals)
                    setLocalDealTabs()
                }
                SubCategoryType.DEALS_AND_REAL_ESTATE -> {
                    buttonSubDealsAndLocalEstate.isSelected = true
                    defaultMainCategorySelection(true)
                    hideEachLayout(isSubCategoryVisible = true)
                    setChildFragment(
                        mainCategoryType = MainCategoryType.BOTH,
                        subCategoryType = SubCategoryType.DEALS_AND_REAL_ESTATE,
                        currentTabType = TabType.NONE
                    )
                }
                else -> {}
            }
        }

    private fun setDealsAndRealEstateAsDefault() = with(binding) {
        defaultSubCategorySelection()
        setChildFragment(
            mainCategoryType = MainCategoryType.BOTH,
            subCategoryType = SubCategoryType.DEALS_AND_REAL_ESTATE,
            currentTabType = TabType.NONE
        )
        buttonSubDealsAndLocalEstate.isSelected = true
    }

    private fun defaultSubCategorySelection() = with(binding) {
        buttonSubDealsAndLocalEstate.isSelected = false
        buttonSubRealEstate.isSelected = false
        buttonSubDeals.isSelected = false
    }


    private fun setTabs(currentTabType: TabType, mainCategoryType: MainCategoryType) {
        this.currentTabType = currentTabType
        this.mainCategoryType = mainCategoryType
    }
    /*===================================================== Both Linked Layout end ====================================================*/
}