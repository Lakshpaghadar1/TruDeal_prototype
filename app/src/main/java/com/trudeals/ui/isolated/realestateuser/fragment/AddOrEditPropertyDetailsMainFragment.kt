package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.trudeals.R
import com.trudeals.databinding.AddOrEditPropertyDetailsMainBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.adapter.ViewPagerFragmentAdapter
import com.trudeals.ui.isolated.dummydata.RealEstatePropertyList
import com.trudeals.ui.isolated.realestateuser.adapter.StepsAdapter
import com.trudeals.utils.*

class AddOrEditPropertyDetailsMainFragment : BaseFragment<AddOrEditPropertyDetailsMainBinding>() {

    private val viewPagerAdapter by lazy {
        ViewPagerFragmentAdapter(this,
            ArrayList<Fragment>().apply {
                add(AddOrEditPropertyDetailsFragment().apply {
                    arguments = AddOrEditPropertyDetailsFragment.createBundle(onClick, currentTab)
                })
                add(SelectRentDurationFragment())
            }
        )
    }
    private val stepsAdapter by lazy { StepsAdapter() }
    private val currentTab by lazy {
        arguments?.getParcelable<PropertyListTag>(CURRENT_TAB) ?: PropertyListTag.HOME_FOR_SALE
    }
    private val onClick by lazy { arguments?.getParcelable<OnClick>(ON_CLICK) ?: OnClick.ADD_NEW }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean
    ): AddOrEditPropertyDetailsMainBinding {
        return AddOrEditPropertyDetailsMainBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setStepsVisibility()
        setStepsRecyclerView()
        setViewPager()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true).showBackButton(true)
            .setOnBackButtonClickListener {
                if (binding.viewPager.currentItem == 0) {
                    navigator.goBack()
                } else {
                    binding.viewPager.currentItem--
                }
            }
        arguments?.getInt(TITLE)?.let { setToolbarTitle(it) }
        setToolbarElevation(R.dimen._1sdp).build()
    }

    private fun setStepsVisibility() = with(binding) {
        when (currentTab) {
            PropertyListTag.HOME_FOR_SALE -> {
                hideView(recyclerViewStep)
            }
            else -> {
                showView(recyclerViewStep)
            }
        }
    }

    private fun setViewPager() = with(binding.viewPager) {
        adapter = viewPagerAdapter
        isUserInputEnabled = false
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                stepsAdapter.changeSelection(position, true)
            }
        })
    }

    private fun setStepsRecyclerView() = with(binding.recyclerViewStep) {
        stepsAdapter.setItems(DataUtils.setSteps(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = stepsAdapter
    }

    private val fragment1Index = 0
    fun navigateToPreviousStep(steps: StepsCount) = with(binding) {
        when(steps) {
            StepsCount.STEP_ONE -> {
                viewPager.setCurrentItem(fragment1Index, true)
            }
            else -> {}
        }
    }

    fun navigateToNext(dataOnValidation: RealEstatePropertyList, steps: StepsCount) =
        with(binding) {
            //go to 2nd fragment
            viewPager.currentItem++
            when (steps) {
                StepsCount.STEP_TWO ->   //pass bundle to 2nd step fragment
                    viewPagerAdapter.getFragment(viewPager.currentItem)
                        .apply {
                            arguments = SelectRentDurationFragment.createBundle(
                                onClick,
                                currentTab,
                                dataOnValidation
                            )
                        }
                else -> {}
            }
        }

    companion object {
        private const val TITLE = "TITLE"
        private const val ON_CLICK = "ON_CLICK"
        private const val CURRENT_TAB = "CURRENT_TAB"

        fun createBundle(title: Int, onClick: OnClick, currentTab: PropertyListTag) =
            bundleOf(TITLE to title, ON_CLICK to onClick, CURRENT_TAB to currentTab)
    }
}