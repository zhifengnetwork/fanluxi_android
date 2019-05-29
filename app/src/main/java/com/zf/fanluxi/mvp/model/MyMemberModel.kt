package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MyMemberBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyMemberModel {
    fun getMyMember(page: Int, num: Int, next_user_id: String): Observable<BaseBean<List<MyMemberBean>>> {
        return RetrofitManager.service.getMyMember(page, num, next_user_id).compose(SchedulerUtils.ioToMain())
    }
}