package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.ShareBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShareModel {

    fun requestShare(): Observable<BaseBean<ShareBean>> {
        return RetrofitManager.service.requestShare()
            .compose(SchedulerUtils.ioToMain())
    }
}