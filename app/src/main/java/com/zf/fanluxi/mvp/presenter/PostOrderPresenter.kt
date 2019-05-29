package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.PostOrderContract
import com.zf.fanluxi.mvp.model.PostOrderModel
import com.zf.fanluxi.net.exception.ExceptionHandle
import com.zf.fanluxi.utils.LogUtils

/**
 * 结算订单 提交订单
 */
class PostOrderPresenter : BasePresenter<PostOrderContract.View>(), PostOrderContract.Presenter {

    private val model: PostOrderModel by lazy { PostOrderModel() }

    /**
     * 拼团订单
     */
    override fun requestGroupOrder(
            buy_type: String,
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
            pay_pwd: String
    ) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestPostGroupOrder(
                buy_type,
                team_id,
                buy_num,
                address_id,
                user_money,
                invoice_type,
                invoice_identity,
                invoice_title,
                invoice_code,
                user_note,
                found_id,
                act,
                pay_pwd
        )
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            //status等于1就是余额支付成功，不用在线支付。
                            //status等于0则是提交订单成功，待在线支付
                            1 -> {
                                if (act == 1) {
                                    //提交订单并且余额支付完成。
                                    setUserMoneyPay()
                                }
                            }
                            0 -> if (it.data != null) {
                                if (act == 0) {
                                    //结算
                                    setPostOrder(it.data)
                                } else if (act == 1) {
                                    //提交订单
                                    setConfirmOrder(it.data)
                                }
                            }
                            else -> showError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    /**
     * 普通购买订单
     */
    override fun requestPostOrder(
            act: Int,
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
            prom_id: String
    ) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestPostOrder(
                act,
                prom_type,
                address_id,
                invoice_title,
                taxpayer,
                invoice_desc,
                coupon_id,
                pay_points,
                user_money,
                user_note,
                pay_pwd,
                goods_id,
                goods_num,
                item_id,
                action,
                shop_id,
                take_time,
                consignee,
                mobile,
                prom_id
        )
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            //status等于1就是余额支付成功，不用在线支付。
                            //status等于0则是提交订单成功，待在线支付
                            1 -> {
                                if (act == 1) {
                                    //提交订单并且余额支付完成。
                                    setUserMoneyPay()
                                }
                            }
                            0 -> if (it.data != null) {
                                if (act == 0) {
                                    //结算
                                    setPostOrder(it.data)
                                } else if (act == 1) {
                                    //提交订单
                                    LogUtils.e(">>>>:" + it.data)
                                    setConfirmOrder(it.data)
                                }
                            }
                            -5 -> {
                                //未添加收货信息
                                setCompleteAddress()
                            }
                            else -> showError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

}