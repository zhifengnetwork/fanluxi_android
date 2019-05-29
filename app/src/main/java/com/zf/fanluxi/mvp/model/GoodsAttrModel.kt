package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.GoodsAttrBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodsAttrModel{
    fun getGoodsAttr(goods_id:String):Observable<BaseBean<GoodsAttrBean>>{
        return RetrofitManager.service.getGoodsAttr(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }
}