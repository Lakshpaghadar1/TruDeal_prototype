package com.trudeals.ui.auth.usertype

import android.view.ViewGroup
import com.trudeals.databinding.SelectUserDescriptionRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class UserTypeDescAdapter :
    AdvanceRecycleViewAdapter<UserTypeDescAdapter.ViewHolder, String>() {

    inner class ViewHolder(private val binding: SelectUserDescriptionRowBinding) :
        BaseHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            textViewDescription.text = item
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}