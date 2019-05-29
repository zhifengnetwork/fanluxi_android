package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderOperateModel {

    fun requestConfirmReceipt(orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestConfirmReceipt(orderId)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestCancelOrder(orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCancelOrder(orderId)
                .compose(SchedulerUtils.ioToMain())
    }
}