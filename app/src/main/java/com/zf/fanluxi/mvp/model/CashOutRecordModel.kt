package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.CashOutBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class CashOutRecordModel {
    fun getCashOutRecordList(page: Int, num: Int): Observable<BaseBean<CashOutBean>> {
        return RetrofitManager.service.getCashOutList(page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}