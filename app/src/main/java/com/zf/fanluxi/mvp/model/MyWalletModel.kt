package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MyWalletBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyWalletModel {
    fun getMyWallet(): Observable<BaseBean<MyWalletBean>> {
        return RetrofitManager.service.getMyWallet().compose(SchedulerUtils.ioToMain())
    }
}