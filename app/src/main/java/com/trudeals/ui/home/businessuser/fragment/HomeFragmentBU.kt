package com.trudeals.ui.home.businessuser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.HomeFragmentBusinessUserBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.adapter.BusinessHomeAdapter
import com.trudeals.ui.home.realestateuser.adapter.RealEstateOrBusinessHomeChipAdapter
import com.trudeals.ui.isolated.businessuser.fragment.AddOrEditDealFragment
import com.trudeals.ui.isolated.businessuser.fragment.BusinessDealDetailFragment
import com.trudeals.ui.isolated.businessuser.fragment.CreateBusinessProfileMainFragment
import com.trudeals.ui.isolated.customeruser.fragment.ChatListFragment
import com.trudeals.ui.isolated.customeruser.fragment.NotificationFragment
import com.trudeals.ui.isolated.dummydata.BusinessDeal
import com.trudeals.utils.DataUtils
import com.trudeals.utils.RealEstateOrBusinessChipType
import com.trudeals.utils.toolbar.MenuItem
import com.trudeals.utils.toolbar.MenuItemTag
import com.trudeals.utils.toolbar.MenuItemType
import com.trudeals.utils.toolbar.ShowAsAction

class HomeFragmentBU : BaseFragment<HomeFragmentBusinessUserBinding>(), View.OnClickListener {

    private val businessHomeChipAdapter by lazy { RealEstateOrBusinessHomeChipAdapter() }
    private val businessHomeAdapter by lazy { BusinessHomeAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HomeFragmentBusinessUserBinding {
        return HomeFragmentBusinessUserBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setChipRecyclerView()
        setListRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
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

    private fun clickListener() = with(binding) {
        chipAdapterClickListener()
        listAdapterClickListener()
        textViewViewAll.setOnClickListener(this@HomeFragmentBU)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            textViewViewAll -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    MyFavOrViewAllListFragmentBU::class.java
                )
                    .addBundle(MyFavOrViewAllListFragmentBU.createBundle(getString(R.string.label_automotive)))
                    .start() //on view all -> bundle -> title "Automotive" is temporary - as it will be based on business profile
            }
        }
    }

    /*===================================================== Business Home Chips start ====================================================*/
    private fun setChipRecyclerView() = with(binding.recyclerViewHomeChips) {
        businessHomeChipAdapter.setItems(DataUtils.businessHomeChip(), 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_2),
            RecyclerView.VERTICAL,
            false
        )
        adapter = businessHomeChipAdapter
    }

    private fun chipAdapterClickListener() {
        businessHomeChipAdapter.setOnItemClickPositionListener { item, _ ->
            when (item.chip) {
                RealEstateOrBusinessChipType.BUSINESS_PROFILE -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        CreateBusinessProfileMainFragment::class.java
                    ).addBundle(CreateBusinessProfileMainFragment.createBundle(HomeFragmentBU::class.java.simpleName))
                        .start()
                }
                RealEstateOrBusinessChipType.ADD_OR_EDIT_DEALS -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        AddOrEditDealFragment::class.java
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
    /*===================================================== Business Home Chips end ====================================================*/

    /*===================================================== Business Home Listing start ====================================================*/

    private fun setListRecyclerView() = with(binding.recyclerViewList) {
        businessHomeAdapter.setItems(DataUtils.businessList(), 1)
        layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        adapter = businessHomeAdapter
    }

    private fun listAdapterClickListener() {
        businessHomeAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                BusinessDealDetailFragment::class.java
            ).addBundle(
                BusinessDealDetailFragment.createBundle(
                    item,
                    BusinessDeal(),
                    HomeFragmentBU::class.java.simpleName
                )
            ).start()
            //temporary managed 2 data class that is why - passing 2 data class here - will manage at API time
        }
    }

/*===================================================== Business Home Listing end ====================================================*/
}