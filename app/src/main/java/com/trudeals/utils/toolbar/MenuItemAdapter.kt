package com.trudeals.utils.toolbar

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.trudeals.R
import com.trudeals.databinding.CustomToolbarMenuItemRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.isInVisible
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.textdecorator.TextDecorator

class MenuItemAdapter : AdvanceRecycleViewAdapter<MenuItemAdapter.ViewHolder, MenuItem>() {

    inner class ViewHolder(private val binding: CustomToolbarMenuItemRowBinding) :
        BaseHolder<MenuItem>(binding.root) {
        override fun bind(item: MenuItem) = with(binding) {
            textViewBadge.isInVisible(!item.showBadge)
            textViewBadge.text =
                if (item.badgeCount > 99) getString(R.string.label_99_plus) else item.badgeCount.toString()

            item.iconColor?.let { drawableTint ->
                imageViewMenuIcon.supportImageTintList =
                    ContextCompat.getColorStateList(context, drawableTint)
            }

            item.titleColor?.let { titleColor ->
                textViewTitle.setTextColor(getColor(titleColor))
            }

            when (item.menuItemType) {
                MenuItemType.ICON -> {
                    showView(imageViewMenuIcon)
                    hideView(textViewTitle)
                    item.icon?.let { icon ->
                        imageViewMenuIcon.setImageResource(icon)
                    }
                }

                MenuItemType.TITLE -> {
                    showView(textViewTitle)
                    hideView(imageViewMenuIcon, textViewBadge)
                    item.title?.let {
                        textViewTitle.text = item.title
                    }
                }

                MenuItemType.BOTH -> {
                    showView(imageViewMenuIcon, textViewTitle)
                    item.icon?.let { icon ->
                        imageViewMenuIcon.setImageResource(icon)
                    }
                    item.title?.let {
                        textViewTitle.text = item.title
                    }
                }
            }

            val textDecor = TextDecorator.decorate(textViewTitle, textViewTitle.trimmedText)

            item.titleTypeface?.let { titleTypeface ->
                textDecor.setTypeface(
                    ResourcesCompat.getFont(context, titleTypeface),
                    textViewTitle.trimmedText)
            }
            item.titleFontSize?.let { titleFontSize ->
                textDecor.setAbsoluteSize(titleFontSize, textViewTitle.trimmedText)
            }
            textDecor.build()

            textViewTitle.isVisible(item.isVisible)

            root.setOnClickListener {
                onClickListener?.invoke(item)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}