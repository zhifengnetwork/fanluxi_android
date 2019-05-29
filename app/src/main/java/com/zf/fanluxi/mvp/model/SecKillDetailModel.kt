package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.SecKillDetailBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillDetailModel {
    fun getSecKillDetail(id: String): Observable<BaseBean<SecKillDetailBean>> {
        return RetrofitManager.service.getSecKillDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}