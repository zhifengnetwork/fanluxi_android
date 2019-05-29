package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.GoodEvaBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodEvaModel {

    fun getGoodEva(goodId: String, type: Int, page: Int): Observable<BaseBean<GoodEvaBean>> {
        return RetrofitManager.service.getGoodEva(goodId, type, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}