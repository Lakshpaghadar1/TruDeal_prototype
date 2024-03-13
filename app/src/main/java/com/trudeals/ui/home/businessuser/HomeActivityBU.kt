package com.trudeals.ui.home.businessuser

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.HomeActivityBusinessUserBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.auth.setnewpassword.SetNewPasswordFragment
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.common.webview.WebViewFragment
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.home.businessuser.fragment.MyFavOrViewAllListFragmentBU
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.home.realestateuser.fragment.SubscriptionFragment
import com.trudeals.ui.isolated.businessuser.fragment.CreateBusinessProfileMainFragment
import com.trudeals.ui.isolated.customeruser.adapter.NavigationDrawerAdapter
import com.trudeals.ui.isolated.customeruser.dialog.UserDialog
import com.trudeals.ui.isolated.customeruser.fragment.ContactUsFragment
import com.trudeals.ui.isolated.customeruser.fragment.EditProfileFragment
import com.trudeals.ui.isolated.customeruser.fragment.UserProfileFragment
import com.trudeals.ui.isolated.dummydata.NavigationDrawerOption
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.UserType
import com.trudeals.utils.dialog.DialogUtils
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.trimmedText
import io.sentry.Sentry

class HomeActivityBU : BaseActivity(), View.OnClickListener {
    private lateinit var binding: HomeActivityBusinessUserBinding

    private val navigationDrawerAdapter by lazy { NavigationDrawerAdapter() }

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        binding = HomeActivityBusinessUserBinding.inflate(layoutInflater)
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

        //binding.layoutNavigationContent.constraintLayoutSubscription.isVisible(!session.isBUSubscribed)
        load(HomeFragmentBU::class.java).replace(false)

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
            navigationDrawerAdapter.setItems(DataUtils.businessNavigationDrawerOption(), 1)
            layoutManager = LinearLayoutManager(this@HomeActivityBU, RecyclerView.VERTICAL, false)
            adapter = navigationDrawerAdapter
        }

    private fun setUser() = with(binding.layoutNavigationContent) {
        textViewSelectedUser.text = getString(R.string.label_business_advertising)
        imageViewIcon.setImageResource(R.drawable.ic_business_advertising)
    }

    private fun openOrClose(isOpen: Boolean) = with(binding.drawerLayout) {
        if (isOpen) {
            this.openDrawer(GravityCompat.START)
            //binding.layoutNavigationContent.constraintLayoutSubscription.isVisible(!session.isBUSubscribed)
        } else {
            this.closeDrawer(GravityCompat.START)
        }
    }

    private fun openUserSelectionDialog() = with(binding.layoutNavigationContent) {
        val userDialog = UserDialog(
            this@HomeActivityBU,
            R.style.TransparentDialog,
            constraintLayoutUserType.measuredWidth,
            constraintLayoutUserType.x,
            constraintLayoutUserType.y,
            textViewSelectedUser.trimmedText,
            isBUSubscribed = session.isBUSubscribed,
            isREUSubscribed = session.isREUSubscribed
        )
        userDialog.setOnOptionClick { selectedUser ->
            binding.layoutNavigationContent.apply {
                when (selectedUser.userType) {
                    UserType.REAL_ESTATE_USER -> {
                        if (session.isREUSubscribed) {
                            openOrClose(isOpen = false)
                            textViewSelectedUser.text = selectedUser.user
                            imageViewIcon.setImageResource(selectedUser.icon)
                            defaultUserSelection()
                            session.isRealEstateUser = true
                            loadActivity(HomeActivityREU::class.java).byFinishingCurrent().start()
                        } else {
                            openOrClose(isOpen = false)
                            loadActivity(
                                IsolatedFullActivity::class.java,
                                SubscriptionFragment::class.java
                            ).addBundle(SubscriptionFragment.createBundle(selectedUser.user)).start()
                        }
                    }
                    UserType.CUSTOMER_USER -> {
                        openOrClose(isOpen = false)
                        textViewSelectedUser.text = selectedUser.user
                        imageViewIcon.setImageResource(selectedUser.icon)
                        defaultUserSelection()
                        session.isCustomerUser = true
                        loadActivity(HomeActivityCU::class.java).byFinishingCurrent().start()
                    }
                    UserType.BUSINESS_USER -> {}
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
            layoutHeader.imageViewClose.setOnClickListener(this@HomeActivityBU)
            layoutHeader.constraintLayoutProfileContent.setOnClickListener(this@HomeActivityBU)
            layoutHeader.editBg.setOnClickListener(this@HomeActivityBU)
            layoutHeader.textViewEdit.setOnClickListener(this@HomeActivityBU)
            buttonLogout.setOnClickListener(this@HomeActivityBU)

            constraintLayoutUserType.setOnClickListener(this@HomeActivityBU)
            constraintLayoutSubscription.setOnClickListener(this@HomeActivityBU)

            navigationDrawerAdapter.setOnItemClickPositionListener { item, _ ->
                clickOnNavigationOptions(item)
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

    private fun clickOnNavigationOptions(item: NavigationDrawerOption) {
        when (item.onClick) {
            OnClick.CHANGE_PASSWORD -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    SetNewPasswordFragment::class.java
                ).addBundle(SetNewPasswordFragment.createBundle(HomeActivityCU::class.java.simpleName))
                    .start()
            }
            OnClick.MY_BUSINESS_PROFILE -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    CreateBusinessProfileMainFragment::class.java
                ).addBundle(CreateBusinessProfileMainFragment.createBundle(HomeActivityBU::class.java.simpleName))
                    .start()
            }
            OnClick.MY_FAV_DEALS -> {
                openOrClose(isOpen = false)
                loadActivity(
                    IsolatedFullActivity::class.java,
                    MyFavOrViewAllListFragmentBU::class.java
                ).addBundle(MyFavOrViewAllListFragmentBU.createBundle(item.option)).start()
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