package com.arsha.unsplash.Utils.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Arsha on 4/6/2019.
 */
class FollowPagerAdapter : FragmentPagerAdapter{

    constructor(manager: android.support.v4.app.FragmentManager): super(manager)

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment)
        titles.add(title)
    }

}