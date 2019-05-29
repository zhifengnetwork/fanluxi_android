package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.SecKillTimeBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillTimeModel {

    fun getSecKillTime(): Observable<BaseBean<SecKillTimeBean>> {
        return RetrofitManager.service.getSecKillTimeList()
                .compose(SchedulerUtils.ioToMain())
    }
}