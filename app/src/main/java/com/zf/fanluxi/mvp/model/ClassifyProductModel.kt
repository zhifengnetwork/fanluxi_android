package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AdvertBean
import com.zf.fanluxi.mvp.bean.ClassifyProductBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ClassifyProductModel {
    fun requestClassifyProduct(cat_id: String): Observable<BaseBean<List<ClassifyProductBean>>> {
        return RetrofitManager.service.getClassifyProduct(cat_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}