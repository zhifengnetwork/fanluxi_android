package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MessageBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MessageModel {
    fun getMessage(page: Int, num: Int, type: String): Observable<BaseBean<MessageBean>> {
        return RetrofitManager.service.getMessage(page, num, type)
            .compose(SchedulerUtils.ioToMain())
    }
}