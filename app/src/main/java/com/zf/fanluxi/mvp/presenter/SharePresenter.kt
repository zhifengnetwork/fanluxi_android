package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.ShareContract
import com.zf.fanluxi.mvp.model.ShareModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class SharePresenter : BasePresenter<ShareContract.View>(), ShareContract.Presenter {

    private val model: ShareModel by lazy { ShareModel() }

    override fun requestShare() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestShare()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setShare(it.data)
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