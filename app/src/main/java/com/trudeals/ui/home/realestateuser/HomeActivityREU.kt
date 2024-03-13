package com.trudeals.ui.home.realestateuser

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.HomeActivityRealEstateUserBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.auth.setnewpassword.SetNewPasswordFragment
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.common.webview.WebViewFragment
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.fragment.HomeFragmentREU
import com.trudeals.ui.home.realestateuser.fragment.SubscriptionFragment
import com.trudeals.ui.isolated.customeruser.adapter.NavigationDrawerAdapter
import com.trudeals.ui.isolated.customeruser.dialog.UserDialog
import com.trudeals.ui.isolated.customeruser.fragment.ContactUsFragment
import com.trudeals.ui.isolated.customeruser.fragment.EditProfileFragment
import com.trudeals.ui.isolated.customeruser.fragment.UserProfileFragment
import com.trudeals.ui.isolated.realestateuser.fragment.MyFavListFragmentREU
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.UserType
import com.trudeals.utils.dialog.DialogUtils
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.trimmedText
import io.sentry.Sentry

class HomeActivityREU : BaseActivity(), View.OnClickListener {
    private lateinit var binding: HomeActivityRealEstateUserBinding

    private val navigationDrawerAdapter by lazy { NavigationDrawerAdapter() }

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        binding = HomeActivityRealEstateUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setWidthOfNavDrawer()
        setUser()
        setRecyclerView()
        setClickListener()
        Sentry.captureMessage(getString(R.string.app_name))

        //binding.layoutNavigationContent.constraintLayoutSubscription.isVisible(!session.isREUSubscribed)
        load(HomeFragmentREU::class.java).replace(false)

    }

    private fun setUpToolbar() = with(binding) {
        setToolbar(customToolbar)
        showHamburgerIcon(true)
            .setOnHamburgerIconClickListener {
                openOrClose(isOpen = true)
            }
    }

    private fun setWidthOfNavDrawer() = with(binding.navigationDrawer) {
        val widthOfNav = (resources.displayMetrics.widthPixels) * 0.9
        layoutParams.width = widthOfNav.toInt()
        requestLayout()
    }

    private fun setRecyclerView() =
        with(binding.layoutNavigationContent.recyclerViewNavigationMenu) {
            navigationDrawerAdapter.setItems(DataUtils.realEstateNavigationDrawerOption(), 1)
            layoutManager = LinearLayoutManager(this@HomeActivityREU, RecyclerView.VERTICAL, false)
            adapter = navigationDrawerAdapter
        }

    private fun setUser() = with(binding.layoutNavigationContent) {
        textViewSelectedUser.text = getString(R.string.label_real_estate)
        imageViewIcon.setImageResource(R.drawable.ic_real_estate)
    }

    private fun openOrClose(isOpen: Boolean) = with(binding.drawerLayout) {
        if (isOpen) {
            this.openDrawer(GravityCompat.START)
            //binding.layoutNavigationContent.constraintLayoutSubscription.isVisible(!session.isREUSubscribed)
        } else {
            this.closeDrawer(GravityCompat.START)
        }
    }

    private fun openUserSelectionDialog() = with(binding.layoutNavigationContent) {
        val userDialog = UserDialog(
            this@HomeActivityREU,
            R.style.TransparentDialog,
            constraintLayoutUserType.measuredWidth,
            constraintLayoutUserType.x,
            constraintLayoutUserType.y,
            textViewSelectedUser.trimmedText,
            isREUSubscribed = session.isREUSubscribed,
            isBUSubscribed = session.isBUSubscribed
        )
        userDialog.setOnOptionClick { selectedUser ->
            binding.layoutNavigationContent.apply {
                when (selectedUser.userType) {
                    UserType.REAL_ESTATE_USER -> {}
                    UserType.CUSTOMER_USER -> {
                        openOrClose(isOpen = false)
                        textViewSelectedUser.text = selectedUser.user
                        imageViewIcon.setImageResource(selectedUser.icon)
                        defaultUserSelection()
                        session.isCustomerUser = true
                        loadActivity(HomeActivityCU::class.java).byFinishingCurrent().start()
                    }
                    UserType.BUSINESS_USER -> {
                        if (session.isBUSubscribed) {
                            openOrClose(isOpen = false)
                            textViewSelectedUser.text = selectedUser.user
                            imageViewIcon.setImageResource(selectedUser.icon)
                            defaultUserSelection()
                            session.isBusinessOwnerUser = true
                            loadActivity(HomeActivityBU::class.java).byFinishingCurrent().start()
                        } else {
                            openOrClose(isOpen = false)
                            loadActivity(
                                IsolatedFullActivity::class.java,
                                SubscriptionFragment::class.java
                            ).addBundle(SubscriptionFragment.createBundle(selectedUser.user)).start()
                        }
                    }
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

    private fun setClickListener() =
        with(binding.layoutNavigationContent) {
            layoutHeader.imageViewClose.setOnClickListener(this@HomeActivityREU)
            layoutHeader.constraintLayoutProfileContent.setOnClickListener(this@HomeActivityREU)
            layoutHeader.editBg.setOnClickListener(this@HomeActivityREU)
            layoutHeader.textViewEdit.setOnClickListener(this@HomeActivityREU)
            buttonLogout.setOnClickListener(this@HomeActivityREU)

            constraintLayoutUserType.setOnClickListener(this@HomeActivityREU)
            constraintLayoutSubscription.setOnClickListener(this@HomeActivityREU)

            navigationDrawerAdapter.setOnItemClickPositionListener { item, _ ->
                clickOnNavigationOptions(item.onClick)
            }
        }

    override fun onClick(v: View) = with(binding.layoutNavigationContent) {
        when (v) {
            layoutHeader.imageViewClose -> {
                openOrClose(isOpen = false)
            }
            layoutHeader.constraintLayoutProfileContent -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    UserProfileFragment::class.java
                ).start()
            }
            layoutHeader.editBg, layoutHeader.textViewEdit -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    EditProfileFragment::class.java
                ).start()
            }
            constraintLayoutUserType -> {
                openUserSelectionDialog()
            }
            constraintLayoutSubscription -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    SubscriptionFragment::class.java
                ).start()
            }
            buttonLogout -> {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        DialogUtils.showAlertDialog(
            this,
            message = R.string.label_are_you_sure_you_want_to_logout,
            onPositiveButtonClick = {
                logout()
            }
        )
    }

    private fun clickOnNavigationOptions(onClick: OnClick) {
        when (onClick) {
            OnClick.CHANGE_PASSWORD -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    SetNewPasswordFragment::class.java
                ).addBundle(SetNewPasswordFragment.createBundle(HomeActivityCU::class.java.simpleName))
                    .start()
            }
            OnClick.MY_LISTING -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    MyFavListFragmentREU::class.java
                ).start()
            }
            OnClick.DELETE_ACCOUNT -> {
                showMessage("DELETE_ACCOUNT")
            }
            OnClick.RATE_APP -> {
                openOrClose(isOpen = false)
                showMessage("RATE_APP")
            }
            OnClick.SHARE_APP -> {
                openOrClose(isOpen = false)
                showMessage("SHARE_APP")
            }
            OnClick.HELP_AND_FAQ -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    WebViewFragment::class.java
                ).addBundle(
                    WebViewFragment.createBundle(
                        title = R.string.label_help_and_faq,
                        url = getString(R.string.dummy_url_webView)
                    )
                ).start()
            }
            OnClick.TERMS_AND_PRIVACY_POLICY -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    WebViewFragment::class.java
                ).addBundle(
                    WebViewFragment.createBundle(
                        title = R.string.label_terms_and_pp,
                        url = getString(R.string.dummy_url_webView)
                    )
                ).start()
            }
            OnClick.ABOUT_US -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    WebViewFragment::class.java
                ).addBundle(
                    WebViewFragment.createBundle(
                        title = R.string.label_about_us,
                        url = getString(R.string.dummy_url_webView)
                    )
                ).start()
            }
            OnClick.CONTACT_US -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    ContactUsFragment::class.java
                ).start()
            }
            else -> {}
        }
    }
}