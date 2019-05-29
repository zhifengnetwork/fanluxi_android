package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.GroupBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupModel {
    fun getGroup(page: Int): Observable<BaseBean<List<GroupBean>>> {
        return RetrofitManager.service.getGroupList(page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}