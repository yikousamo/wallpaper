package com.dongyk.wallpaper.bus.manager

import com.dongyk.wallpaper.bus.base.IBusService

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 业务接口发现
 */
class BusServiceFactory private constructor(busServiceDiscovery: IBusServiceDiscovery) : IBusServiceDiscovery {

    companion object {
         fun getInstance():BusServiceFactory{
             return Inner.instance
         }
     }

    private object Inner{
        var instance = BusServiceFactory(BusServiceDiscovery())
    }

    private var mBusServiceDiscovery: IBusServiceDiscovery = busServiceDiscovery

    override fun <TBusService : IBusService, TBusServiceImpl : TBusService> register(busServiceClass: Class<TBusService>, busServiceImplClass: Class<TBusServiceImpl>) {

        mBusServiceDiscovery.register(busServiceClass, busServiceImplClass)
    }

    override fun <TBusService : IBusService> unRegister(busServiceClass: Class<TBusService>) {

        mBusServiceDiscovery.unRegister(busServiceClass)
    }

    override fun <TBusService : IBusService> getBusService(busServiceClass: Class<TBusService>): TBusService? {
        return mBusServiceDiscovery.getBusService(busServiceClass)
    }

    override fun <TBusService : IBusService> deleteBusService(busServiceClass: Class<TBusService>) {
        mBusServiceDiscovery.deleteBusService(busServiceClass)
    }

    override fun clearAll() {

        mBusServiceDiscovery.clearAll()
    }
}