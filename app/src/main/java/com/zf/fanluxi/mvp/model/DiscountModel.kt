package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.DiscountBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class DiscountModel {

    fun requestDisount(status: String): Observable<BaseBean<List<DiscountBean>>> {
        return RetrofitManager.service.getDiscount(status)
            .compose(SchedulerUtils.ioToMain())
    }
}