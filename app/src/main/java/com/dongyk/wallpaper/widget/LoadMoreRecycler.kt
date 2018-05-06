package com.dongyk.wallpaper.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

/**
 * @author dongyk
 * @date 2018/5/4
 * @discription
 */
class LoadMoreRecycler : RecyclerView {


    private var isLoading: Boolean = false
    var isCanLoadMore: Boolean = true

    interface OnLoadMoreListener{
        fun onLoadMore()
    }

    var onLoadMoreListener : OnLoadMoreListener? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attributeset: AttributeSet?): super(context, attributeset)

    init {
        this.addOnScrollListener(object : OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onLoadMoreListener?.let {
                    if (dy > 0 && !isLoading && isCanLoadMore && getLastVisiblePosition()+1 == adapter.itemCount){
                        isLoading = true
                        onLoadMoreListener!!.onLoadMore()
                    }
                }

            }
        })
    }

    fun stopLoadMore(){
        isLoading = false
    }

    fun getLastVisiblePosition(): Int{
        val position: Int
        if (layoutManager is LinearLayoutManager) {
            position = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is GridLayoutManager) {
            position = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val layoutManager = layoutManager as StaggeredGridLayoutManager
            val lastPositions = layoutManager.findLastVisibleItemPositions(IntArray(layoutManager.spanCount))
            position = getMaxPosition(lastPositions)
        } else {
            position = layoutManager.itemCount - 1
        }
        return position
    }

    fun getMaxPosition(positions:IntArray ): Int{
        val size = positions.size
        var maxPosition = Integer.MIN_VALUE
        for (i in 0 until size) {
            maxPosition = Math.max(maxPosition, positions[i])
        }
        return maxPosition
    }

}