package com.trudeals.utils.extension

import androidx.recyclerview.widget.RecyclerView

/**
 * This method will automatically count position start and item count and then it will notify the adapter.
 *
 * @param mainList The main list in which you want to add data.
 * @param listToBeAdded The list which you want to add.
 * */
fun RecyclerView.Adapter<*>.notifyItemRangeInserted(
    mainList: ArrayList<*>,
    listToBeAdded: ArrayList<*>
) {
    notifyItemRangeInserted(mainList.size - listToBeAdded.size, mainList.size)
}

/**
 * This method will automatically count position start and item count and then it will notify the adapter.
 *
 * @param mainList The main list in which you want to change data.
 * @param listToBeChanged The list which you want to change.
 * */
fun RecyclerView.Adapter<*>.notifyItemRangeChanged(
    mainList: ArrayList<*>,
    listToBeChanged: ArrayList<*>
) {
    notifyItemRangeChanged(mainList.size - listToBeChanged.size, mainList.size)
}

/**
 * This method will automatically count position start and item count and then it will notify the adapter.
 *
 * @param mainList The main list from which you want to remove data.
 * @param listToBeRemoved The list which you want to remove.
 * */
fun RecyclerView.Adapter<*>.notifyItemRangeRemoved(
    mainList: ArrayList<*>,
    listToBeRemoved: ArrayList<*>
) {
    notifyItemRangeRemoved(mainList.size - listToBeRemoved.size, mainList.size)
}
