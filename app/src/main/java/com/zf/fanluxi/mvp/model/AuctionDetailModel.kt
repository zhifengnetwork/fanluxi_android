package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AuctionDetailBean
import com.zf.fanluxi.mvp.bean.AuctionPriceBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionDetailModel {
    fun getAuctionDetail(id: String): Observable<BaseBean<AuctionDetailBean>> {
        return RetrofitManager.service.getAuctionDetail(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun getAuctionPrice(id: String): Observable<BaseBean<AuctionPriceBean>> {
        return RetrofitManager.service.getAuctionPrice(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestBid(id: String, price: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestBid(id, price)
                .compose(SchedulerUtils.ioToMain())
    }

}