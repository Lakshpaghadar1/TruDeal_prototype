package com.trudeals.ui.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.trudeals.R
import com.trudeals.core.AppPreferences
import com.trudeals.core.AppSession
import com.trudeals.core.Session
import com.trudeals.di.HasComponent
import com.trudeals.di.Injector
import com.trudeals.di.component.ActivityComponent
import com.trudeals.di.component.DaggerActivityComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.exception.AuthenticationException
import com.trudeals.exception.ServerException
import com.trudeals.ui.auth.AuthActivity
import com.trudeals.ui.base.loader.Loader
import com.trudeals.ui.base.loader.LoadingDialog
import com.trudeals.ui.manager.*
import com.trudeals.utils.hideView
import com.trudeals.utils.imagepicker.MediaSelectHelper
import com.trudeals.utils.showView
import com.trudeals.utils.textdecorator.TextDecorator
import com.trudeals.utils.toolbar.CustomToolbar
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasComponent<ActivityComponent>, HasToolbar,
    Navigator, Loader {

    override val component: ActivityComponent
        get() = activityComponent

    private var toolbar: CustomToolbar? = null

    @Inject
    lateinit var navigationFactory: FragmentNavigationFactory

    @Inject
    lateinit var activityStarter: ActivityStarter

    @Inject
    lateinit var appSession: AppSession

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var mediaSelectHelper: MediaSelectHelper

    private lateinit var activityComponent: ActivityComponent

    private val view by lazy {
        createViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = DaggerActivityComponent.builder()
            .bindApplicationComponent(Injector.INSTANCE.applicationComponent)
            .bindActivity(this)
            .build()

        inject(activityComponent)

        super.onCreate(savedInstanceState)

        setContentView(view)
    }

    fun <F : BaseFragment<*>> getCurrentFragment(): F? {
        return if (findFragmentPlaceHolder() == 0) null else supportFragmentManager.findFragmentById(
            findFragmentPlaceHolder()
        ) as F
    }

    protected val currentVisibleFragment: Fragment
        get() = supportFragmentManager.fragments.last()

    abstract fun findFragmentPlaceHolder(): Int

    abstract fun createViewBinding(): View

    abstract fun inject(activityComponent: ActivityComponent)

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun shouldGoBack(): Boolean {
        return true
    }

    override fun onBackPressed() {
        hideKeyboard()

        val currentFragment = getCurrentFragment<BaseFragment<*>>()
        if (currentFragment == null)
            super.onBackPressed()
        else if (currentFragment.onBackActionPerform() && shouldGoBack())
            super.onBackPressed()

        // pending animation
        // overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
    }

    fun hideKeyboard() {
        // Check if no view has focus:

        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun <T : BaseFragment<*>> load(tClass: Class<T>): FragmentActionPerformer<T> {
        return navigationFactory.make(tClass)
    }

    override fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder {
        return activityStarter.make(aClass)
    }

    override fun <T : BaseFragment<*>> loadActivity(
        aClass: Class<out BaseActivity>,
        pageTClass: Class<T>
    ): ActivityBuilder {
        return activityStarter.make(aClass).setPage(pageTClass)
    }

    override fun goBack() {
        onBackPressed()
    }

    // Snackbar
    fun showMessage(message: String) {
        showSnackBar(message)
    }

    fun showMessage(@StringRes stringId: Int) {
        showSnackBar(getString(stringId))
    }

    fun showMessage(applicationException: ApplicationException) {
        showSnackBar(applicationException.message)
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.duration = 3000
        val snackView = snackbar.view
        val textView =
            snackView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.maxLines = 4

        // Used to show snackbar on top
        val params = snackView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackView.layoutParams = params

        snackView.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.colorPrimary,
                null
            )
        )
        snackbar.show()
    }

    private fun showSnackBar(message: String, viewSet: View) {
        val snackbar = Snackbar.make(viewSet, message, Snackbar.LENGTH_LONG)
        snackbar.duration = 3000
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setAction("OK") { snackbar.dismiss() }
        val snackView = snackbar.view
        val textView =
            snackView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.maxLines = 4

        // Used to show snackbar on top
        val params = snackView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackView.layoutParams = params

        snackView.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.colorPrimary,
                null
            )
        )
        snackbar.show()
    }

    fun onError(throwable: Throwable) {
        try {
            when (throwable) {
                is ServerException -> showMessage(throwable.message.toString())
                is ConnectException -> showMessage(R.string.connection_exception)
                is AuthenticationException -> {
                    logout()
                }
                is ApplicationException -> {
                    showMessage(throwable.toString())
                }
                is SocketTimeoutException -> showMessage(R.string.socket_time_out_exception)
                else -> showMessage(getString(R.string.other_exception) + throwable.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // logout
    fun logout() {
        appSession.clearSession()
        session.isLoggedOut = true
        loadActivity(AuthActivity::class.java)
            .byFinishingAll().start()
        appSession.isInitial = false
    }

   /* // Loader
    private val loader by lazy {
        LoadingDialog(this) {
            goBack()
        }
    }*/

    var customLoader: LoadingDialog? = null
    var isCustomLoaderShowing = false

    override fun showLoader() {
        toggleLoaderNew(true)
    }

    override fun hideLoader() {
        toggleLoaderNew(false)
    }

    private fun toggleLoaderNew(boolean: Boolean) {
        if (boolean) {
            if (!isCustomLoaderShowing) {
                isCustomLoaderShowing = true
                customLoader = LoadingDialog { goBack() }
                customLoader?.show(
                    supportFragmentManager, LoadingDialog::class.java.simpleName
                )
            }
        } else {
            if (isCustomLoaderShowing) {
                isCustomLoaderShowing = false
                customLoader?.dismiss()
            }
        }
    }

 /*   override fun showLoader() {
        loader.show()
    }

    override fun hideLoader() {
        loader.dismiss()
    }*/

    /* ------------- Custom Toolbar ------------- */
    // It sets the toolbar
    override fun setToolbar(toolbar: CustomToolbar) {
        this.toolbar = toolbar
    }

    // Use to show toolbar
    override fun showToolbar(isVisible: Boolean): HasToolbar {
        toolbar?.let { toolbar ->
            if (isVisible)
                showView(toolbar)
            else
                hideView(toolbar)

            toolbar.reset()
        }
        return this
    }

    override fun setToolbarTitle(title: String): HasToolbar {
        toolbar?.setToolbarTitle(title)
        return this
    }

    override fun setToolbarTitle(@StringRes title: Int): HasToolbar {
        toolbar?.setToolbarTitle(title)
        return this
    }

    override fun showBackButton(isVisible: Boolean): HasToolbar {
        toolbar?.apply {
            showBackButton(isVisible)
            setOnBackButtonClickListener {
                goBack()
            }
        }
        return this
    }

    override fun showLocationText(isVisible: Boolean): HasToolbar {
        toolbar?.apply {
            showLocationText(isVisible)
        }
        return this
    }

    override fun setBackButtonIcon(@DrawableRes backButtonIcon: Int): HasToolbar {
        toolbar?.setBackButtonIcon(backButtonIcon)
        return this
    }

    override fun setOnBackButtonClickListener(onBackButtonClick: () -> Unit): HasToolbar {
        toolbar?.setOnBackButtonClickListener(onBackButtonClick)
        return this
    }

    override fun showHamburgerIcon(isVisible: Boolean): HasToolbar {
        toolbar?.showHamburgerIcon(isVisible)
        return this
    }

    override fun setOnHamburgerIconClickListener(onMenuIconClick: () -> Unit) {
        toolbar?.setOnHamburgerIconClickListener(onMenuIconClick)
    }

    override fun setHamburgerIcon(@DrawableRes hamburgerIcon: Int): HasToolbar {
        toolbar?.setHamburgerIcon(hamburgerIcon)
        return this
    }

    override fun setToolbarColor(@ColorRes color: Int): HasToolbar {
        toolbar?.setToolbarColor(color)
        return this
    }

    override fun setToolbarTitleColor(color: Int): HasToolbar {
        toolbar?.setToolbarTitleColor(color)
        return this
    }


    override fun setToolbarLocation(text: Int): HasToolbar {
        toolbar?.setToolbarLocation(text)
        return this
    }

    override fun setToolbarLocation(text: String): HasToolbar {
        toolbar?.setToolbarLocation(text)
        return this
    }

    override fun setToolbarLocationTextColor(color: Int): HasToolbar {
        toolbar?.setToolbarLocationTextColor(color)
        return this
    }

    /**
     * This method is use to set title on the toolbar and then it will decorate the title with the help of [TextDecorator]
     *
     * Note: It has higher priority than [setToolbarTitle].
     *
     * @param title Title which you want to set on toolbar
     * @param decorateToolbarTitle It will give you [TextDecorator] instance. Call the appropriate method on it.
     * Do not call build method on it, it will be called automatically.
     * @see TextDecorator
     * */
    override fun setAndDecorateToolbarTitle(
        title: String,
        decorateToolbarTitle: (textDecorator: TextDecorator) -> TextDecorator
    ): HasToolbar {
        toolbar?.setAndDecorateToolbarTitle(title, decorateToolbarTitle)

        return this
    }

    override fun setAndDecorateToolbarLocationText(
        text: String,
        decorateToolbarLocationText: (textDecoratorLocation: TextDecorator) -> TextDecorator
    ): HasToolbar {
        toolbar?.setAndDecorateToolbarLocationText(text, decorateToolbarLocationText)

        return this
    }

    /**
     * @param menuItems Icons or title which you wanted to display.
     * */
    override fun addMenuItems(vararg menuItems: com.trudeals.utils.toolbar.MenuItem): HasToolbar {
        toolbar?.addMenuItems(*menuItems)

        return this
    }

    override fun updateMenuItem(
        menuItem: com.trudeals.utils.toolbar.MenuItem,
        predicate: (com.trudeals.utils.toolbar.MenuItem) -> Boolean
    ): HasToolbar {
        toolbar?.updateMenuItem(menuItem = menuItem, predicate = predicate)

        return this
    }

    override fun updateMenuItem(
        predicate: (com.trudeals.utils.toolbar.MenuItem) -> Boolean,
        menuItemToUpdate: (com.trudeals.utils.toolbar.MenuItem) -> Unit
    ): HasToolbar {
        toolbar?.updateMenuItem(predicate = predicate, menuItemToUpdate = menuItemToUpdate)

        return this
    }

    override fun setOnMenuItemClickListener(onMenuItemClick: (menuItem: com.trudeals.utils.toolbar.MenuItem) -> Unit) {
        toolbar?.setOnMenuItemClickListener(onMenuItemClick)
    }

    override fun setMoreOptionIcon(moreOptionIcon: Int): HasToolbar {
        toolbar?.setMoreOptionIcon(moreOptionIcon)

        return this
    }

    override fun setMaxItemAllowed(maxItemAllowed: Int): HasToolbar {
        toolbar?.setMaxItemAllowed(maxItemAllowed)

        return this
    }

    override fun setPopupMenuElevation(@DimenRes popupMenuElevation: Int): HasToolbar {
        toolbar?.setPopupMenuElevation(popupMenuElevation)

        return this
    }

    override fun setPopupMenuCornerRadius(@DimenRes popupMenuCornerRadius: Int): HasToolbar {
        toolbar?.setPopupMenuCornerRadius(popupMenuCornerRadius)

        return this
    }

    override fun setPopupMenuBackgroundColor(popupMenuBackgroundColor: Int): HasToolbar {
        toolbar?.setPopupMenuBackgroundColor(popupMenuBackgroundColor)

        return this
    }

    override fun setToolbarElevation(elevation: Int): HasToolbar {
        toolbar?.setToolbarElevation(elevation)

        return this
    }

    override fun build() {
        toolbar?.build()
    }
}
