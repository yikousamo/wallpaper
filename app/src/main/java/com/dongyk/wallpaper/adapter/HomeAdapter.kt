package com.dongyk.wallpaper.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dongyk.wallpaper.R
import com.dongyk.wallpaper.bean.BusSpecifyCategoryPic
import com.dongyk.wallpaper.bus.manager.BusServiceFactory
import com.dongyk.wallpaper.bus.viewservice.IBusPreViewService
import com.dongyk.wallpaper.utils.ViewUtil
import com.dongyk.wallpaper.widget.ImageLoader
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * @author dongyk
 * @date 2018/5/4
 * @discription
 */
class HomeAdapter(var context: Context, var mDatas: MutableList<BusSpecifyCategoryPic>?) : RecyclerView.Adapter<HomeAdapter.HomeVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVH {
        return HomeVH(LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent,false))
    }

    override fun onBindViewHolder(holder: HomeVH, position: Int) {
        with(holder.itemView){
            this?.homeItemImg?.let { img->
                mDatas?.get(position)?.url?.let {
                    url -> ImageLoader.loadImage(img, url)
                }
            }

            this?.setOnClickListener {
                val busPreViewService = BusServiceFactory.getInstance().getBusService(IBusPreViewService::class.java)
                val imgRect = ViewUtil.getViewLocationRect(homeItemImg)
                busPreViewService?.goPreviewActivity(context, mDatas?.get(position)?.url!!, imgRect)
            }
        }
    }

    fun dealUrl(url: String):String{
        return url.replace("bdr/__85", "bdm/"+
                ViewUtil.getScreenHeight()+
                "_" + ViewUtil.getScreenWidth() +
                "_100"/* 图片质量0-100 */)
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }


    class HomeVH(itemView: View): RecyclerView.ViewHolder(itemView)

}