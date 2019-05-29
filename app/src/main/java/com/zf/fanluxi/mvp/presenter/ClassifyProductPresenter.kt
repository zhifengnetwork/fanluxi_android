package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.ClassifyProductContract
import com.zf.fanluxi.mvp.model.ClassifyProductModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class ClassifyProductPresenter : BasePresenter<ClassifyProductContract.View>(), ClassifyProductContract.Presenter {
    private val model: ClassifyProductModel by lazy { ClassifyProductModel() }
    override fun requestClassifyProduct(cat_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestClassifyProduct(cat_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setProduct(it.data)
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