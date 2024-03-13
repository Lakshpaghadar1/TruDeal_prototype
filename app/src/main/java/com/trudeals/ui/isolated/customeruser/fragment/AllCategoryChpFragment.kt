package com.trudeals.ui.isolated.customeruser.fragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.AllCategoryChipFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.fragment.HomeFragmentCU
import com.trudeals.ui.isolated.businessuser.fragment.AddBusinessProfileDetailsFragment
import com.trudeals.ui.isolated.customeruser.adapter.LocalDealsViewAllChipAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.decoratelayoutmanager.GridSpacingItemDecoration

class AllCategoryChpFragment : BaseFragment<AllCategoryChipFragmentBinding>() {

    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }

    private val localDealsViewAllChipAdapter by lazy {
        LocalDealsViewAllChipAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AllCategoryChipFragmentBinding {
        return AllCategoryChipFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        clickListeners()
        setRecyclerView()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_all_category))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun clickListeners() {
        localDealsViewAllChipAdapter.setOnItemClickPositionListener { item, _ ->
            when (sourceScreen) {
                HomeFragmentCU::class.java.simpleName -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        LocalDealsSelectedCategoryChipListFragment::class.java
                    ).addBundle(
                        LocalDealsSelectedCategoryChipListFragment.createBundle(
                            title = item.title,
                            currentTabType = item.currentTabType,
                            mainCategoryType = item.mainCategoryType
                        )
                    ).start()
                }

                AddBusinessProfileDetailsFragment::class.java.simpleName -> {
                    val intent = Intent()
                    intent.putExtra("SELECTED_CATEGORY", item.title)
                    requireActivity().setResult(Activity.RESULT_OK, intent)
                    navigator.goBack()
                }
            }
        }
    }

    private fun setRecyclerView() = with(binding.recyclerViewAllCategory) {
        localDealsViewAllChipAdapter.setItems(DataUtils.allCategoryLocalDeals(), 1)
        layoutManager =
            GridLayoutManager(
                requireContext(),
                resources.getInteger(R.integer.int_3),
                RecyclerView.VERTICAL,
                false
            )
        addItemDecoration(
            GridSpacingItemDecoration(resources.getInteger(R.integer.int_3), 30, false)
        )
        adapter = localDealsViewAllChipAdapter
    }

    companion object {
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(sourceScreen: String) = bundleOf(SOURCE_SCREEN to sourceScreen)
    }
}