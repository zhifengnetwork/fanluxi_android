package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class ChangePwdModel {
    fun changePwd(passold: String, password: String, password2: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.getUpdatePwd(passold, password, password2)
            .compose(SchedulerUtils.ioToMain())
    }
}