package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.LoginBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class LoginModel {
    fun login(phone: String, pwd: String): Observable<BaseBean<LoginBean>> {
        return RetrofitManager.service.login(phone, pwd)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestWeChatLogin(code: String): Observable<BaseBean<LoginBean>> {
        return RetrofitManager.service.requestWeChatLogin(code)
            .compose(SchedulerUtils.ioToMain())
    }
}