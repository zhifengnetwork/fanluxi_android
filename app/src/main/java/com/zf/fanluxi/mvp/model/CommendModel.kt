package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AppSignBean
import com.zf.fanluxi.mvp.bean.CommendBean
import com.zf.fanluxi.mvp.bean.MeBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class CommendModel {
    fun requestCommend(type: String, page: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.requestCommend(type, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestAppSign(): Observable<BaseBean<AppSignBean>> {
        return RetrofitManager.service.requestAppSign().compose(SchedulerUtils.ioToMain())
    }

    fun requestMe(): Observable<BaseBean<MeBean>> {
        return RetrofitManager.service.requestMe()
                .compose(SchedulerUtils.ioToMain())
    }
}