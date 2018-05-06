package com.dongyk.wallpaper.net

import com.dongyk.wallpaper.base.Constans
import com.dongyk.wallpaper.base.Response
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 请求网络内容，并分发到相应回调
 */
object NetDispatcher{

    fun <ApiResponse, BusResponse> getResponseAndDispatch(deferred: Deferred<ApiResponse>?,
                                         netRequest: Deferred<ApiResponse>,
                                         iHttpCallBack: IHttpCallBack<BusResponse>,
                                         transform: (api: ApiResponse)-> Response<BusResponse>){

        async (UI){
            try {

                var deferredReal = deferred
                if (deferredReal?.isActive == true){
                    deferredReal.cancel()
                }

                deferredReal = netRequest
                // 协程请求网络
                val apiResponse = deferredReal.await()
                // 业务转换
                val busResponse = transform(apiResponse)
                // 业务分发
                if (!interceptResponseCode(busResponse)){
                    iHttpCallBack.onSuccess(busResponse)
                }else{
                    iHttpCallBack.onFailure(busResponse.code, busResponse.msg)
                }
            }catch (e: Exception){
                e.printStackTrace()
                iHttpCallBack.onFailure(Constans.RESPONSE_FAILURE_CODE, Constans.RESPONSE_NULL)
            }finally {
                iHttpCallBack.onFinish()
            }
        }
    }

    /**
     * 统一效验各种错误码
     */
    fun <BusResponse> interceptResponseCode(busResponse: Response<BusResponse>): Boolean{
        return Constans.RESPONSE_SUCCESS_CODE != (busResponse.code)
    }

}