package com.trudeals.ui.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter(var fm: Fragment, var list: ArrayList<Fragment>) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

    fun getFragment(position: Int): Fragment  =  list[position]

}