package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.DistributeOrderBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class DistributeOrderModel {
    fun requestDistributeOrder(page: Int): Observable<BaseBean<DistributeOrderBean>> {
        return RetrofitManager.service.getDistributeOrder(page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}