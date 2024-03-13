package com.trudeals.ui.auth.usertype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.UserTypeFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.auth.signin.SignInFragment
import com.trudeals.ui.base.BaseFragment
import com.trudeals.utils.DataUtils

class UserTypeFragment : BaseFragment<UserTypeFragmentBinding>(), View.OnClickListener {

    private val customerDescAdapter by lazy { UserTypeDescAdapter() }
    private val realEstateDescAdapter by lazy { UserTypeDescAdapter() }
    private val businessOwnerDescAdapter by lazy { UserTypeDescAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): UserTypeFragmentBinding {
        return UserTypeFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(false)
            .build()
    }

    private fun setRecyclerView() = with(binding) {
        recyclerViewDescCustomerUser.apply {
            customerDescAdapter.setItems(DataUtils.setCustomerDescData(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = customerDescAdapter
        }

        recyclerViewDescRealEstateUser.apply {
            realEstateDescAdapter.setItems(DataUtils.setRealEstateDescData(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = realEstateDescAdapter
        }

        recyclerViewDescBusinessOwnerUser.apply {
            businessOwnerDescAdapter.setItems(DataUtils.setBusinessOwnerDescData(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = businessOwnerDescAdapter
        }
    }

    private fun clickListeners() = with(binding) {
        buttonCustomerContinue.setOnClickListener(this@UserTypeFragment)
        buttonRealEstateContinue.setOnClickListener(this@UserTypeFragment)
        buttonBusinessOwnerContinue.setOnClickListener(this@UserTypeFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonCustomerContinue -> {
                defaultUserSelection()
                session.isCustomerUser = true
                navigator.load(SignInFragment::class.java).replace(true)
            }
            buttonRealEstateContinue -> {
                defaultUserSelection()
                session.isRealEstateUser = true
                navigator.load(SignInFragment::class.java).replace(true)
            }
            buttonBusinessOwnerContinue -> {
                defaultUserSelection()
                session.isBusinessOwnerUser = true
                navigator.load(SignInFragment::class.java).replace(true)
            }
        }
    }

    private fun defaultUserSelection() {
        session.isGuestUser = false
        session.isCustomerUser = false
        session.isRealEstateUser = false
        session.isBusinessOwnerUser = false
    }
}