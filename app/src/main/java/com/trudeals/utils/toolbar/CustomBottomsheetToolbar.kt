package com.trudeals.utils.toolbar

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import com.trudeals.R
import com.trudeals.databinding.CustomBottomsheetToolbarBinding
import com.trudeals.utils.extension.dpToPx
import com.trudeals.utils.extension.isInVisible
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.textdecorator.TextDecorator

class CustomBottomsheetToolbar(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private val binding: CustomBottomsheetToolbarBinding =
        CustomBottomsheetToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    /* --------------- Attributes --------------- */
    private val typedArray by lazy {
        getContext().obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
    }

    private var title = typedArray.getString(R.styleable.CustomToolbar_title)

    private var showBackButton =
        typedArray.getBoolean(R.styleable.CustomToolbar_showBackButton, false)
    private var backButtonIcon =
        typedArray.getResourceId(R.styleable.CustomToolbar_backButtonIcon, DEFAULT_BACK_BUTTON_ICON)

    private var toolbarColor = typedArray.getResourceId(
        R.styleable.CustomToolbar_toolbarColor,
        DEFAULT_TOOLBAR_COLOR
    )

    private var toolbarTitleColor =
        typedArray.getResourceId(
            R.styleable.CustomToolbar_toolbarTitleColor,
            DEFAULT_TOOLBAR_TITLE_COLOR
        )

    private var toolbarLocationTextColor =
        typedArray.getResourceId(
            R.styleable.CustomToolbar_toolbarLocationTextColor,
            DEFAULT_TOOLBAR_LOCATION_TEXT_COLOR
        )

    private var textDecorator: TextDecorator? = null
    private var textDecoratorLocation: TextDecorator? = null

    init {
        typedArray.recycle()
    }

    // Handling of back button
    private fun showBackButton() = with(binding) {
        imageViewBackButton.setImageResource(backButtonIcon)
        imageViewBackButton.isInVisible(!showBackButton)
    }

    fun showBackButton(showBackButton: Boolean): CustomBottomsheetToolbar {
        this.showBackButton = showBackButton

        return this
    }

    fun setBackButtonIcon(@DrawableRes backButtonIcon: Int): CustomBottomsheetToolbar {
        this.backButtonIcon = backButtonIcon

        return this
    }

    fun setOnBackButtonClickListener(onBackButtonClick: () -> Unit) = with(binding) {
        if (showBackButton) {
            imageViewBackButton.setOnClickListener {
                onBackButtonClick()
            }
        }
    }

       // Handling of Toolbar title
    private fun handleToolbarTitle() = with(binding) {
        title?.let {
            showView(textViewToolbarTitle)
            textViewToolbarTitle.text = title
        } ?: run {
            hideView(textViewToolbarTitle)
        }
    }

    /**
     * Use to set the toolbar title
     * @param title Pass the title you want to display.
     *
     * */
    fun setToolbarTitle(title: String): CustomBottomsheetToolbar {
        this.title = title

        return this
    }

    /**
     * Use to set the toolbar title
     * @param title Pass the StringRes which you want to display as title.
     * */
    fun setToolbarTitle(@StringRes title: Int): CustomBottomsheetToolbar {
        this.title = context.getString(title)

        return this
    }

    // Toolbar color
    private fun handleToolbarColor() = with(binding) {
        toolbar.setBackgroundColor(resources.getColor(toolbarColor, null))
    }

    fun setToolbarColor(@ColorRes toolbarColor: Int): CustomBottomsheetToolbar {
        this.toolbarColor = toolbarColor

        return this
    }

    // Toolbar title color
    private fun handleToolbarTitleColor() = with(binding) {
        textViewToolbarTitle.setTextColor(
            ResourcesCompat.getColor(
                resources,
                toolbarTitleColor,
                null
            )
        )
    }

    fun setToolbarTitleColor(@ColorRes toolbarTitleColor: Int): CustomBottomsheetToolbar {
        this.toolbarTitleColor = toolbarTitleColor

        return this
    }

    fun setToolbarLocationTextColor(@ColorRes toolbarLocationTextColor: Int): CustomBottomsheetToolbar {
        this.toolbarLocationTextColor = toolbarLocationTextColor

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
     *
     * @see TextDecorator
     * */
    fun setAndDecorateToolbarTitle(
        title: String,
        decorateToolbarTitle: (textDecorator: TextDecorator) -> TextDecorator
    ): CustomBottomsheetToolbar {
        this.textDecorator = decorateToolbarTitle(
            TextDecorator.decorate(
                binding.textViewToolbarTitle,
                title
            )
        )

        return this
    }

    fun setToolbarElevation(elevation: Int): CustomBottomsheetToolbar {
        this.elevation = resources.dpToPx(elevation).toFloat()

        return this
    }

    // It set the padding horizontal on the toolbar title based on available space.
    private fun setPaddingOnToolbarTitle() = with(binding) {
        post {
            val padding = resources.dpToPx(R.dimen._10sdp)
            textViewToolbarTitle.updatePadding(
                left = padding,
                right = padding
            )
        }
    }


    fun build() {
        showBackButton()
        handleToolbarTitle()
        handleToolbarColor()
        handleToolbarTitleColor()
        setPaddingOnToolbarTitle()
        textDecorator?.build()
        textDecoratorLocation?.build()
    }

    fun reset() {
        setToolbarTitle("")
        setToolbarColor(DEFAULT_TOOLBAR_COLOR)
        setToolbarTitleColor(DEFAULT_TOOLBAR_TITLE_COLOR)
        setToolbarLocationTextColor((DEFAULT_TOOLBAR_LOCATION_TEXT_COLOR))
        showBackButton(false)
        showBackButton()
        textDecorator = null
        textDecoratorLocation = null
        elevation = 0f
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        outlineProvider = CustomOutline(width, height)
    }

    private class CustomOutline(var width: Int, var height: Int) : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline) {
            outline.setRect(0, 0, width, height)
        }
    }

    companion object {
        const val DEFAULT_TOOLBAR_COLOR = R.color.white
        const val DEFAULT_TOOLBAR_TITLE_COLOR = R.color.black
        const val DEFAULT_TOOLBAR_LOCATION_TEXT_COLOR = R.color.black
        const val DEFAULT_BACK_BUTTON_ICON = R.drawable.ic_back
    }
}
