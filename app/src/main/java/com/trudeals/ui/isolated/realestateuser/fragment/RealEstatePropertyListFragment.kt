package com.trudeals.ui.isolated.realestateuser.fragment

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.RealEstatePropertyListFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.dummydata.RealEstatePropertyList
import com.trudeals.ui.isolated.realestateuser.adapter.PropertyListTabAdapter
import com.trudeals.ui.isolated.realestateuser.adapter.RealEstatePropertyDetailsAdapter
import com.trudeals.ui.isolated.realestateuser.dialog.DialogDeletePropertyDetails
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.PropertyListTag
import com.trudeals.utils.toolbar.MenuItem
import com.trudeals.utils.toolbar.MenuItemTag
import com.trudeals.utils.toolbar.MenuItemType
import com.trudeals.utils.toolbar.ShowAsAction

class RealEstatePropertyListFragment : BaseFragment<RealEstatePropertyListFragmentBinding>() {

    private var isEdit: Boolean = false
    private var position: Int = -1
    private var currentTab: PropertyListTag = PropertyListTag.HOME_FOR_SALE

    private val propertyListTabAdapter by lazy { PropertyListTabAdapter() }
    private val realEstateHomeForSaleAdapter by lazy { RealEstatePropertyDetailsAdapter() }
    private val realEstateHomeForRentAdapter by lazy { RealEstatePropertyDetailsAdapter() }
    private val realEstateVacationRentalAdapter by lazy { RealEstatePropertyDetailsAdapter() }

    private val dialogDeletePropertyDetails by lazy { DialogDeletePropertyDetails() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RealEstatePropertyListFragmentBinding {
        return RealEstatePropertyListFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_my_listing))
            .addMenuItems(
                MenuItem(
                    menuItemType = MenuItemType.BOTH,
                    title = getString(R.string.label_add_new),
                    icon = R.drawable.ic_add_property,
                    tag = MenuItemTag.AddNew,
                    showAsAction = ShowAsAction.ALWAYS,
                    titleColor = R.color.C_ED1D26,
                    titleTypeface = R.font.cerebri_sans_bold,
                    titleFontSize = resources.getDimensionPixelSize(R.dimen._10ssp),
                    isVisible = true
                )
            )
            .setToolbarElevation(R.dimen._1sdp)
            .build()

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.tag) {
                MenuItemTag.AddNew -> {
                    onClickOfAddNew()
                }
                else -> {}
            }
        }
    }

    private val dataAsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.getParcelable<RealEstatePropertyList>("DATA").let { data ->
                    when (result?.data?.extras?.getParcelable<PropertyListTag>("CURRENT_TAB")) {
                        PropertyListTag.HOME_FOR_SALE -> {
                            binding.recyclerViewList.apply {
                                data?.let {
                                    if (!isEdit) realEstateHomeForSaleAdapter.addItem(it)
                                    else realEstateHomeForSaleAdapter.updateItem(position, it)
                                }
                                adapter = realEstateHomeForSaleAdapter
                            }
                        }
                        PropertyListTag.HOME_FOR_RENT -> {
                            binding.recyclerViewList.apply {
                                data?.let {
                                    if (!isEdit) realEstateHomeForRentAdapter.addItem(it)
                                    else realEstateHomeForRentAdapter.updateItem(position, it)
                                }
                                adapter = realEstateHomeForRentAdapter
                            }
                        }
                        PropertyListTag.VACATION_RENTAL -> {
                            binding.recyclerViewList.apply {
                                data?.let {
                                    if (!isEdit) realEstateVacationRentalAdapter.addItem(it)
                                    else realEstateVacationRentalAdapter.updateItem(position, it)
                                }
                                adapter = realEstateVacationRentalAdapter
                            }
                        }
                        else -> {}
                    }
                }
            }
        }

    private fun onClickOfAddNew() {
        isEdit = false
        when (currentTab) {
            PropertyListTag.HOME_FOR_SALE -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AddOrEditPropertyDetailsMainFragment::class.java
                ).onResultActivity(dataAsResult).addBundle(
                    AddOrEditPropertyDetailsMainFragment.createBundle(
                        R.string.label_add_property_details,
                        OnClick.ADD_NEW,
                        currentTab
                    )
                ).start()
            }
            PropertyListTag.HOME_FOR_RENT -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AddOrEditPropertyDetailsMainFragment::class.java
                ).onResultActivity(dataAsResult).addBundle(
                    AddOrEditPropertyDetailsMainFragment.createBundle(
                        R.string.label_add_property_details,
                        OnClick.ADD_NEW,
                        currentTab
                    )
                ).start()
            }
            PropertyListTag.VACATION_RENTAL -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AddOrEditPropertyDetailsMainFragment::class.java
                ).onResultActivity(dataAsResult).addBundle(
                    AddOrEditPropertyDetailsMainFragment.createBundle(
                        R.string.label_add_property_details,
                        OnClick.ADD_NEW,
                        currentTab
                    )
                ).start()
            }
        }
    }

    private fun setRecyclerView() = with(binding) {
        //tab list
        recyclerViewTabs.apply {
            propertyListTabAdapter.setItems(DataUtils.propertyListTab(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = propertyListTabAdapter
        }
        //data list for home for sale tab - as default
        setHomeForSaleRecyclerView()

    }

    private fun clickListener() = with(binding) {
        clickListenerTabAdapter()
        clickListenerHomeForSaleAdapter()
        clickListenerHomeForRentAdapter()
        clickListenerVacationRentalAdapter()
    }

    //click of Tab adapter
    private fun clickListenerTabAdapter() {
        propertyListTabAdapter.setOnItemClickPositionListener { item, position ->
            propertyListTabAdapter.changeSelection(position, true)
            currentTab = item.tag

            //data list for tab
            when (currentTab) {
                PropertyListTag.HOME_FOR_SALE -> {
                    setHomeForSaleRecyclerView()
                }
                PropertyListTag.HOME_FOR_RENT -> {
                    setHomeForRentRecyclerView()
                }
                PropertyListTag.VACATION_RENTAL -> {
                    setVacationRentalRecyclerView()
                }
            }
        }
    }

    private fun setHomeForSaleRecyclerView() = with(binding.recyclerViewList) {
        realEstateHomeForSaleAdapter.setItems(DataUtils.realEstateHomeForSalePropertyList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = realEstateHomeForSaleAdapter
    }

    private fun setHomeForRentRecyclerView() = with(binding.recyclerViewList) {
        realEstateHomeForRentAdapter.setItems(DataUtils.realEstateHomeForRentPropertyList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = realEstateHomeForRentAdapter
    }

    private fun setVacationRentalRecyclerView() = with(binding.recyclerViewList) {
        realEstateVacationRentalAdapter.setItems(
            DataUtils.realEstateVacationRentalPropertyList(),
            1
        )
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = realEstateVacationRentalAdapter
    }

    //click of Home for sale adapter
    private fun clickListenerHomeForSaleAdapter() = with(binding.recyclerViewList) {
        realEstateHomeForSaleAdapter.setOnItemClickPositionListener { item, _ ->
            loadPreviewDetailFragment(item)
        }
        realEstateHomeForSaleAdapter.setOnClickOfView { item, position, onClick ->
            viewClickOnAdapter(item, onClick, position, realEstateHomeForSaleAdapter)
        }
    }

    //click of Home for rent adapter
    private fun clickListenerHomeForRentAdapter() = with(binding.recyclerViewList) {
        realEstateHomeForRentAdapter.setOnItemClickPositionListener { item, _ ->
            loadPreviewDetailFragment(item)
        }
        realEstateHomeForRentAdapter.setOnClickOfView { item, position, onClick ->
            viewClickOnAdapter(item, onClick, position, realEstateHomeForRentAdapter)
        }
    }

    //click of Home for rent adapter
    private fun clickListenerVacationRentalAdapter() = with(binding.recyclerViewList) {
        realEstateVacationRentalAdapter.setOnItemClickPositionListener { item, _ ->
            loadPreviewDetailFragment(item)
        }
        realEstateVacationRentalAdapter.setOnClickOfView { item, position, onClick ->
            viewClickOnAdapter(item, onClick, position, realEstateVacationRentalAdapter)
        }
    }

    //load preview detail fragment on click item click in adapter
    private fun loadPreviewDetailFragment(item: RealEstatePropertyList) {
        navigator.loadActivity(
            IsolatedFullActivity::class.java,
            PreviewOrSavePropertyDetailFragment::class.java
        ).onResultActivity(dataAsResult).addBundle(
            PreviewOrSavePropertyDetailFragment.createBundle(
                RealEstatePropertyListFragment::class.java.simpleName,
                item,//set data
                currentTab
            )
        ).start()
    }

    //click of view like edit/delete in adapter
    private fun viewClickOnAdapter(
        item: RealEstatePropertyList,
        onClick: OnClick,
        position: Int,
        adapter: RealEstatePropertyDetailsAdapter
    ) {
        when (onClick) {
            OnClick.EDIT -> {
                this@RealEstatePropertyListFragment.position = position
                isEdit = true
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AddOrEditPropertyDetailsMainFragment::class.java
                ).onResultActivity(dataAsResult).addBundle(
                    AddOrEditPropertyDetailsMainFragment.createBundle(
                        R.string.label_edit_property_details,
                        OnClick.EDIT,
                        currentTab
                    )
                ).start()
            }
            OnClick.DELETE -> {
                dialogDeletePropertyDetails.show(
                    childFragmentManager,
                    DialogDeletePropertyDetails::class.java.simpleName
                )
                dialogDeletePropertyDetails.setOnPositiveClick {
                    dialogDeletePropertyDetails.dismiss()
                    adapter.removeItem(item)
                }
            }
            else -> {}
        }
    }
}