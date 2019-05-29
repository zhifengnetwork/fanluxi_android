package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.ShippingBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShippingModel {
    fun requestShipping(orderId: String): Observable<BaseBean<ShippingBean>> {
        return RetrofitManager.service.requestShipping(orderId)
                .compose(SchedulerUtils.ioToMain())
    }
}