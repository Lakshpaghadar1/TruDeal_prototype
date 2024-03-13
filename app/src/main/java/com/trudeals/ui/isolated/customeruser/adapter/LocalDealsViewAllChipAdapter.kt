package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.trudeals.databinding.CustomChipItemViewAllLocalDealsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.LocalDealsChip
import com.trudeals.utils.extension.toBinding

class LocalDealsViewAllChipAdapter : AdvanceRecycleViewAdapter<LocalDealsViewAllChipAdapter.ViewHolder, LocalDealsChip>() {

    inner class ViewHolder(private val binding: CustomChipItemViewAllLocalDealsBinding) :
        BaseHolder<LocalDealsChip>(binding.root) {

        override fun bind(item: LocalDealsChip) = with(binding) {
            imageViewIcon.setImageResource(item.icon)
            textViewTitle.text = item.title
            textViewTitle.setTextColor(ContextCompat.getColor(context, item.color))
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, item.color))
            cardView.strokeColor = ContextCompat.getColor(context, item.color)

            root.setOnClickListener {
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}