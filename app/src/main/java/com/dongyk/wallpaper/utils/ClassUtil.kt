package com.dongyk.wallpaper.utils

import java.lang.reflect.InvocationTargetException

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
object ClassUtil {

    fun <T> newInstance(clazz: Class<T>): T? {

        var instance: T? = null
        val cs = clazz.declaredConstructors
        try {

            instance = cs[0].newInstance() as T

        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: Exception){
            e.printStackTrace()
        }



        return instance
    }
}