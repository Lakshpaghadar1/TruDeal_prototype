package com.trudeals.ui.isolated.realestateuser.fragment

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.trudeals.R
import com.trudeals.databinding.ImageLibraryMainFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.home.realestateuser.adapter.RealEstateTabsAdapter
import com.trudeals.ui.isolated.realestateuser.adapter.ViewPagerFragmentAdapterBU
import com.trudeals.utils.DataUtils


class ImageLibraryMainFragment : BaseFragment<ImageLibraryMainFragmentBinding>(),
    View.OnClickListener {

    private val isReupload by lazy { arguments?.getBoolean(IS_REUPLOAD) }
    private val allowedMaxCount by lazy { arguments?.getInt(ALLOWED_MAX_COUNT) }
    private var currentFragmentSelected: ImageLibraryTabFragments? = null
    private var viewPagerAdapter: ViewPagerFragmentAdapterBU? = null
    private val tabList by lazy { DataUtils.imageLibTabData() }
    private val tabsAdapter by lazy { RealEstateTabsAdapter() }


    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ImageLibraryMainFragmentBinding {
        return ImageLibraryMainFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setViewPager()
        setTabsRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_image_library))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setViewPager() = with(binding.viewPager) {
        viewPagerAdapter = ViewPagerFragmentAdapterBU(
            this@ImageLibraryMainFragment, tabList,
            isReupload, allowedMaxCount
        )
        adapter = viewPagerAdapter
        isUserInputEnabled = true
        offscreenPageLimit = viewPagerAdapter?.itemCount!! - 1

        this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabsAdapter.changeSelection(position, true)
                currentFragmentSelected = viewPagerAdapter?.getFragment(position)
            }
        })
    }

    private fun setTabsRecyclerView() = with(binding.recyclerViewTabsList) {
        tabsAdapter.setItems(tabList, 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_4),
            RecyclerView.VERTICAL,
            false
        )
        adapter = tabsAdapter

        tabsAdapter.setOnItemClickPositionListener { _, position ->
            tabsAdapter.changeSelection(position, true)
            binding.viewPager.currentItem = position
        }
    }

    private fun clickListener() = with(binding) {
        buttonSave.setOnClickListener(this@ImageLibraryMainFragment)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonSave -> {
                /*val currentFragment = viewPagerAdapter?.getFragment(viewPager.currentItem) //NOTE: as per single type of profile if we are restricting user to select image from only business profile type tab - then this logic is fine to get list of selected images
                //right now we are not restricting user to change tab when user select images from image lib screen, need to confirm
                Log.e("TAG", "onClick: ${currentFragment?.getSelectedList()}")*/
                val intent = Intent()
                intent.putParcelableArrayListExtra(
                    LIST,
                    currentFragmentSelected?.getSelectedList()
                ) //set list
                intent.putExtra(IS_REUPLOAD, isReupload) //set boolean
                requireActivity().setResult(Activity.RESULT_OK, intent)
                requireActivity().finish()
            }
        }
    }

    override fun onBackActionPerform(): Boolean {
        return currentFragmentSelected?.clearSelectionAndGoBack() == true
    }

    companion object {
        const val LIST = "LIST"
        const val IS_REUPLOAD = "IS_REUPLOAD"
        private const val ALLOWED_MAX_COUNT = "ALLOWED_MAX_COUNT"
        fun createBundle(isReupload: Boolean, allowedMaxCount: Int) =
            bundleOf(
                IS_REUPLOAD to isReupload,
                ALLOWED_MAX_COUNT to allowedMaxCount
            )
    }
}