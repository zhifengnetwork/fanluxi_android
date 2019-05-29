package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MaterialDetailBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialDetailModel {
    fun getMaterialDetail(id: String): Observable<BaseBean<MaterialDetailBean>> {
        return RetrofitManager.service.getMaterialDetail(id).compose(SchedulerUtils.ioToMain())
    }
}