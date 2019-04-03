package com.arsha.unsplash

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Arsha on 4/3/2019.
 */
class ProfPagerAdapter : FragmentPagerAdapter{

    constructor(manager: android.support.v4.app.FragmentManager) : super(manager)

    private val fragments : MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence? = null

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
    }
}
