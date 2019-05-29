package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.PostOrderBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class PostOrderModel {
    fun requestPostOrder(act: Int,
                         prom_type: Int,
                         address_id: String,
                         invoice_title: String,
                         taxpayer: String,
                         invoice_desc: String,
                         coupon_id: String,
                         pay_points: String,
                         user_money: String,
                         user_note: String,
                         pay_pwd: String,
                         goods_id: String,
                         goods_num: String,
                         item_id: String,
                         action: String,
                         shop_id: String,
                         take_time: String,
                         consignee: String,
                         mobile: String,
                         prom_id: String): Observable<BaseBean<PostOrderBean>> {
        return RetrofitManager.service.requestPostOrder(act, prom_type, address_id, invoice_title, taxpayer, invoice_desc, coupon_id, pay_points, user_money, user_note, pay_pwd, goods_id, goods_num, item_id, action, shop_id, take_time, consignee, mobile, prom_id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestPostGroupOrder(buy_type: String,
                              team_id: String,
                              buy_num: String,
                              address_id: String,
                              user_money: String,
                              invoice_type: String,
                              invoice_identity: String,
                              invoice_title: String,
                              invoice_code: String,
                              user_note: String,
                              found_id: String,
                              act: Int,
                              pay_pwd: String): Observable<BaseBean<PostOrderBean>> {
        return RetrofitManager.service.requestPostGroupOrder(buy_type, team_id, buy_num, address_id, user_money, invoice_type, invoice_identity, invoice_title, invoice_code, user_note, found_id, act, pay_pwd)
                .compose(SchedulerUtils.ioToMain())
    }
}