package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.UpdateUserInfoContract
import com.zf.fanluxi.mvp.model.UpdateUserInfoModel
import com.zf.fanluxi.net.exception.ExceptionHandle
import okhttp3.MultipartBody

class UpdateUserInfoPresenter : BasePresenter<UpdateUserInfoContract.View>(), UpdateUserInfoContract.Presenter {

    //更改用户信息
    override fun changeUserInfo(
        nickname: String,
        mobile: String,
        sex: String,
        birthyear: String,
        birthmonth: String,
        birthday: String
    ) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.changeUserInfo(nickname, mobile, sex, birthyear, birthmonth, birthday)
            .subscribe({ it ->
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setChangeUserInfo(it)
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

    private val model: UpdateUserInfoModel by lazy {
        UpdateUserInfoModel()
    }

    //上传头像
    override fun upLoadHead(head: MultipartBody.Part) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.uploadHead(head)
            .subscribe({ it ->
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setHead(it)
                        -2 -> showError(it.msg, it.status)
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
