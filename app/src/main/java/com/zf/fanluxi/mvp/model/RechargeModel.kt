package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.RechargeRecordBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class RechargeModel {
    fun getRechargeRecordList(page: Int, num: Int): Observable<BaseBean<RechargeRecordBean>> {
        return RetrofitManager.service.getRechargeList(page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}