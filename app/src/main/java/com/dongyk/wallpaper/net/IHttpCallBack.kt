package com.dongyk.wallpaper.net

import android.widget.Toast
import com.dongyk.wallpaper.base.ContextInstance
import com.dongyk.wallpaper.base.Response

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 网络请求回调
 */
interface IHttpCallBack<BusResponse> {

    fun onSuccess(response: Response<BusResponse>)

    fun onFailure(code: String, msg: String){
        Toast.makeText(ContextInstance.getContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun onFinish(){
        // do nothing
    }

}