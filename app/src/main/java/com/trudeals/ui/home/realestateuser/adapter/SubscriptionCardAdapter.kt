package com.trudeals.ui.home.realestateuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemSubscriptionCardBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.SubscriptionCard
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.hideView
import com.trudeals.utils.showView

class SubscriptionCardAdapter :
    AdvanceRecycleViewAdapter<SubscriptionCardAdapter.ViewHolder, SubscriptionCard>() {

    inner class ViewHolder(val binding: ItemSubscriptionCardBinding) :
        BaseHolder<SubscriptionCard>(binding.root) {
        override fun bind(item: SubscriptionCard) = with(binding) {
            textViewSubscriptionType.text = item.title
            textViewPrice.text = context.getString(R.string.label_dollar_space_x, item.price)
            textViewTimePeriod.text = item.timePeriod

            if (item.isSelected) {
                cardView.strokeWidth = this.root.resources.getDimension(R.dimen._1sdp).toInt()
                showView(imageViewSelected)
            }
            else {
                cardView.strokeWidth = this.root.resources.getDimension(R.dimen.dp_0).toInt()
                hideView(imageViewSelected)
            }

            root.setOnClickListener {
                item.isSelected = !item.isSelected
                onClickPositionListener?.invoke(item, bindingAdapterPosition)

            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
}