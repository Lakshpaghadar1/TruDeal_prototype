package com.trudeals.ui.isolated.customeruser.bottomsheet

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.SortByBottomsheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.ui.base.BaseBottomSheet
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.fragment.HomeFragmentCU
import com.trudeals.ui.isolated.customeruser.adapter.SortByAdapter
import com.trudeals.ui.isolated.customeruser.fragment.FilterListFragment
import com.trudeals.utils.DataUtils
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.TabType

class SortByBottomsheet : BaseBottomSheet<SortByBottomsheetBinding>(), View.OnClickListener {

    var sourceScreen: String? = null
    var currentTab: TabType = TabType.AUTOMOTIVE

    private val sortByAdapter by lazy {
        SortByAdapter()
    }

    override fun createViewBinding(inflater: LayoutInflater): SortByBottomsheetBinding {
        return SortByBottomsheetBinding.inflate(inflater)
    }

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
        bottomSheetComponent.inject(this)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }

    override fun destroyViewBinding() {}

    private fun clickListeners() = with(binding) {
        buttonApply.setOnClickListener(this@SortByBottomsheet)

        sortByAdapter.setOnItemClickPositionListener { _, position ->
            sortByAdapter.changeSelection(position, true)
        }
    }

    private fun setRecyclerView() = with(binding.recyclerViewSortBy) {
        sortByAdapter.setItems(DataUtils.sortBy(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = sortByAdapter
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonApply -> {
                this@SortByBottomsheet.dismiss()
                if (sourceScreen == HomeFragmentCU::class.java.simpleName) {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        FilterListFragment::class.java
                    ).addBundle(
                        FilterListFragment.createBundle(
                            currentTab = currentTab,
                            //subCategory = LinkedCategoryType.BOTH,
                            mainCategoryType = MainCategoryType.LOCAL_DEALS
                        )
                    ).start()
                }
            }
        }
    }
}