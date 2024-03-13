package com.trudeals.ui.base.adavancedrecyclerview

import android.view.View

interface OnRecycleItemClick<T> {
    fun onClick(t: T?, view: View)
}
