package com.trudeals.ui.isolated.customeruser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.trudeals.R
import com.trudeals.databinding.ItemUserSelectionBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.UserSelection
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.setIcon
import com.trudeals.utils.extension.toBinding

class UserSelectionAdapter :
    AdvanceRecycleViewAdapter<UserSelectionAdapter.ViewHolder, UserSelection>() {

    private var isREUSubscribed: Boolean = false
    private var isBUSubscribed: Boolean = false

    inner class ViewHolder(val binding: ItemUserSelectionBinding) :
        BaseHolder<UserSelection>(binding.root) {
        override fun bind(item: UserSelection) = with(binding) {
            textViewUsertype.text = item.user
            imageViewIcon.setImageResource(item.icon)
            textViewUsertype.isSelected = item.isSelected
            constraintLayout.isSelected = item.isSelected

            textViewUsertype.setOnClickListener {
                constraintLayout.isSelected = !constraintLayout.isSelected
                textViewUsertype.isSelected = !textViewUsertype.isSelected

                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            handleOptionSelection(item.isSelected)
            setBgSelector()
            viewDivider.isVisible(bindingAdapterPosition != itemCount - 1)
        }

        private fun handleOptionSelection(isSelected: Boolean) {
            if (isSelected) {
                selectOption()
            } else {
                unSelectOption()
            }
        }

        private fun setBgSelector() = with(binding) {
            when (bindingAdapterPosition) {
                0 -> {
                    root.background = ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.bg_user_selection_top,
                        null
                    )
                }
                2 -> root.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.bg_user_selection_bottom,
                    null
                )
                else -> root.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.selector_bg_options_dialog,
                    null
                )
            }
        }

        private fun selectOption() = with(binding) {
            textViewUsertype.setIcon(endIcon = R.drawable.ic_right_sign)
        }

        private fun unSelectOption() = with(binding) {
            val icon = when (bindingAdapterPosition) {
                0 -> R.drawable.ic_continue
                1 -> if (isREUSubscribed) R.drawable.ic_continue else R.drawable.ic_crown
                2 -> if (isBUSubscribed) R.drawable.ic_continue else R.drawable.ic_crown
                else -> R.drawable.ic_crown
            }
            textViewUsertype.setIcon(endIcon = icon)
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lastItemSelectedPos = getItemIndex(items!!.find { it.isSelected })
        return ViewHolder(parent.toBinding())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeSelection(position: Int, isSingleSelected: Boolean) {
        for (i in items!!.indices) {
            if (i == position) {
                if (isSingleSelected) {
                    items!![position].isSelected = true
                } else {
                    items!![position].isSelected =
                        !items!![position].isSelected
                }
            } else {
                if (isSingleSelected) {
                    items!![i].isSelected = false
                }
            }
        }
        if (isSingleSelected) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(position)
        }
    }

    fun getSubscribedFlags(isREUSubscribed: Boolean, isBUSubscribed: Boolean) {
        this.isREUSubscribed = isREUSubscribed
        this.isBUSubscribed = isBUSubscribed
    }
}