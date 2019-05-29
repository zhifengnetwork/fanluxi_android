package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class HotSearchModel {
    fun requestHotSearch(): Observable<BaseBean<String>> {
        return RetrofitManager.service.requestHotSearch()
                .compose(SchedulerUtils.ioToMain())
    }
}