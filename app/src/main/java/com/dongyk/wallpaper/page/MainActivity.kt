package com.dongyk.wallpaper.page

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import com.dongyk.wallpaper.R
import com.dongyk.wallpaper.adapter.MainViewPagerAdapter
import com.dongyk.wallpaper.base.BaseActivity
import com.dongyk.wallpaper.base.Response
import com.dongyk.wallpaper.bean.BusWallPagerCategory
import com.dongyk.wallpaper.bus.manager.BusServiceFactory
import com.dongyk.wallpaper.bus.viewservice.IBusSysViewService
import com.dongyk.wallpaper.net.IHttpCallBack
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 首页
 */
class MainActivity : BaseActivity() {

    private val mBusViewService = BusServiceFactory.getInstance().getBusService(IBusSysViewService::class.java)

    private val mFragmentManager: FragmentManager by lazy { supportFragmentManager }
    private var mFragments = mutableListOf<Fragment>()
    private var mTitles = mutableListOf<String>()
    private val mMainViewPagerAdapter: FragmentPagerAdapter by lazy { MainViewPagerAdapter(mFragmentManager, mFragments, mTitles) }


    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolBar?.title = getString(R.string.app_name)
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolBar)

        mBusViewService?.queryWallPagerAllCategory(object : IHttpCallBack<List<BusWallPagerCategory>>{
            override fun onSuccess(response: Response<List<BusWallPagerCategory>>) {

                val datas = response.data
                datas?.forEach {
                    mTitles.add(it.cat)
                    val fragment = HomeFragment()
                    val bundle = Bundle()
                    bundle.putInt(HomeFragment.CID, it.cid)
                    fragment.arguments = bundle
                    mFragments.add(fragment)
                }

                mainViewPager.adapter = mMainViewPagerAdapter
                tabLayout.setUpWithViewPager(mainViewPager)
            }
        })
    }

}
