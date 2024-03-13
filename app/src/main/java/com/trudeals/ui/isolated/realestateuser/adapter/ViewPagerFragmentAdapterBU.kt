package com.trudeals.ui.isolated.realestateuser.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trudeals.ui.home.dummydata.RealEstateTabREU
import com.trudeals.ui.isolated.realestateuser.fragment.ImageLibraryTabFragments

class ViewPagerFragmentAdapterBU(
    fm: Fragment, var list: ArrayList<RealEstateTabREU>,
    private val isReupload: Boolean?,
    private val allowedMaxCount: Int?
) : FragmentStateAdapter(fm) {

    private val hashMap: HashMap<Int, ImageLibraryTabFragments> = HashMap()
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        //managing all tabs from single fragment
        val fragment = ImageLibraryTabFragments()
        fragment.arguments = allowedMaxCount?.let { allowedMaxCount ->
            isReupload?.let { isUpload ->
                ImageLibraryTabFragments.createBundle(
                    list[position].currentTabType,
                    isUpload,
                    allowedMaxCount
                )
            }
        }
        hashMap[position] = fragment
        return fragment
    }

    fun getFragment(position: Int): ImageLibraryTabFragments? {
        return hashMap[position]
    }
}
