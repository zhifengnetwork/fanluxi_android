package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.OrderListBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderListModel {
    fun requestOrderList(type: String, page: Int,keyWord:String): Observable<BaseBean<List<OrderListBean>>> {
        return RetrofitManager.service.getOrderList(type, page, UriConstant.PER_PAGE,keyWord)
            .compose(SchedulerUtils.ioToMain())
    }
}