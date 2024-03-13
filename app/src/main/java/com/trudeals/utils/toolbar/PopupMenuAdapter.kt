package com.trudeals.utils.toolbar

import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.trudeals.R
import com.trudeals.databinding.CustomToolbarPopupMenuRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.setIcon
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator

class PopupMenuAdapter : AdvanceRecycleViewAdapter<PopupMenuAdapter.ViewHolder, MenuItem>() {

    inner class ViewHolder(private val binding: CustomToolbarPopupMenuRowBinding) :
        BaseHolder<MenuItem>(binding.root) {

        override fun bind(item: MenuItem) = with(binding) {
            item.title?.let {
                textViewPopupMenu.text = item.title
            }

            item.icon?.let { icon ->
                textViewPopupMenu.setIcon(startIcon = icon)
            }

            item.iconColor?.let { drawableTint ->
                textViewPopupMenu.supportCompoundDrawablesTintList =
                    ContextCompat.getColorStateList(context, drawableTint)
            }

            item.titleColor?.let { titleColor ->
                textViewPopupMenu.setTextColor(getColor(titleColor))
            }

            item.titleTypeface?.let { titleTypeface ->
                TextDecorator.decorate(textViewPopupMenu, textViewPopupMenu.trimmedText)
                    .setTypeface(
                        ResourcesCompat.getFont(context, titleTypeface),
                        textViewPopupMenu.trimmedText
                    ).build()
            }

            root.setOnClickListener {
                onClickListener?.invoke(item)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}