package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.GoodsSpecBean
import com.zf.fanluxi.mvp.bean.SpecBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ActiveSpecModel {

    fun requestSpec(id: String): Observable<BaseBean<List<List<SpecBean>>>> {
        return RetrofitManager.service.requestGoodsSpec(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestSpecInfo(key: String, goodsId: String): Observable<BaseBean<GoodsSpecBean>> {
        return RetrofitManager.service.requestSpecInfo(key, goodsId)
                .compose(SchedulerUtils.ioToMain())
    }
}