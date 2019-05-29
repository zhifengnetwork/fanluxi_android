package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.*
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodsDetailModel {

    fun getSecKillDetail(id: String): Observable<BaseBean<SecKillDetailBean>> {
        return RetrofitManager.service.getSecKillDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsDetail(goods_id: String): Observable<BaseBean<GoodsDetailBean>> {
        return RetrofitManager.service.getGoodsDetail(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodEva(goodId: String, type: Int, page: Int, num: Int): Observable<BaseBean<GoodEvaBean>> {
        return RetrofitManager.service.getGoodEva(goodId, type, page, num)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestAddress(): Observable<BaseBean<List<AddressBean>>> {
        return RetrofitManager.service.getAddressList()
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsFreight(goods_id: String, region_id: String, buy_num: String): Observable<BaseBean<GoodsFreightBean>> {
        return RetrofitManager.service.getGoodsFreight(goods_id, region_id, buy_num)
            .compose(SchedulerUtils.ioToMain())
    }

    fun setCollectGoods(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setCollectGoods(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun delCollectGoods(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delCollectGoods(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun addCart(goods_id: String, goods_num: Int, item_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.addCart(goods_id, goods_num, item_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsSpec(goods_id: String): Observable<BaseBean<List<List<GoodsSpecBean>>>> {
        return RetrofitManager.service.getGoodsSpec(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getPricePic(key: String, goods_id: String): Observable<BaseBean<GoodsSpecBean>> {
        return RetrofitManager.service.requestSpecInfo(key, goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsAttr(goods_id:String):Observable<BaseBean<GoodsAttrBean>>{
        return RetrofitManager.service.getGoodsAttr(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getRecommendGoods(id: String, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getRecommendGoods(id, num)
            .compose(SchedulerUtils.ioToMain())
    }
}