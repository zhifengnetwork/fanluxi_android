package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AccountDetailBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AccountDetailModel {
    fun getAccountDetail(type: String, page: Int, num: Int): Observable<BaseBean<AccountDetailBean>> {
        return RetrofitManager.service.getAccountInfo(type, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}