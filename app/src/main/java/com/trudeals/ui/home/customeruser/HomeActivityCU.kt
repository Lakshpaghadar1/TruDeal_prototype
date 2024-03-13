package com.trudeals.ui.home.customeruser

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.HomeActivityCustomerUserBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.auth.setnewpassword.SetNewPasswordFragment
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.common.webview.WebViewFragment
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.customeruser.fragment.HomeFragmentCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.home.realestateuser.fragment.SubscriptionFragment
import com.trudeals.ui.isolated.customeruser.adapter.NavigationDrawerAdapter
import com.trudeals.ui.isolated.customeruser.dialog.UserDialog
import com.trudeals.ui.isolated.customeruser.fragment.*
import com.trudeals.utils.*
import com.trudeals.utils.dialog.DialogUtils
import com.trudeals.utils.extension.trimmedText
import io.sentry.Sentry

class HomeActivityCU : BaseActivity(), View.OnClickListener {
    private lateinit var binding: HomeActivityCustomerUserBinding

    // private val dialogSelectUser by lazy { DialogSelectUser() }

    private val navigationDrawerAdapter by lazy {
        NavigationDrawerAdapter()
    }

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        binding = HomeActivityCustomerUserBinding.inflate(layoutInflater)
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

        hideView(binding.layoutNavigationContent.constraintLayoutSubscription)
        load(HomeFragmentCU::class.java).replace(false)
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
            navigationDrawerAdapter.setItems(DataUtils.customerNavigationDrawerOption(), 1)
            layoutManager = LinearLayoutManager(this@HomeActivityCU, RecyclerView.VERTICAL, false)
            adapter = navigationDrawerAdapter
        }

    private fun openOrClose(isOpen: Boolean) = with(binding.drawerLayout) {
        if (isOpen) {
            this.openDrawer(GravityCompat.START)
        } else {
            this.closeDrawer(GravityCompat.START)
        }
    }

    private fun setClickListener() =
        with(binding.layoutNavigationContent) {
            layoutHeader.imageViewClose.setOnClickListener(this@HomeActivityCU)
            layoutHeader.constraintLayoutProfileContent.setOnClickListener(this@HomeActivityCU)
            layoutHeader.editBg.setOnClickListener(this@HomeActivityCU)
            layoutHeader.textViewEdit.setOnClickListener(this@HomeActivityCU)
            buttonLogout.setOnClickListener(this@HomeActivityCU)

            constraintLayoutUserType.setOnClickListener(this@HomeActivityCU)

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

    private fun setUser() = with(binding.layoutNavigationContent) {
        textViewSelectedUser.text = getString(R.string.label_user)
        imageViewIcon.setImageResource(R.drawable.ic_customer_user)
    }

    private fun openUserSelectionDialog() = with(binding.layoutNavigationContent) {
        val userDialog = UserDialog(
            this@HomeActivityCU,
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
                            ).addBundle(SubscriptionFragment.createBundle(selectedUser.user))
                                .start()
                        }
                    }
                    UserType.CUSTOMER_USER -> {}
                    UserType.BUSINESS_USER -> {
                        if (session.isBUSubscribed) {
                            openOrClose(isOpen = false)
                            textViewSelectedUser.text = selectedUser.user
                            imageViewIcon.setImageResource(selectedUser.icon)
                            defaultUserSelection()
                            session.isBUSubscribed = true
                            loadActivity(HomeActivityBU::class.java).byFinishingCurrent().start()
                        } else {
                            openOrClose(isOpen = false)
                            loadActivity(
                                IsolatedFullActivity::class.java,
                                SubscriptionFragment::class.java
                            ).addBundle(SubscriptionFragment.createBundle(selectedUser.user))
                                .start()
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
            OnClick.MY_FAV_REAL_ESTATE -> {
                openOrClose(isOpen = false)
                loadActivity(IsolatedFullActivity::class.java, MyFavListsFragmentCU::class.java)
                    .addBundle(
                        MyFavListsFragmentCU.createBundle(
                            getString(R.string.label_my_fav_real_estate),
                            MainCategoryType.REAL_ESTATE
                        )
                    )
                    .start()
            }
            OnClick.MY_FAV_DEALS -> {
                openOrClose(isOpen = false)
                loadActivity(IsolatedFullActivity::class.java, MyFavListsFragmentCU::class.java)
                    .addBundle(
                        MyFavListsFragmentCU.createBundle(
                            getString(R.string.label_my_fav_deals),
                            MainCategoryType.LOCAL_DEALS
                        )
                    )
                    .start()
            }
            OnClick.DELETE_ACCOUNT -> {
                showMessage("DELETE_ACCOUNT")
            }
            OnClick.NEWS -> {
                openOrClose(isOpen = false)
                loadActivity(IsolatedFullActivity::class.java, NewsFragment::class.java).start()
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
            OnClick.ADVERTISE_WITH_US -> {
                openOrClose(isOpen = false)
                showMessage("ADVERTISE_WITH_US")
            }
            OnClick.TERMS_AND_PRIVACY_POLICY -> {
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