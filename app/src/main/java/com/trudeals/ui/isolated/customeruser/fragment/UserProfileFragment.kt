package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.UserProfileFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity

class UserProfileFragment : BaseFragment<UserProfileFragmentBinding>(),
    View.OnClickListener {
    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): UserProfileFragmentBinding {
        return UserProfileFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setData()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_profile))
            .build()
    }

    private fun setData() = with(binding) {
        textViewUserName.text = getString(R.string.dummy_user_name)
        textViewProfileEmail.text = getString(R.string.dummy_email)
        textViewFirstName.text = getString(R.string.dummy_first_name)
        textViewLastName.text = getString(R.string.dummy_last_name)
        textViewEmail.text = getString(R.string.dummy_email)
        textViewPhoneNumber.text = "+9".plus(" ").plus("1415245121")
        textViewAddress.text = getString(R.string.dummy_address)
    }

    private fun clickListener() = with(binding) {
        buttonEdit.setOnClickListener(this@UserProfileFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonEdit -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    EditProfileFragment::class.java
                ).start()
            }
        }
    }
}