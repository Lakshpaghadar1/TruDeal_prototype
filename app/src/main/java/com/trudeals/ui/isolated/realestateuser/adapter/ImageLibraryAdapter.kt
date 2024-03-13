package com.trudeals.ui.isolated.realestateuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.ItemImageLibraryBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.ImageLibrary
import com.trudeals.utils.ClickTypeTag
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding


class ImageLibraryAdapter(private val isReupload: Boolean) :
    AdvanceRecycleViewAdapter<ImageLibraryAdapter.ViewHolder, ImageLibrary>() {

    var isLongPress: Boolean = false
    private var onItemClick: ((
        position: Int,
        item: ImageLibrary,
        clickType: ClickTypeTag,
        isReupload: Boolean
    ) -> Unit)? = null

    inner class ViewHolder(val binding: ItemImageLibraryBinding) :
        BaseHolder<ImageLibrary>(binding.root) {
        override fun bind(item: ImageLibrary): Unit = with(binding) {
            imageViewProfile.setImageResource(item.profileImage)
            textView.text = item.profileName
            checkbox.isChecked = item.isChecked

            constraintLayout.setOnClickListener {
                onItemClick?.invoke(
                    bindingAdapterPosition,
                    item,
                    ClickTypeTag.SHORT_CLICK,
                    isReupload
                )
            }
            constraintLayout.setOnLongClickListener {
                if(!isReupload) {
                    checkbox.isChecked = !checkbox.isChecked
                    onItemClick?.invoke(
                        bindingAdapterPosition,
                        item,
                        ClickTypeTag.LONG_CLICK,
                        false
                    )
                }
                true
            }

            if(!isReupload) {
                checkbox.isVisible(isLongPress)
            } else {
                checkbox.isVisible(item.isChecked)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showCheckbox(isLongPress: Boolean) {
        this.isLongPress = isLongPress
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeSelection(position: Int, isSingleSelected: Boolean) {
        for (i in items!!.indices) {
            if (i == position) {
                if (isSingleSelected) {
                    items!![position].isChecked = true
                } else {
                    items!![position].isChecked = !items!![position].isChecked
                }
            } else {
                if (isSingleSelected) {
                    items!![i].isChecked = false
                }
            }
        }
        notifyDataSetChanged()
    }

    fun setOnItemClick(onItemClick: (position: Int, item: ImageLibrary, clickType: ClickTypeTag, isReupload: Boolean) -> Unit) {
        this.onItemClick = onItemClick
    }
}