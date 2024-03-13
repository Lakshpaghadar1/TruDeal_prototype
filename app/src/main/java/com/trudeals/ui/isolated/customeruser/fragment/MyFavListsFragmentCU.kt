package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.IsolatedListFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.adapter.CustomerHomeAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.OnClick
import com.trudeals.utils.TabType

class MyFavListsFragmentCU : BaseFragment<IsolatedListFragmentBinding>() {

    private var currentTab = TabType.NONE  //default is automotive
    private var mainCategoryType = MainCategoryType.LOCAL_DEALS  //default is real estate

    private val favListAdapter by lazy {
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

    private fun init() {
        //set item view and data as per category
        when (arguments?.getParcelable<MainCategoryType>(MAIN_CATEGORY_TYPE)) {
            MainCategoryType.REAL_ESTATE -> {
                mainCategoryType = MainCategoryType.REAL_ESTATE
                favListAdapter.setItems(DataUtils.realEstateFavListCU(), 1)
            }
            MainCategoryType.LOCAL_DEALS -> {
                mainCategoryType = MainCategoryType.LOCAL_DEALS
                favListAdapter.setItems(DataUtils.localDealsFavListCU(), 1)
            }
            else -> {}
        }
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(arguments?.getString(TITLE) ?: "")
            .build()
    }

    private fun setRecyclerView() = with(binding.recyclerViewList) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = favListAdapter
    }

    private fun clickListeners() = with(binding) {
        favListAdapter.setOnItemClickPositionListener { item, _ ->
            when (arguments?.getParcelable<MainCategoryType>(MAIN_CATEGORY_TYPE)) {
                MainCategoryType.REAL_ESTATE -> {
                    mainCategoryType = MainCategoryType.REAL_ESTATE
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        RealEstateDetailFragmentCU::class.java
                    ).addBundle(
                        LocalDealDetailFragment.createBundle(
                            item, currentTab, MainCategoryType.REAL_ESTATE
                        )
                    ).start()
                }
                MainCategoryType.LOCAL_DEALS -> {
                    mainCategoryType = MainCategoryType.LOCAL_DEALS
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        LocalDealDetailFragment::class.java
                    ).addBundle(
                        LocalDealDetailFragment.createBundle(
                            item, currentTab, MainCategoryType.LOCAL_DEALS
                        )
                    ).start()
                }
                else -> {}
            }
        }

        favListAdapter.setOnClickOfView { _, _, onClick ->
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
        private const val MAIN_CATEGORY_TYPE = "MAIN_CATEGORY_TYPE"
        fun createBundle(title: String, mainCategoryType: MainCategoryType) =
            bundleOf(TITLE to title, MAIN_CATEGORY_TYPE to mainCategoryType)
    }
}