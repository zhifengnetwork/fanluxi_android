package com.zf.fanluxi.mvp.model


import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MaterialClassifyBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialClassifyModel {

    fun getMaterialClassify(): Observable<BaseBean<MaterialClassifyBean>> {
        return RetrofitManager.service.getMaterialClassify().compose(SchedulerUtils.ioToMain())
    }
}