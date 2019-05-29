package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AdvertBean
import com.zf.fanluxi.mvp.bean.BonusBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class BonusModel {
    fun getBonus(): Observable<BaseBean<BonusBean>> {
        return RetrofitManager.service.getBonus().compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}