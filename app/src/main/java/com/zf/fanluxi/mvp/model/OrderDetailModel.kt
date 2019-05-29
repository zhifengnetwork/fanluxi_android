package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.OrderListBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderDetailModel {
    fun getOrderDetail(id: String): Observable<BaseBean<OrderListBean>> {
        return RetrofitManager.service.getOrderDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}