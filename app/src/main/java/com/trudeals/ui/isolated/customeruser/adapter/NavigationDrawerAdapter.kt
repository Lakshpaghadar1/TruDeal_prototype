package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemNavigationDrawerBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.NavigationDrawerOption
import com.trudeals.utils.extension.setIcon
import com.trudeals.utils.extension.toBinding

class NavigationDrawerAdapter :
    AdvanceRecycleViewAdapter<NavigationDrawerAdapter.ViewHolder, NavigationDrawerOption>() {

    inner class ViewHolder(val binding: ItemNavigationDrawerBinding) :
        BaseHolder<NavigationDrawerOption>(binding.root) {
        override fun bind(item: NavigationDrawerOption) = with(binding) {
            textViewNavigationOption.text = item.option
            textViewNavigationOption.setIcon(item.icon)

            textViewNavigationOption.setOnClickListener {
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}