package com.trudeals.ui.home.realestateuser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.SubscriptionFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.home.realestateuser.adapter.SubscriptionCardAdapter
import com.trudeals.ui.home.realestateuser.adapter.SubscriptionDescriptionPointsAdapter
import com.trudeals.ui.isolated.businessuser.fragment.CreateBusinessProfileMainFragment
import com.trudeals.utils.DataUtils

class SubscriptionFragment : BaseFragment<SubscriptionFragmentBinding>(), View.OnClickListener {

    private val subscriptionCardAdapter by lazy { SubscriptionCardAdapter() }
    private val subscriptionDescriptionPointsAdapter by lazy { SubscriptionDescriptionPointsAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SubscriptionFragmentBinding {
        return SubscriptionFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setCardRecyclerView()
        setDescriptionPointsRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_subscriptions))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setCardRecyclerView() = with(binding.recyclerViewSubOptions) {
        subscriptionCardAdapter.setItems(DataUtils.subsCard(), 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_3),
            RecyclerView.VERTICAL,
            false
        )
        adapter = subscriptionCardAdapter
    }

    private fun setDescriptionPointsRecyclerView() = with(binding.recyclerViewDescriptionPoints) {
        subscriptionDescriptionPointsAdapter.setItems(DataUtils.subsDesc(), 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_2),
            RecyclerView.VERTICAL,
            false
        )
        adapter = subscriptionDescriptionPointsAdapter
    }

    private fun clickListener() = with(binding) {
        buttonProceedToPayment.setOnClickListener(this@SubscriptionFragment)
        subscriptionCardAdapter.setOnItemClickPositionListener { _, position ->
            subscriptionCardAdapter.changeSelection(position, true)
        }
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonProceedToPayment -> {
                when (arguments?.getString(USER_TYPE)) {
                    getString(R.string.label_real_estate) -> {
                        session.isREUSubscribed = true
                        defaultUserSelection()
                        session.isRealEstateUser = true
                       // navigator.loadActivity(HomeActivityREU::class.java).byFinishingAll().start()
                    }
                    getString(R.string.label_business_advertising) -> {
                        session.isBusinessOwnerUser = true
                       /* navigator.loadActivity(
                            IsolatedFullActivity::class.java,
                            CreateBusinessProfileMainFragment::class.java
                        ).start()*/
                    }
                   //else -> showMessage("something went wrong")
                }

                if(session.isBusinessOwnerUser) {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        CreateBusinessProfileMainFragment::class.java
                    ).start()
                } else if(session.isRealEstateUser) {
                    navigator.loadActivity(HomeActivityREU::class.java).byFinishingAll().start()
                } else {
                    showMessage("something went wrong")
                }
            }
        }
    }

    private fun defaultUserSelection() {
        session.isGuestUser = false
        session.isCustomerUser = false
        session.isRealEstateUser = false
        session.isBusinessOwnerUser = false
    }

    companion object {
        private const val USER_TYPE = "USER_TYPE"

        fun createBundle(userType: String) = bundleOf(USER_TYPE to userType)
    }
}