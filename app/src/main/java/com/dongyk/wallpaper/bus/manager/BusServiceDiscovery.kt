package com.dongyk.wallpaper.bus.manager

import android.util.SparseArray
import com.dongyk.wallpaper.bus.base.IBusService
import com.dongyk.wallpaper.utils.ClassUtil

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
class BusServiceDiscovery :IBusServiceDiscovery{

    private val mBusServiceClassSparseArray = SparseArray<Class<IBusService>>()
    private val mBusServiceSparseArray = SparseArray<IBusService>()


    override fun <TBusService : IBusService, TBusServiceImpl : TBusService> register(busServiceClass: Class<TBusService>, busServiceImplClass: Class<TBusServiceImpl>) {

        var existBusServiceClass: Class<IBusService>? = mBusServiceClassSparseArray.get(busServiceClass.hashCode())

        existBusServiceClass ?: let {
            mBusServiceClassSparseArray.put(busServiceClass.hashCode(), busServiceImplClass as Class<IBusService>)
        }

    }

    override fun <TBusService : IBusService> unRegister(busServiceClass: Class<TBusService>) {

        mBusServiceClassSparseArray.remove(busServiceClass.hashCode())
        mBusServiceSparseArray.remove(busServiceClass.hashCode())
    }

    override fun <TBusService : IBusService> getBusService(busServiceClass: Class<TBusService>): TBusService? {

        var busService: TBusService? = mBusServiceSparseArray.get(busServiceClass.hashCode()) as TBusService?
        if (busService == null) {
            val existBusServiceClass = mBusServiceClassSparseArray.get(busServiceClass.hashCode()) as Class<TBusService>
            if (existBusServiceClass != null) {

                busService = ClassUtil.newInstance(existBusServiceClass)
                mBusServiceSparseArray.put(busServiceClass.hashCode(), busService)
            }
        }

        return busService

    }

    override fun <TBusService : IBusService> deleteBusService(busServiceClass: Class<TBusService>) {
        val busService = mBusServiceSparseArray.get(busServiceClass.hashCode()) as TBusService
        if (busService != null) {
            mBusServiceSparseArray.delete(busServiceClass.hashCode())
        }
    }

    override fun clearAll() {

        mBusServiceSparseArray.clear()
    }
}