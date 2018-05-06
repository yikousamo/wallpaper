package com.dongyk.wallpaper.bus.viewservice

import com.dongyk.wallpaper.bean.BusSpecifyCategoryPic
import com.dongyk.wallpaper.bean.BusWallPagerCategory
import com.dongyk.wallpaper.bus.base.IBusService
import com.dongyk.wallpaper.net.IHttpCallBack

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 系统模块-UI相关
 */
interface IBusSysViewService : IBusService{


    /**
     * 查询壁纸所有支持的类别
     */
    fun queryWallPagerAllCategory(iHttpCallBack: IHttpCallBack<List<BusWallPagerCategory>>)


    /**
     * 查询指定类别的壁纸
     */
    fun queryWallPagerSpecifyCategory(cid: Int, start: Int, count: Int, iHttpCallBack: IHttpCallBack<List<BusSpecifyCategoryPic>>)


}