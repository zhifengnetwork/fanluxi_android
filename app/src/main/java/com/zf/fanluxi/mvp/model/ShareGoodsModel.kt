package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.CommendBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShareGoodsModel {
    fun getShareGoods(is_distribut: Int, page: Int, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getShareGoods(is_distribut, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}