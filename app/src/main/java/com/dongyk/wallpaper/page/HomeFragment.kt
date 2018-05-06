package com.dongyk.wallpaper.page

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dongyk.wallpaper.R
import com.dongyk.wallpaper.adapter.HomeAdapter
import com.dongyk.wallpaper.base.Response
import com.dongyk.wallpaper.bean.BusSpecifyCategoryPic
import com.dongyk.wallpaper.bus.manager.BusServiceFactory
import com.dongyk.wallpaper.bus.viewservice.IBusSysViewService
import com.dongyk.wallpaper.net.IHttpCallBack
import com.dongyk.wallpaper.widget.DividerGridItemDecoration
import com.dongyk.wallpaper.widget.LoadMoreRecycler.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author dongyk
 * @date 2018/5/4
 * @discription
 */
class HomeFragment : Fragment() {
    companion object {
        val CID = "cid"
    }

    private val mBusViewService = BusServiceFactory.getInstance().getBusService(IBusSysViewService::class.java)
    private var mContentView: View? = null
    private var mCid: Int? = null

    private var mStart = 0
    private val mCount = 10

    private var mDatas = mutableListOf<BusSpecifyCategoryPic>()
    private val mLayoutManager by lazy { StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) }
    private val mHomeAdapter by lazy{ HomeAdapter(context!!, mDatas) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView ?: run {
            mContentView = inflater.inflate(R.layout.fragment_home, container, false)

        }

        return mContentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mCid = arguments?.getInt(CID)

        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = mHomeAdapter
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.addItemDecoration(DividerGridItemDecoration(context!!))

        recyclerView?.onLoadMoreListener = object : OnLoadMoreListener{
            override fun onLoadMore() {
                mCid?.let {
                    mStart += mCount
                    requestData(it, mStart, mCount)
                }
            }

        }
        refreshLayout.setOnRefreshListener {
            refreshUI()
        }


        refreshUI()

    }

    fun refreshUI(){
        mCid?.let {
            mStart = 0
            refreshLayout.isRefreshing = true
            requestData(it, mStart, mCount)
        }
    }

    fun requestData(cid: Int, start: Int, count: Int){
        mBusViewService?.queryWallPagerSpecifyCategory(cid, start, count, object : IHttpCallBack<List<BusSpecifyCategoryPic>> {
            override fun onSuccess(response: Response<List<BusSpecifyCategoryPic>>) {
                val data = response.data
                data?.let {
                    mDatas.addAll(data)
                }
                if (start == 0){// 第一次请求
                    refreshLayout.isRefreshing = false
                }else{ // 加载更多
                    recyclerView?.stopLoadMore()
                }
                if (data == null || data.isEmpty()){
                    recyclerView?.isCanLoadMore = false
                    Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show()
                }
                mHomeAdapter.notifyDataSetChanged()
            }
        })
    }


}