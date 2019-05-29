package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.CommonBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class EvaluateModel {


    fun requestEvaluate(info: String, orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestEvaluate(info, orderId)
            .compose(SchedulerUtils.ioToMain())
    }


    fun requestUploadImg(partList: MultipartBody.Part): Observable<BaseBean<CommonBean>> {
        return RetrofitManager.service.uploadCommonImg(partList)
            .compose(SchedulerUtils.ioToMain())
    }

}