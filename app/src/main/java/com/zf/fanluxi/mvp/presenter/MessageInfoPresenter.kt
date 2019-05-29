package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.MessageInfoContract
import com.zf.fanluxi.mvp.model.MessageInfoModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class MessageInfoPresenter : BasePresenter<MessageInfoContract.View>(), MessageInfoContract.Presenter {
    private val model by lazy { MessageInfoModel() }
    override fun requestMessageInfo(rec_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMessageInfo(rec_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMessageInfo(it.data.info)
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