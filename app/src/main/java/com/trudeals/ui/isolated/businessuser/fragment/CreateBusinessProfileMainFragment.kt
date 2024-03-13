package com.trudeals.ui.isolated.businessuser.fragment

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
import com.trudeals.ui.isolated.dummydata.BusinessProfileDetailMain
import com.trudeals.ui.isolated.realestateuser.adapter.StepsAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.StepsCount

class CreateBusinessProfileMainFragment : BaseFragment<AddOrEditPropertyDetailsMainBinding>() {

    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }
    private val viewPagerAdapter by lazy {
        ViewPagerFragmentAdapter(this,
            ArrayList<Fragment>().apply {
                add(AddBusinessProfileDetailsFragment().apply {
                    arguments = AddBusinessProfileDetailsFragment.createBundle(sourceScreen ?: "")
                })
                add(AddBusinessMediaDetailFragment().apply {
                    arguments = AddBusinessMediaDetailFragment.createBundleSS(sourceScreen ?: "")
                })
                add(AddBusinessSocialMediaDetailFragment().apply {
                    arguments = AddBusinessSocialMediaDetailFragment.createBundleSS(sourceScreen ?: "")
                })
                add(AddBusinessAvailabilityDetailsFragment().apply {
                    arguments = AddBusinessAvailabilityDetailsFragment.createBundleSS(sourceScreen ?: "")
                })
            }
        )
    }
    private val stepsAdapter by lazy { StepsAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddOrEditPropertyDetailsMainBinding {
        return AddOrEditPropertyDetailsMainBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setStepsRecyclerView()
        setViewPager()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setOnBackButtonClickListener {
                if (binding.viewPager.currentItem == 0) {
                    navigator.goBack()
                } else {
                    binding.viewPager.currentItem--
                }
            }
            .setToolbarTitle(getString(R.string.label_add_details))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
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
        stepsAdapter.setItems(DataUtils.setBusinessProfileSteps(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = stepsAdapter
    }

    private val fragment1Index = 0
    fun navigateToPreviousStep(steps: StepsCount) = with(binding) {
        when (steps) {
            StepsCount.STEP_ONE -> {
                viewPager.setCurrentItem(fragment1Index, true)
            }
            else -> {}
        }
    }

    fun navigateToNextStep(
        dataOnNext: BusinessProfileDetailMain,
        steps: StepsCount
    ) =
        with(binding) {
            when (steps) {
                StepsCount.STEP_TWO -> {
                    viewPager.currentItem++
                    // Pass bundle to 2nd step fragment
                    viewPagerAdapter.getFragment(viewPager.currentItem)
                        .apply {
                            arguments = AddBusinessMediaDetailFragment.createBundle(dataOnNext)
                        }
                }
                StepsCount.STEP_THREE -> {
                    viewPager.currentItem++
                    // Pass bundle to 3rd step fragment
                    viewPagerAdapter.getFragment(viewPager.currentItem)
                        .apply {
                            arguments =
                                AddBusinessSocialMediaDetailFragment.createBundle(dataOnNext)
                        }

                }
                StepsCount.STEP_FOUR -> {
                    viewPager.currentItem++
                    // Pass bundle to 4th step fragment
                    viewPagerAdapter.getFragment(viewPager.currentItem)
                        .apply {
                            arguments =
                                AddBusinessAvailabilityDetailsFragment.createBundle(dataOnNext)
                        }
                }
                else -> {}
            }
        }

    companion object {
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(sourceScreen: String) = bundleOf(SOURCE_SCREEN to sourceScreen)
    }
}