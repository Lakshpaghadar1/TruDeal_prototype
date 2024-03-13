package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemAddPropertyImagesBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.AddPropertyImages
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.loadImageFromServerAny
import com.trudeals.utils.extension.toBinding

class AddPropertyImagesAdapter :
    AdvanceRecycleViewAdapter<AddPropertyImagesAdapter.ViewHolder, AddPropertyImages>() {

    var onClick: ((item: AddPropertyImages, position: Int, onClick: OnClick) -> Unit)? = null

    inner class ViewHolder(val binding: ItemAddPropertyImagesBinding) :
        BaseHolder<AddPropertyImages>(binding.root) {
        override fun bind(item: AddPropertyImages) = with(binding) {
            imageViewMedia.loadImageFromServerAny(item.imageUri)

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

    fun setOnClickOfView(onClick: (item: AddPropertyImages, position: Int, onClick: OnClick) -> Unit) {
        this.onClick = onClick
    }
}