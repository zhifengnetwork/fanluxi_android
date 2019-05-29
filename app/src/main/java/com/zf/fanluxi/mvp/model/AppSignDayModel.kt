package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AppSignBean
import com.zf.fanluxi.mvp.bean.AppSignDayBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AppSignDayModel {
    fun requestAppSign(): Observable<BaseBean<AppSignBean>> {
        return RetrofitManager.service.requestAppSign().compose(SchedulerUtils.ioToMain())
    }
    fun getAppSignDay(): Observable<BaseBean<AppSignDayBean>> {
        return RetrofitManager.service.getSignDay().compose(SchedulerUtils.ioToMain())
    }
}