package com.trudeals.ui.home.realestateuser.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.trudeals.databinding.ItemRealEstateHomeChipBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.RealEstateOrBusinessHomeChip
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class RealEstateOrBusinessHomeChipAdapter :
    AdvanceRecycleViewAdapter<RealEstateOrBusinessHomeChipAdapter.ViewHolder, RealEstateOrBusinessHomeChip>() {

    inner class ViewHolder(private val binding: ItemRealEstateHomeChipBinding) :
        BaseHolder<RealEstateOrBusinessHomeChip>(binding.root) {

        override fun bind(item: RealEstateOrBusinessHomeChip) = with(binding) {
            imageViewIcon.setImageResource(item.icon)
            textViewTitle.text = item.title
            textViewTitle.setTextColor(ContextCompat.getColor(context, item.color))
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, item.color))
            cardView.strokeColor = ContextCompat.getColor(context, item.color)
            textViewBadge.text = item.badgeCount.toString()
            textViewBadge.isVisible(item.badgeCount != null)

            root.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}