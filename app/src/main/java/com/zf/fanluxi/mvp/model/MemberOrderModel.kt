package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MyMemberOrderBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MemberOrderModel {
    fun getMemberOrder(page: Int, num: Int, next_user_id: String): Observable<BaseBean<MyMemberOrderBean>> {
        return RetrofitManager.service.getMyMemberOrder(page, num, next_user_id).compose(SchedulerUtils.ioToMain())
    }
}