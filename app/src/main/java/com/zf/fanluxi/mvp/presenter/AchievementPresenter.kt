package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.AchievementContract
import com.zf.fanluxi.mvp.model.AchievementModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class AchievementPresenter : BasePresenter<AchievementContract.View>(), AchievementContract.Presenter {
    private val model by lazy { AchievementModel() }
    override fun requestAchievementDetail() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAchievement()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getAchievement(it.data.list)
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