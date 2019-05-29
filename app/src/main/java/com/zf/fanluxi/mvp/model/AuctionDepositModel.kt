package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.WXPayBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionDepositModel {


    fun requestDeposit(id: String): Observable<BaseBean<WXPayBean>> {
        return RetrofitManager.service.requestDeposit(id)
                .compose(SchedulerUtils.ioToMain())
    }
}