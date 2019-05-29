package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.UserInfoContract
import com.zf.fanluxi.mvp.model.UserInfoModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class UserInfoPresenter : BasePresenter<UserInfoContract.View>(), UserInfoContract.Presenter {

    private val model by lazy {
        UserInfoModel()
    }

    override fun requestUserInfo() {
        checkViewAttached()
        val disposable = model.requestUserInfo().subscribe(
            {
                mRootView?.apply {
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setUserInfo(it.data)
                        }
                        else -> {
                            showError(it.msg, it.status)
                        }
                    }
                }
            }, {
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)

    }
}