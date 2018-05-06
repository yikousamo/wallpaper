package com.dongyk.wallpaper.bus.viewserviceimpl

import com.dongyk.wallpaper.bean.BusSpecifyCategoryPic
import com.dongyk.wallpaper.bean.BusWallPagerCategory
import com.dongyk.wallpaper.bus.manager.BusServiceFactory
import com.dongyk.wallpaper.bus.service.IBusSysService
import com.dongyk.wallpaper.bus.viewservice.IBusSysViewService
import com.dongyk.wallpaper.net.IHttpCallBack

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 系统模块-UI相关-具体实现
 */
class BusSysViewServiceImpl : IBusSysViewService{

    override fun queryWallPagerAllCategory(iHttpCallBack: IHttpCallBack<List<BusWallPagerCategory>>) {
        val busService = BusServiceFactory.getInstance().getBusService(IBusSysService::class.java)
        busService?.queryWallPagerAllCategory(iHttpCallBack)
    }

    override fun queryWallPagerSpecifyCategory(cid: Int, start: Int, count: Int, iHttpCallBack: IHttpCallBack<List<BusSpecifyCategoryPic>>) {
        val busService = BusServiceFactory.getInstance().getBusService(IBusSysService::class.java)
        busService?.queryWallPagerSpecifyCategory(cid, start, count, iHttpCallBack)
    }


}