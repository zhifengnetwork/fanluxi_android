package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.DetailRecordBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class DetailRecordModel {
    fun requestDetailRecord(page: Int): Observable<BaseBean<DetailRecordBean>> {
        return RetrofitManager.service.getDetailRecord(page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}