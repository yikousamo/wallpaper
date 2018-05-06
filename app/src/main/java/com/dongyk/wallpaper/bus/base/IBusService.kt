package com.dongyk.wallpaper.bus.base

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 业务服务总接口
 */
interface IBusService {

    /**
     * 服务是否可用-默认可用
     */
    fun isEnable(): Boolean{
        return true
    }

    /**
     * 服务描述
     */
    fun getDesc(): String{
        return this::javaClass.name
    }

}