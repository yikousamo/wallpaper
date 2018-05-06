package com.dongyk.wallpaper.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author dongyk
 * @date 2018/5/4
 * @discription
 */
class MainViewPagerAdapter constructor(
        fragmentManager: FragmentManager,
        private var fragments: MutableList<Fragment>,
        private var titles: MutableList<String>?) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.get(position)
    }
}