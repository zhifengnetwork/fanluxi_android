package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AddressBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressModel {
    fun requestAddress(): Observable<BaseBean<List<AddressBean>>> {
        return RetrofitManager.service.getAddressList()
            .compose(SchedulerUtils.ioToMain())
    }
}