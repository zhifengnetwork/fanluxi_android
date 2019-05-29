package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.DiscountContract
import com.zf.fanluxi.mvp.model.DiscountModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class DiscountPresenter : BasePresenter<DiscountContract.View>(), DiscountContract.Presenter {

    private val model: DiscountModel by lazy { DiscountModel() }

    override fun requestDiscount(status: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestDisount(status)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setDiscount(it.data)
                            } else {
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