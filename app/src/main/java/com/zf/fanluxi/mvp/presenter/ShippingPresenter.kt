package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.ShippingContract
import com.zf.fanluxi.mvp.model.ShippingModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class ShippingPresenter : BasePresenter<ShippingContract.View>(), ShippingContract.Presenter {

    private val model: ShippingModel by lazy { ShippingModel() }

    override fun requestShipping(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestShipping(orderId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setShipping(it.data)
                            }else{
                                setEmpty()
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