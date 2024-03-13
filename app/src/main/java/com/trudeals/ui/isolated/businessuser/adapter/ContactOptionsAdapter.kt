package com.trudeals.ui.isolated.businessuser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemContactOptionBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.ContactOptions
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.toBinding

class ContactOptionsAdapter :
    AdvanceRecycleViewAdapter<ContactOptionsAdapter.ViewHolder, ContactOptions>() {

    private var onCLickOfView: ((item: ContactOptions, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(val binding: ItemContactOptionBinding) :
        BaseHolder<ContactOptions>(binding.root) {
        override fun bind(item: ContactOptions) = with(binding) {
            imageViewOption.setImageResource(item.option)
            textViewOptionName.text = item.optionName

            root.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, item.tag)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onCLickOfView: (item: ContactOptions, position: Int, onClick: OnClick) -> Unit) {
        this.onCLickOfView = onCLickOfView
    }
}