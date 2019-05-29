package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.OrderOperateContract
import com.zf.fanluxi.mvp.model.OrderOperateModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class OrderOperatePresenter : BasePresenter<OrderOperateContract.View>(), OrderOperateContract.Presenter {

    private val model: OrderOperateModel by lazy { OrderOperateModel() }

    //确认收货
    override fun requestConfirmReceipt(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestConfirmReceipt(orderId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setConfirmReceipt()
                            else -> showOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun requestCancelOrder(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestCancelOrder(orderId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setCancelOrder()
                            else -> showOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

}