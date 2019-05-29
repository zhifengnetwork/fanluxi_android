package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.AddressContract
import com.zf.fanluxi.mvp.model.AddressModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class AddressPresenter : BasePresenter<AddressContract.View>(), AddressContract.Presenter {

    override fun requestAddress() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAddress()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data!=null){
                                getAddress(it.data)
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

    private val model: AddressModel by lazy { AddressModel() }


}