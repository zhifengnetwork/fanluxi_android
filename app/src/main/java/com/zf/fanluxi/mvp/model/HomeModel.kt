package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.HomeBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class HomeModel {
    fun requestHome(): Observable<BaseBean<HomeBean>> {
        return RetrofitManager.service.requestHome()
                .compose(SchedulerUtils.ioToMain())
    }
}