package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.GroupDetailBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupDetailModel {
    fun getGroupDetail(id: String): Observable<BaseBean<GroupDetailBean>> {
        return RetrofitManager.service.getGroupDetail(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun getGroupMember(teamId: String): Observable<BaseBean<GroupDetailBean>> {
        return RetrofitManager.service.getGroupMember(teamId)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestAddCollect(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setCollectGoods(goods_id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestDelCollect(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delCollectGoods(goods_id)
                .compose(SchedulerUtils.ioToMain())
    }

}