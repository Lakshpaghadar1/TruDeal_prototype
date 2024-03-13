package com.trudeals.ui.base

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.trudeals.utils.textdecorator.TextDecorator
import com.trudeals.utils.toolbar.CustomBottomsheetToolbar
import com.trudeals.utils.toolbar.CustomToolbar
import com.trudeals.utils.toolbar.MenuItem

/**
 * Created by hlink21 on 20/12/16.
 */

interface HasToolbar {

    //set custom toolbar
    fun setToolbar(toolbar: CustomToolbar)

    fun showToolbar(isVisible: Boolean): HasToolbar

    fun setToolbarTitle(title: String): HasToolbar
    fun setToolbarTitle(@StringRes title: Int): HasToolbar

    fun setToolbarColor(@ColorRes color: Int): HasToolbar
    fun setToolbarTitleColor(@ColorRes color: Int): HasToolbar

    fun showLocationText(isVisible: Boolean): HasToolbar
    fun setToolbarLocation(text: String): HasToolbar
    fun setToolbarLocation(@StringRes text: Int): HasToolbar

    fun setToolbarLocationTextColor(@ColorRes color: Int): HasToolbar

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
    fun setAndDecorateToolbarTitle(
        title: String,
        decorateToolbarTitle: (textDecorator: TextDecorator) -> TextDecorator
    ): HasToolbar
    fun setAndDecorateToolbarLocationText(
        text: String,
        decorateToolbarLocationText: (textDecoratorLocation: TextDecorator) -> TextDecorator
    ): HasToolbar

    fun setToolbarElevation(elevation: Int): HasToolbar

    fun showBackButton(isVisible: Boolean): HasToolbar
    fun setBackButtonIcon(@DrawableRes backButtonIcon: Int): HasToolbar
    fun setOnBackButtonClickListener(onBackButtonClick: () -> Unit) : HasToolbar

    fun showHamburgerIcon(isVisible: Boolean): HasToolbar
    fun setOnHamburgerIconClickListener(onMenuIconClick: () -> Unit)
    fun setHamburgerIcon(@DrawableRes hamburgerIcon: Int): HasToolbar

    /**
     * @param menuItems Icons or title which you wanted to display.
     * */
    fun addMenuItems(vararg menuItems: MenuItem): HasToolbar
    fun updateMenuItem(menuItem: MenuItem, predicate: (MenuItem) -> Boolean): HasToolbar
    fun updateMenuItem(
        predicate: (MenuItem) -> Boolean,
        menuItemToUpdate: (MenuItem) -> Unit
    ): HasToolbar

    fun setOnMenuItemClickListener(onMenuItemClick: (menuItem: MenuItem) -> Unit)

    fun setMoreOptionIcon(@DrawableRes moreOptionIcon: Int): HasToolbar

    fun setMaxItemAllowed(maxItemAllowed: Int): HasToolbar

    fun setPopupMenuElevation(@DimenRes popupMenuElevation: Int): HasToolbar
    fun setPopupMenuCornerRadius(@DimenRes popupMenuCornerRadius: Int): HasToolbar
    fun setPopupMenuBackgroundColor(@ColorRes popupMenuBackgroundColor: Int): HasToolbar

    fun build()
}
