package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.ChangeUserBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class UpdateUserInfoModel {

    fun uploadHead(head: MultipartBody.Part): Observable<BaseBean<String>> {
        return RetrofitManager.service.uploadMemberIcon(head)
            .compose(SchedulerUtils.ioToMain())
    }

    fun changeUserInfo(
        nickname: String,
        mobile: String,
        sex: String,
        birthyear: String,
        birthmonth: String,
        birthday: String
    ): Observable<BaseBean<ChangeUserBean>> {
        return RetrofitManager.service.updateUserInfo(nickname, mobile, sex, birthyear, birthmonth, birthday)
            .compose(SchedulerUtils.ioToMain())
    }

}
