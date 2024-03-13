package com.trudeals.ui.home.businessuser.adapter

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemBusinessHomeRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.BusinessHomeListItem
import com.trudeals.utils.OnClick
import com.trudeals.utils.StatusType
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.hideView
import com.trudeals.utils.showView

class BusinessHomeAdapter : AdvanceRecycleViewAdapter<BusinessHomeAdapter.ViewHolder, BusinessHomeListItem>() {

    private var onClickOfView: ((item: BusinessHomeListItem, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(private val binding: ItemBusinessHomeRowBinding) :
        BaseHolder<BusinessHomeListItem>(binding.root) {

        override fun bind(item: BusinessHomeListItem): Unit = with(binding) {
            setData(item)

            //setLikeButtonVisibility(item)
            setStatus(item)
            setUpCLickListener(item)
        }

        private fun setData(item: BusinessHomeListItem) = with(binding) {
            item.image?.let { imageView.setImageResource(it) }
            textViewTitle.text = item.title
            textViewLocation.text = item.location
            textViewDescription.text = item.description
            textViewAmount.text = "$".plus(item.amount)
            imageViewHeart.isSelected = item.isLiked

        }

        /*private fun setLikeButtonVisibility(item: BusinessHomeListItem) = with(binding) {
           if (item.isFavList) showView(imageViewHeart)
            else hideView(imageViewHeart)
        }*/

        private fun setUpCLickListener(item: BusinessHomeListItem) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            imageViewHeart.setOnClickListener {
                imageViewHeart.isSelected = !imageViewHeart.isSelected
                item.isLiked = imageViewHeart.isSelected
                onClickOfView?.invoke(item, bindingAdapterPosition, OnClick.HEART)
            }
        }

        private fun setStatus(item: BusinessHomeListItem) = with(binding) {
            when (item.status) {
                StatusType.FEATURED -> {
                    hideView(layoutStatus.textViewForSale)
                    showView(layoutStatus.textViewFeatured)
                }
                StatusType.FOR_SALE -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_RENT -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_rent)
                }
                StatusType.DISCOUNT -> {
                    hideView(layoutStatus.textViewForSale)
                    showView(layoutStatus.textViewDiscount)
                }
                StatusType.FEATURED_AND_FORSALE -> {
                    showView(layoutStatus.textViewFeatured, layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_VACATION_RENT -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_vacation_rent)
                }
                StatusType.NONE -> {
                    hideView(layoutStatus.linearLayoutStatus)
                }
                else -> {}
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onClickOfView: (item: BusinessHomeListItem, position: Int, onClick: OnClick) -> Unit) {
        this.onClickOfView = onClickOfView
    }
}