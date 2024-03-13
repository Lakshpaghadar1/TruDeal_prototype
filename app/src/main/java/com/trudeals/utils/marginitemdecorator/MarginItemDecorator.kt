package com.trudeals.utils.marginitemdecorator

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(
    private val spaceSize: Int? = null,
    private val layoutType: LayoutType,
    private val spanCount: Int = 0,
    private var horizontalSpaceSize: Int = 0,
    private var verticalSpaceSize: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        spaceSize?.let {
            horizontalSpaceSize = spaceSize
            verticalSpaceSize = spaceSize
        }

        with(outRect) {
            if (layoutType == LayoutType.LINEAR_LAYOUT_HORIZONTAL) {
                left = horizontalSpaceSize
                right = horizontalSpaceSize

                parent.adapter?.let {
                    if (parent.getChildAdapterPosition(view) == it.itemCount - 1) {
                        right = 0
                    }
                }

                if (parent.getChildAdapterPosition(view) == 0) {
                    left = 0
                }
            } else if (layoutType == LayoutType.LINEAR_LAYOUT_VERTICAL) {
                top = verticalSpaceSize
                bottom = verticalSpaceSize

                parent.adapter?.let {
                    if (parent.getChildAdapterPosition(view) == it.itemCount - 1) {
                        bottom = 0
                    }
                }

                if (parent.getChildAdapterPosition(view) == 0) {
                    top = 0
                }
            } else if (layoutType == LayoutType.GRID_LAYOUT_VERTICAL) {
                left = horizontalSpaceSize
                right = horizontalSpaceSize
                top = verticalSpaceSize
                bottom = verticalSpaceSize

                if (parent.getChildAdapterPosition(view) < spanCount) {
                    top = 0
                }

                when (parent.getChildAdapterPosition(view) % spanCount) {
                    0 -> left = 0
                    spanCount - 1 -> right = 0
                }

                /*left = spaceSize
                right = spaceSize
                top = spaceSize
                bottom = spaceSize

                when (parent.getChildAdapterPosition(view) % spanCount) {
                    0 -> left = 0
                    spanCount - 1 -> right = 0
                }*/
            }
        }
    }

    companion object {
        fun getDimension(context: Context, dimen: Int) =
            (context.resources.getDimension(dimen) / context.resources.displayMetrics.density).toInt()
    }
}