package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.DistributeBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class DistributeModel {

    fun requestDistribute(): Observable<BaseBean<DistributeBean>> {
        return RetrofitManager.service.requestDistribute()
                .compose(SchedulerUtils.ioToMain())
    }
}