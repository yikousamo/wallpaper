package com.dongyk.wallpaper.bus.service

import com.dongyk.wallpaper.bean.BusSpecifyCategoryPic
import com.dongyk.wallpaper.bean.BusWallPagerCategory
import com.dongyk.wallpaper.bus.base.IBusService
import com.dongyk.wallpaper.net.IHttpCallBack

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 系统服务-目前只是首页，现在叫"首页服务"更合适
 */
interface IBusSysService : IBusService {

    /**
     * 查询壁纸所有支持的类别
     */
    fun queryWallPagerAllCategory(iHttpCallBack: IHttpCallBack<List<BusWallPagerCategory>>)

    /**
     * 查询指定类别的壁纸
     */
    fun queryWallPagerSpecifyCategory(cid: Int, start: Int, count: Int, iHttpCallBack: IHttpCallBack<List<BusSpecifyCategoryPic>>)

}