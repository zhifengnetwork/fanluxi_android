package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AuctionBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionListModel {
    fun getAuctionList(page: Int): Observable<BaseBean<AuctionBean>> {
        return RetrofitManager.service.getAuctionList(page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}