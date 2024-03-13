package com.trudeals.utils.dotsindicator

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.DotsIndicatorRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.setDrawable
import com.trudeals.utils.extension.toBinding

class DotsIndicatorAdapter : AdvanceRecycleViewAdapter<DotsIndicatorAdapter.ViewHolder, Dot>() {

    private var dotWidth: Float = 0f
    private var dotHeight: Float = 0f
    private var selectedDotWidth: Float = 0f
    private var selectedDotHeight: Float = 0f

    inner class ViewHolder(private val binding: DotsIndicatorRowBinding) :
        BaseHolder<Dot>(binding.root) {

        override fun bind(item: Dot) = with(binding) {
            val layoutParams = dot.layoutParams
            if (item.isSelected) {
                dot.setDrawable(R.drawable.dot_selected)
                layoutParams.width = selectedDotWidth.toInt()
                layoutParams.height = selectedDotHeight.toInt()
                dot.layoutParams = layoutParams
            } else {
                dot.setDrawable(R.drawable.dot_un_selected)
                layoutParams.width = dotWidth.toInt()
                layoutParams.height = dotHeight.toInt()
                dot.layoutParams = layoutParams
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lastItemSelectedPos = getItemIndex(items!!.find { it.isSelected })
        return ViewHolder(parent.toBinding())
    }

    fun setDotWidth(dotWidth: Float) {
        this.dotWidth = dotWidth
    }

    fun setDotHeight(dotHeight: Float) {
        this.dotHeight = dotHeight
    }

    fun setSelectedDotWidth(selectedDotWidth: Float) {
        this.selectedDotWidth = selectedDotWidth
    }

    fun setSelectedDotHeight(selectedDotHeight: Float) {
        this.selectedDotHeight = selectedDotHeight
    }
}