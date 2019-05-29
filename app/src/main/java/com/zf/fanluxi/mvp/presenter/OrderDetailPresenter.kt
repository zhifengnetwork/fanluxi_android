package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.OrderDetailContract
import com.zf.fanluxi.mvp.model.OrderDetailModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class OrderDetailPresenter : BasePresenter<OrderDetailContract.View>(), OrderDetailContract.Presenter {

    private val model: OrderDetailModel by lazy { OrderDetailModel() }

    override fun requestOrderDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getOrderDetail(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setOrderDetail(it.data)
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