package com.trudeals.ui.home.realestateuser.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trudeals.ui.home.dummydata.RealEstateTabREU
import com.trudeals.ui.home.realestateuser.fragment.HomeRealEstateTabFragments

class ViewPagerFragmentAdapterREU(
    fm: Fragment, var list: ArrayList<RealEstateTabREU>
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        //managing all tabs from single fragment
        val fragment = HomeRealEstateTabFragments()
        fragment.arguments = HomeRealEstateTabFragments.createBundle(list[position].currentTabType)
        return fragment
    }
}