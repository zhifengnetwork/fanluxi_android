package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class BindZfbModel {
    fun setBindZfb(zfb_account: String, realname: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setBindZfb(zfb_account, realname)
            .compose(SchedulerUtils.ioToMain())
    }
}