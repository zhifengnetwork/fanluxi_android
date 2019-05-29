package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MessageInfoBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MessageInfoModel {
    fun getMessageInfo(rec_id: String): Observable<BaseBean<MessageInfoBean>> {
        return RetrofitManager.service.getMessageInfo(rec_id)
            .compose(SchedulerUtils.ioToMain())
    }
}