package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.ItemPropertyDetailsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.customeruser.adapter.FeaturesAdapter
import com.trudeals.ui.home.customeruser.adapter.PropertyImagesAdapter
import com.trudeals.ui.isolated.customeruser.fragment.ViewImageVideoFragment.Companion.IS_VIDEO
import com.trudeals.ui.isolated.dummydata.PropertyDetails
import com.trudeals.utils.PropertyDetailsTag
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class PropertyDetailsAdapter :
    AdvanceRecycleViewAdapter<PropertyDetailsAdapter.ViewHolder, PropertyDetails>() {

    private var onClick: ((onClick: String, item: PropertyDetails) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemPropertyDetailsBinding) :
        BaseHolder<PropertyDetails>(binding.root) {

        private val featuresAdapter by lazy { FeaturesAdapter() }
        private val propertyImagesAdapter by lazy { PropertyImagesAdapter() }

        override fun bind(item: PropertyDetails) = with(binding) {
            textViewTitle.text = item.title
            clickListeners(item)

        }

        private fun clickListeners(item: PropertyDetails) = with(binding) {
            constraintLayoutTop.setOnClickListener {
                setExpansionView(item)
            }

            imageViewArrow.setOnClickListener {
                setExpansionView(item)
            }
        }

        private fun setExpansionView(item: PropertyDetails) = with(binding) {
            imageViewArrow.isSelected = !imageViewArrow.isSelected
            when (item.tag) {
                PropertyDetailsTag.FEATURES -> {
                    constraintLayoutFeatures.isVisible(!constraintLayoutFeatures.isVisible)
                    recyclerViewFeatures.apply {
                        featuresAdapter.setItems(item.featuresList ?: arrayListOf(), 1)
                        layoutManager =
                            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                        adapter = featuresAdapter
                    }
                }
                PropertyDetailsTag.PROPERTY_AREA_AND_SPECIFICATIONS -> {
                    constraintLayoutAreaAndSpecifications.isVisible(!constraintLayoutAreaAndSpecifications.isVisible)
                    textViewAreaAndSpecs.text = item.areaAndSpecifications
                }
                PropertyDetailsTag.PROPERTY_IMAGES -> {
                    constraintLayoutImages.isVisible(!constraintLayoutImages.isVisible)
                    recyclerViewImages.apply {
                        propertyImagesAdapter.setItems(item.imagesList ?: arrayListOf(), 1)
                        layoutManager =
                            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                        adapter = propertyImagesAdapter
                    }
                }
                PropertyDetailsTag.PROPERTY_VIDEO -> {
                    constraintLayoutVideo.isVisible(!constraintLayoutVideo.isVisible)
                    shapeableImageForVideoView.setImageResource(item.imageForVideo ?:0)
                    shapeableImageForVideoView.setOnClickListener {
                        onClick?.invoke(IS_VIDEO, item)
                    }
                    imageViewPlayVideo.setOnClickListener {
                        onClick?.invoke(IS_VIDEO, item)
                    }
                }
                PropertyDetailsTag.NONE -> {
                    constraintLayoutExpanded.isVisible(!constraintLayoutExpanded.isVisible)
                }
            }
        }
    }


    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClick(onClick: (onCLick: String, item: PropertyDetails) -> Unit) {
        this.onClick = onClick
    }
}