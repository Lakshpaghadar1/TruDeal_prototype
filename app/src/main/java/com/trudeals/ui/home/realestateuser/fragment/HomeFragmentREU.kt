package com.trudeals.ui.home.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.trudeals.R
import com.trudeals.databinding.HomeFragmentRealEstateUserBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.realestateuser.adapter.RealEstateOrBusinessHomeChipAdapter
import com.trudeals.ui.home.realestateuser.adapter.RealEstateTabsAdapter
import com.trudeals.ui.home.realestateuser.adapter.ViewPagerFragmentAdapterREU
import com.trudeals.ui.isolated.customeruser.fragment.ChatListFragment
import com.trudeals.ui.isolated.customeruser.fragment.NotificationFragment
import com.trudeals.ui.isolated.realestateuser.fragment.BookingRequestFragment
import com.trudeals.ui.isolated.realestateuser.fragment.RealEstatePropertyListFragment
import com.trudeals.ui.isolated.realestateuser.fragment.ScheduleOpenHouseTimingFragment
import com.trudeals.utils.DataUtils
import com.trudeals.utils.RealEstateOrBusinessChipType
import com.trudeals.utils.toolbar.MenuItem
import com.trudeals.utils.toolbar.MenuItemTag
import com.trudeals.utils.toolbar.MenuItemType
import com.trudeals.utils.toolbar.ShowAsAction

class HomeFragmentREU : BaseFragment<HomeFragmentRealEstateUserBinding>() {

    private val realEstateHomeChipAdapter by lazy { RealEstateOrBusinessHomeChipAdapter() }
    private val realEstateTabsAdapter by lazy { RealEstateTabsAdapter() }
    private var viewPagerAdapter: ViewPagerFragmentAdapterREU? = null
    private val tabList by lazy { DataUtils.realEstateHomeTabData() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HomeFragmentRealEstateUserBinding {
        return HomeFragmentRealEstateUserBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListener()
        setRealEstateTabs()
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
                icon = R.drawable.ic_notification,
                tag = MenuItemTag.Notification,
                showAsAction = ShowAsAction.ALWAYS,
            )
        )
            .showHamburgerIcon(true)
            .setToolbarElevation(R.dimen._1sdp).build()

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.tag) {
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

    /*===================================================== Real Estate Home Chips start ====================================================*/
    private fun setRecyclerView() = with(binding.recyclerViewHomeChips) {
        realEstateHomeChipAdapter.setItems(DataUtils.realEstateHomeChip(), 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_2),
            RecyclerView.VERTICAL,
            false
        )
        adapter = realEstateHomeChipAdapter
    }

    private fun clickListener() {
        realEstateHomeChipAdapter.setOnItemClickPositionListener { item, _ ->
            when (item.chip) {
                RealEstateOrBusinessChipType.MY_LISTING -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        RealEstatePropertyListFragment::class.java
                    ).start()
                }
                RealEstateOrBusinessChipType.SCHEDULE_AN_OPEN_HOUSE -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ScheduleOpenHouseTimingFragment::class.java
                    ).start()
                }
                RealEstateOrBusinessChipType.BOOKING_REQUEST -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        BookingRequestFragment::class.java
                    ).start()
                }
                RealEstateOrBusinessChipType.CHAT -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ChatListFragment::class.java
                    ).start()
                }
                else -> {}
            }
        }
    }
    /*===================================================== Real Estate Home Chips end ====================================================*/

    /*===================================================== Real Estate Tabs Layout start ====================================================*/
    private fun setRealEstateTabs() {
        setViewPager()
        setTabsRecyclerView()
    }

    private fun setViewPager() = with(binding.layoutRealEstate.viewPager) {
        viewPagerAdapter = ViewPagerFragmentAdapterREU(
            this@HomeFragmentREU,
            tabList
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
        layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.int_4), RecyclerView.VERTICAL, false)
        adapter = realEstateTabsAdapter

        realEstateTabsAdapter.setOnItemClickPositionListener { _, position ->
            realEstateTabsAdapter.changeSelection(position, true)
            binding.layoutRealEstate.viewPager.currentItem = position
        }
    }
}
/*===================================================== Real Estate Tabs Layout end ====================================================*/