package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.ChangePwdContract
import com.zf.fanluxi.mvp.model.ChangePwdModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class ChangePwdPresenter : BasePresenter<ChangePwdContract.View>(), ChangePwdContract.Presenter {
    private val model by lazy { ChangePwdModel() }
    override fun requestChangePwd(passold: String, password: String, password2: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.changePwd(passold, password, password2)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> changePwdSuccess(it.msg)
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