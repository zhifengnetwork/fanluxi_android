package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.CartBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class CartListModel {
    fun getCartList(page: Int): Observable<BaseBean<CartBean>> {
        return RetrofitManager.service.getCartList(page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}