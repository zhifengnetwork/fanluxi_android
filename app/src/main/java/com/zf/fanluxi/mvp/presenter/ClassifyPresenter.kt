package com.zf.fanluxi.mvp.presenter


import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.ClassifyContract
import com.zf.fanluxi.mvp.model.ClassifyModel
import com.zf.fanluxi.net.exception.ExceptionHandle


class ClassifyPresenter : BasePresenter<ClassifyContract.View>(), ClassifyContract.Presenter {
    override fun requestClassify() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestClassify()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setTitle(it.data)
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


    private val model: ClassifyModel by lazy { ClassifyModel() }

}