package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.BonusContract
import com.zf.fanluxi.mvp.model.BonusModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class BonusPresenter : BasePresenter<BonusContract.View>(), BonusContract.Presenter {
    private val model by lazy { BonusModel() }
    override fun requestBonus() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getBonus()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getBonus(it.data)
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

    override fun requestAdList(pid: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAdList(pid)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getAdList(it.data.list)
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


}