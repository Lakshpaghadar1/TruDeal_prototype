package com.trudeals.ui.home.customeruser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.trudeals.R
import com.trudeals.databinding.CustomChipItemLocalDealsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.LocalDealsChip
import com.trudeals.utils.extension.toBinding

class LocalDealsChipAdapter : AdvanceRecycleViewAdapter<LocalDealsChipAdapter.ViewHolder, LocalDealsChip>() {

    inner class ViewHolder(private val binding: CustomChipItemLocalDealsBinding) :
        BaseHolder<LocalDealsChip>(binding.root) {

        override fun bind(item: LocalDealsChip) = with(binding) {
            imageViewIcon.setImageResource(item.icon)
            textViewTitle.text = item.title
            textViewTitle.setTextColor(ContextCompat.getColor(context, item.color))
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, item.color))
            handleChipSelection(item)

            setUpClickListener(item)
        }

        private fun handleChipSelection(item: LocalDealsChip) {
            if (item.isSelected) {
                selectedChip(item)
            } else {
                deSelectedChip()
            }
        }

        private fun selectedChip(item: LocalDealsChip) = with(binding) {
            cardView.strokeColor = ContextCompat.getColor(context, item.color)
            cardView.strokeWidth = this.root.resources.getDimension(R.dimen._1sdp).toInt()
        }

        @SuppressLint("ResourceAsColor")
        private fun deSelectedChip() = with(binding) {
            cardView.strokeColor = ContextCompat.getColor(context, android.R.color.transparent)
            cardView.strokeWidth = this.root.resources.getDimension(R.dimen.dp_0).toInt()
        }

        private fun setUpClickListener(item: LocalDealsChip) = with(binding) {
            root.setOnClickListener {
                selectSingleItem(bindingAdapterPosition) { prevSelectedItem ->
                    prevSelectedItem.isSelected = false
                }
                item.isSelected = true
                notifyItemChanged(bindingAdapterPosition)
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lastItemSelectedPos = getItemIndex(items!!.find { it.isSelected })
        return ViewHolder(parent.toBinding())
    }
}