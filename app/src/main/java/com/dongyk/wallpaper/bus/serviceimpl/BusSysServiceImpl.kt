package com.dongyk.wallpaper.bus.serviceimpl

import com.dongyk.wallpaper.base.Constans
import com.dongyk.wallpaper.base.Response
import com.dongyk.wallpaper.bean.BusSpecifyCategoryPic
import com.dongyk.wallpaper.bean.BusWallPagerCategory
import com.dongyk.wallpaper.bean.CategoryResponse
import com.dongyk.wallpaper.bean.SpecifyCategoryResponse
import com.dongyk.wallpaper.bus.service.IBusSysService
import com.dongyk.wallpaper.net.ApiService
import com.dongyk.wallpaper.net.IHttpCallBack
import com.dongyk.wallpaper.net.NetDispatcher
import com.dongyk.wallpaper.utils.ViewUtil
import kotlinx.coroutines.experimental.Deferred
import java.math.BigDecimal

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 系统服务-具体实现
 */
class BusSysServiceImpl : IBusSysService {

    private var mQueryWallPagerAllCategory: Deferred<CategoryResponse>? = null
    private var mQueryWallPagerSpecifyCategory: Deferred<SpecifyCategoryResponse>? = null

    override fun queryWallPagerAllCategory(iHttpCallBack: IHttpCallBack<List<BusWallPagerCategory>>) {
        NetDispatcher.getResponseAndDispatch(mQueryWallPagerAllCategory, ApiService.queryWallPagerAllCategory(), iHttpCallBack){
            transformApiResponseToBusWallPagerCategory(apiResponse = it)
        }
    }

    // 将接口返回的bean转换成业务需要的bean
    private fun transformApiResponseToBusWallPagerCategory(apiResponse: CategoryResponse?): Response<List<BusWallPagerCategory>>{
        val busResponse: Response<List<BusWallPagerCategory>> = Response<List<BusWallPagerCategory>>(data = mutableListOf())
        val busList = busResponse.data as MutableList<BusWallPagerCategory>
        busResponse.code = apiResponse?.error ?: Constans.RESPONSE_FAILURE_CODE
        apiResponse?.data?.forEach {
            busList.add(BusWallPagerCategory(it.cat, it.cid))
        }
        return busResponse
    }

    override fun queryWallPagerSpecifyCategory(cid: Int, start: Int, count: Int, iHttpCallBack: IHttpCallBack<List<BusSpecifyCategoryPic>>) {
        NetDispatcher.getResponseAndDispatch(mQueryWallPagerSpecifyCategory, ApiService.queryWallPagerSpecifyCategory(cid, start, count), iHttpCallBack){
            transformApiResponseToBusSpecifyCategoryPic(apiResponse = it)
        }
    }

    private fun transformApiResponseToBusSpecifyCategoryPic(apiResponse: SpecifyCategoryResponse?): Response<List<BusSpecifyCategoryPic>>{
        val busResponse: Response<List<BusSpecifyCategoryPic>> = Response<List<BusSpecifyCategoryPic>>(data = mutableListOf())
        val busList = busResponse.data as MutableList<BusSpecifyCategoryPic>
        busResponse.code = apiResponse?.errno ?: Constans.RESPONSE_FAILURE_CODE
        busResponse.msg = apiResponse?.errmsg ?: Constans.RESPONSE_DEFAULT_MSG
        apiResponse?.data?.forEach {
            busList.add(BusSpecifyCategoryPic(it.id, dealUrlToPreUrl(it.url!!, it.resolution!!), it.img_1024_768))
        }
        return busResponse
    }

    // 计算出大图合适的宽高
    private fun dealUrlToPreUrl(url: String, widthAndHeight: String): String{
        var destUrl = url

        try {
            val screenWith = ViewUtil.getScreenWidth()
            val screenHeight = ViewUtil.getScreenHeight()

            val screenRatio = BigDecimal(screenWith).divide(BigDecimal(screenHeight), 6,BigDecimal.ROUND_HALF_DOWN).toFloat()
            val screenRatio2 = BigDecimal(screenHeight).divide(BigDecimal(screenWith), 6,BigDecimal.ROUND_HALF_DOWN).toFloat()


            val maxWidth = widthAndHeight.substring(0, widthAndHeight.indexOf("x")).toInt()
            val maxHeight = widthAndHeight.substring(widthAndHeight.indexOf("x")+1).toInt()

            var urlWidth: Int = screenWith
            var urlHeight: Int = screenHeight

            if (maxWidth < screenWith && maxHeight > screenHeight){// 宽小高足够
                urlWidth = maxWidth
                urlHeight = (maxWidth*screenRatio2).toInt()
            }else if(maxHeight < screenHeight && maxWidth > screenWith){// 高小宽足够
                urlWidth = (maxHeight*screenRatio).toInt()
                urlHeight = maxHeight
            }else if(maxWidth < screenWith || maxHeight < screenHeight){// 宽高都不够
                urlWidth = maxWidth
                urlHeight = maxHeight
            }

            destUrl = destUrl.replace("bdr/__85", "bdm/"+
                    urlWidth+ "_" + urlHeight + "_100"/* 图片质量0-100 */)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return destUrl
    }

}