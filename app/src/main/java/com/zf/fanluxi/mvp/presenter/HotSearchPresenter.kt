package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.HotSearchContract
import com.zf.fanluxi.mvp.model.HotSearchModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class HotSearchPresenter : BasePresenter<HotSearchContract.View>(), HotSearchContract.Presenter {

    private val model: HotSearchModel by lazy { HotSearchModel() }

    override fun requestHotSearch() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestHotSearch()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setHotSearch(it.data)
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