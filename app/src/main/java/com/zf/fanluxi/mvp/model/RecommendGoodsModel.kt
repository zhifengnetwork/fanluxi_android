package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.CommendBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class RecommendGoodsModel {
    fun getRecommendGoods(id: String, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getRecommendGoods(id, num)
            .compose(SchedulerUtils.ioToMain())
    }
}