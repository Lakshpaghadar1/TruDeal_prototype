package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.trudeals.databinding.ItemAddPropertyImagesBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.loadImageFromServerAny
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.hideView
import com.trudeals.utils.imagepicker.OutPutFileAny

class AddPropertyDocAdapter :
    AdvanceRecycleViewAdapter<AddPropertyDocAdapter.ViewHolder, OutPutFileAny>() {

    var onClick: ((item: OutPutFileAny, position: Int, onClick: OnClick) -> Unit)? = null

    inner class ViewHolder(val binding: ItemAddPropertyImagesBinding) :
        BaseHolder<OutPutFileAny>(binding.root) {
        override fun bind(item: OutPutFileAny) = with(binding) {
            hideView(imageViewDragHandler)
            (constraintLayoutContent.layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                marginStart = 0
                constraintLayoutContent.layoutParams = this
            }
            imageViewMedia.loadImageFromServerAny(item.thumbImage)

            imageViewDeleteMedia.setOnClickListener {
                onClick?.invoke(item, bindingAdapterPosition, OnClick.DELETE)
            }

            imageViewReUploadMedia.setOnClickListener {
                onClick?.invoke(item, bindingAdapterPosition, OnClick.RE_UPLOAD)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onClick: (item: OutPutFileAny, position: Int, onClick: OnClick) -> Unit) {
        this.onClick = onClick
    }
}