package com.dongyk.wallpaper.bus.manager

import com.dongyk.wallpaper.bus.base.IBusService

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
interface IBusServiceDiscovery {
    /**
     *
     * 注册业务接口，需要两个参数
     *
     * @param busServiceClass
     * 业务接口
     * @param busServiceImplClass
     * 业务接口具体实现
     */
    fun <TBusService : IBusService, TBusServiceImpl : TBusService> register(busServiceClass: Class<TBusService>, busServiceImplClass: Class<TBusServiceImpl>)

    /**
     *
     * 取消注册业务接口，如果该业务接口已经有实例了，那么取消注册也会把实例删除
     *
     * @param busServiceClass
     * 业务接口
     */
    fun <TBusService : IBusService> unRegister(busServiceClass: Class<TBusService>)


    /**
     *
     * 根据业务接口获取具体实现业务接口的实例
     *
     * @param busServiceClass
     * 业务接口
     * @return
     */
    fun <TBusService : IBusService> getBusService(busServiceClass: Class<TBusService>): TBusService?


    /**
     *
     * 根据业务接口删除具体实现的实例，但是不会取消注册
     *
     * @param busServiceClass
     * 业务接口
     */
    fun <TBusService : IBusService> deleteBusService(busServiceClass: Class<TBusService>)


    /**
     * 清空所有注册的实例
     */
    fun clearAll()
}