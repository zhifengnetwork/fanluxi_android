package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AdvertBean
import com.zf.fanluxi.mvp.bean.SecKillListBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillListModel {
    fun getSecKillList(startTime: String, endTime: String, page: Int): Observable<BaseBean<SecKillListBean>> {
        return RetrofitManager.service.getSecKillList(startTime, endTime, page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}