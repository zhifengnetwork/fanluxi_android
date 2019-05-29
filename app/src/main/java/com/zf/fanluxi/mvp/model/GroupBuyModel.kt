package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.GroupBuyBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupBuyModel {
    fun requestGroupBuy(type: String, page: Int): Observable<BaseBean<GroupBuyBean>> {
        return RetrofitManager.service.requestGroupBuyList(type, page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}