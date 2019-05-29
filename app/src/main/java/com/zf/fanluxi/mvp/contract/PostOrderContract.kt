package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.PostOrderBean

interface PostOrderContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setPostOrder(bean: PostOrderBean)

        fun setConfirmOrder(bean: PostOrderBean)

        fun setUserMoneyPay()

        fun setCompleteAddress()

    }

    interface Presenter : IPresenter<View> {

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
                             prom_id: String)

        fun requestGroupOrder(buy_type: String,
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
                              pay_pwd: String)

    }

}