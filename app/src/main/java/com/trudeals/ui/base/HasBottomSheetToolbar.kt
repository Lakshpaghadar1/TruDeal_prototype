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

interface HasBottomSheetToolbar {

    fun setToolbar(toolbar: CustomBottomsheetToolbar)

    fun showToolbar(isVisible: Boolean): HasBottomSheetToolbar

    fun setToolbarTitle(title: String): HasBottomSheetToolbar
    fun setToolbarTitle(@StringRes title: Int): HasBottomSheetToolbar

    fun setToolbarColor(@ColorRes color: Int): HasBottomSheetToolbar
    fun setToolbarTitleColor(@ColorRes color: Int): HasBottomSheetToolbar


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
    ): HasBottomSheetToolbar

    fun setToolbarElevation(elevation: Int): HasBottomSheetToolbar

    fun showBackButton(isVisible: Boolean): HasBottomSheetToolbar
    fun setBackButtonIcon(@DrawableRes backButtonIcon: Int): HasBottomSheetToolbar

    fun build()
}
