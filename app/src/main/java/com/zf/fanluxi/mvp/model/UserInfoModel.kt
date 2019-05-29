package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.UserInfoBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class UserInfoModel {
    fun requestUserInfo(): Observable<BaseBean<UserInfoBean>> {
        return RetrofitManager.service.getUserInfo()
            .compose(SchedulerUtils.ioToMain())
    }
}