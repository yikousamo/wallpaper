package com.dongyk.wallpaper.base

import android.app.Application
import com.dongyk.wallpaper.bus.manager.BusServiceFactory
import com.dongyk.wallpaper.bus.service.IBusSysService
import com.dongyk.wallpaper.bus.serviceimpl.BusSysServiceImpl
import com.dongyk.wallpaper.bus.viewservice.IBusPreViewService
import com.dongyk.wallpaper.bus.viewservice.IBusSysViewService
import com.dongyk.wallpaper.bus.viewserviceimpl.BusPreViewServiceImpl
import com.dongyk.wallpaper.bus.viewserviceimpl.BusSysViewServiceImpl

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
class WallPagerApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        ContextInstance.setContext(this)

        // 系统模块服务
        BusServiceFactory.getInstance().register(IBusSysViewService::class.java, BusSysViewServiceImpl::class.java)
        BusServiceFactory.getInstance().register(IBusSysService::class.java, BusSysServiceImpl::class.java)

        // 预览模块服务
        BusServiceFactory.getInstance().register(IBusPreViewService::class.java, BusPreViewServiceImpl::class.java)
    }
}