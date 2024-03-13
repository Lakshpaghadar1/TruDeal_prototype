package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.ImageLibraryListingFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.isolated.dummydata.ImageLibrary
import com.trudeals.ui.isolated.realestateuser.adapter.ImageLibraryAdapter
import com.trudeals.utils.ClickTypeTag
import com.trudeals.utils.DataUtils
import com.trudeals.utils.TabType

class ImageLibraryTabFragments :
    BaseFragment<ImageLibraryListingFragmentBinding>() {

    private var checkedItemCount = 0
    private val allowedMaxCount by lazy { arguments?.getInt(ALLOWED_MAX_COUNT) }
    private val isReupload by lazy { arguments?.getBoolean(IS_REUPLOAD) }
    private val currentTab by lazy { arguments?.getParcelable<TabType>(CURRENT_TAB_TYPE) }
    private val imageLibraryAdapter by lazy { ImageLibraryAdapter(isReupload ?: false) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ImageLibraryListingFragmentBinding {
        return ImageLibraryListingFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        setRecyclerView()
        clickListener()
    }

    private fun init() {
        when (currentTab) {
            TabType.AUTOMOTIVE -> {
                //each tab data - currently set same data
                imageLibraryAdapter.setItems(DataUtils.imageLibAutomativeTab(), 1)
            }
            TabType.SALON -> {
                imageLibraryAdapter.setItems(DataUtils.imageLibSalonTab(), 1)
            }
            TabType.FOOD_AND_DRINK -> {
                imageLibraryAdapter.setItems(DataUtils.imageLibFoodTab(), 1)
            }
            TabType.HEALTH_AND_FITNESS -> {
                imageLibraryAdapter.setItems(DataUtils.imageLibHealthTab(), 1)
            }
            else -> {}
        }
    }

    override fun setUpToolbar() {}

    private fun setRecyclerView() = with(binding.recyclerViewImages) {
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_3),
            RecyclerView.VERTICAL,
            false
        )
        adapter = imageLibraryAdapter
    }

    private fun clickListener() {
        imageLibraryAdapter.setOnItemClick { position, item, clickType, isReupload ->
            when (clickType) {
                ClickTypeTag.SHORT_CLICK -> {
                    if (isReupload) {
                        imageLibraryAdapter.changeSelection(position, true)
                    } else {
                        if (item.isChecked) {
                            // Item is already checked, uncheck it
                            item.isChecked = false
                            checkedItemCount--
                        } else if (checkedItemCount < (3 - (allowedMaxCount ?: 0))) {

                            // Item is not checked and maximum count is not reached
                            item.isChecked = true
                            checkedItemCount++
                        } else {
                            // Maximum checked items reached, show a message
                            showMessage("Maximum checked items reached")
                        }
                    }
                    imageLibraryAdapter.notifyItemChanged(position)
                }

                ClickTypeTag.LONG_CLICK -> {
                    // Reset all items to unchecked state
                    imageLibraryAdapter.items?.forEach {
                        it.isChecked = false
                    }

                    // Set the long-clicked item as checked
                    item.isChecked = true
                    checkedItemCount = 1
                    imageLibraryAdapter.showCheckbox(true)
                }
            }
        }
    }

    fun clearSelectionAndGoBack(): Boolean {
        if (imageLibraryAdapter.isLongPress) {
            imageLibraryAdapter.showCheckbox(false)
        } else {
            return true
        }
        return false
    }

    fun getSelectedList(): ArrayList<ImageLibrary> {
        return ArrayList<ImageLibrary>().apply {
            imageLibraryAdapter.items?.filter { it.isChecked }?.let { addAll(it) }
        }
    }

    companion object {
        private const val CURRENT_TAB_TYPE = "CURRENT_TAB_TYPE"
        private const val IS_REUPLOAD = "IS_REUPLOAD"
        private const val ALLOWED_MAX_COUNT = "ALLOWED_MAX_COUNT"
        fun createBundle(currentTabType: TabType, isReupload: Boolean, allowedMaxCount: Int) =
            bundleOf(
                CURRENT_TAB_TYPE to currentTabType,
                IS_REUPLOAD to isReupload,
                ALLOWED_MAX_COUNT to allowedMaxCount
            )
    }
}