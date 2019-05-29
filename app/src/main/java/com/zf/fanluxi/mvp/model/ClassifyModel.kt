package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.ClassifyBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ClassifyModel{
    fun requestClassify(): Observable<BaseBean<List<ClassifyBean>>>{
        return RetrofitManager.service.getClassifyList()
            .compose(SchedulerUtils.ioToMain())
    }
}