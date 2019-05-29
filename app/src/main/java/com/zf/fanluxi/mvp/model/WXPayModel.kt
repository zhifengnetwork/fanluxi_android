package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.WXPayBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class WXPayModel {


    fun requestWXPay(order_sn: String): Observable<BaseBean<WXPayBean>> {
        return RetrofitManager.service.requestWXPay(order_sn)
            .compose(SchedulerUtils.ioToMain())
    }
}