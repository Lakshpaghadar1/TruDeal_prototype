package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.trudeals.R
import com.trudeals.databinding.ItemNewsRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.NewsList
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator

class NewsAdapter : AdvanceRecycleViewAdapter<NewsAdapter.ViewHolder, NewsList>() {

    inner class ViewHolder(val binding: ItemNewsRowBinding) : BaseHolder<NewsList>(binding.root) {
        override fun bind(item: NewsList) = with(binding) {
            setData(item)
            setTypeFace()
            clickListener(item)
        }

        private fun setData(item: NewsList) = with(binding) {
            textViewTitle.text = item.title
            textViewPostedOn.text = context.getString(R.string.label_x_posted_on, item.postedOn)
            textViewDescription.text = item.description
        }

        private fun setTypeFace() = with(binding) {
            TextDecorator.decorate(textViewPostedOn, textViewPostedOn.trimmedText).setTypeface(
                ResourcesCompat.getFont(textViewPostedOn.context, R.font.cerebri_sans_regular),
                getString(R.string.label_posted_on)
            ).build()
        }

        private fun clickListener(item: NewsList) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}