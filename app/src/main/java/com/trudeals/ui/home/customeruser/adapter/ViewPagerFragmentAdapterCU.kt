package com.trudeals.ui.home.customeruser.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trudeals.ui.home.dummydata.RealEstateTabCU
import com.trudeals.ui.home.customeruser.fragment.HomeRealEstateAndLocalDealsTabFragments

class ViewPagerFragmentAdapterCU(
    fm: Fragment, var list: ArrayList<RealEstateTabCU>
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        //managing all tabs from single fragment
        val fragment = HomeRealEstateAndLocalDealsTabFragments()
        fragment.arguments = HomeRealEstateAndLocalDealsTabFragments.createBundle(
            list[position].currentTabType,
            list[position].subCategoryType,
            list[position].mainCategoryType
        )
        return fragment
    }
}