package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.MaterialListBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialListModel {
    fun getMaterialList(cid: String, page: Int, num: Int): Observable<BaseBean<MaterialListBean>> {
        return RetrofitManager.service.getMaterialList(cid, page, num).compose(SchedulerUtils.ioToMain())
    }
}