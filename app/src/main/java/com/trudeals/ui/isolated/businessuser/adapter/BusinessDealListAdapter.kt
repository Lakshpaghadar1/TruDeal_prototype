package com.trudeals.ui.isolated.businessuser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.ItemBusinessDealBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.BusinessDeal
import com.trudeals.ui.isolated.dummydata.BusinessDealButton
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.toBinding

class BusinessDealListAdapter :
    AdvanceRecycleViewAdapter<BusinessDealListAdapter.ViewHolder, BusinessDeal>() {

    private var onCLickOfView: ((item: BusinessDealButton, parentItem: BusinessDeal, subPosition: Int, parentPosition: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(private val binding: ItemBusinessDealBinding) :
        BaseHolder<BusinessDeal>(binding.root) {
        private val businessDealButtonsAdapter by lazy { BusinessDealButtonsAdapter() }
        override fun bind(item: BusinessDeal) = with(binding) {
            textViewHeading.text = context.getString(
                R.string.label_deal_name_x_x_discount,
                item.dealName,
                item.discount
            )
            textViewStartSaleDateIs.text = item.startSaleDate
            textViewStartSaleTimeIs.text = item.startSaleTime
            textViewEndSaleDateIs.text = item.endSaleDate
            textViewEndSaleTimeIs.text = item.endSaleTime
            textViewViews.text = item.views
            textViewRedeems.text = item.redeems
            textViewShares.text = item.shares
            setRecyclerView()
            recyclerClickListener()
            root.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
        }

        private fun setRecyclerView() =
            with(binding.recyclerViewButtons) {
                businessDealButtonsAdapter.setItems(DataUtils.setDealButtons(), 1)
                layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
                adapter = businessDealButtonsAdapter
            }

        private fun recyclerClickListener() {
            businessDealButtonsAdapter.setOnItemClickPositionListener { item, position ->
                onCLickOfView?.invoke(
                    item,
                    items?.get(bindingAdapterPosition)!!,
                    position,
                    bindingAdapterPosition,
                    item.tag
                )
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onCLickOfView: (item: BusinessDealButton, parentItem: BusinessDeal, subPosition: Int, parentPosition: Int, onClick: OnClick) -> Unit) {
        this.onCLickOfView = onCLickOfView
    }
}