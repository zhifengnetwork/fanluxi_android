package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.CashOutRecordContract
import com.zf.fanluxi.mvp.model.CashOutRecordModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class CashOutRecordPresenter : BasePresenter<CashOutRecordContract.View>(), CashOutRecordContract.Presenter {
    private var mPage = 1
    override fun requestCashOutList(page: Int?) {
        checkViewAttached()

        mPage = page ?: mPage

        mRootView?.showLoading()
        val disposable = model.getCashOutRecordList(mPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getCashOutList(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.list)
                                }
                                if (it.data.list.size < PER_PAGE) {
                                    setLoadComplete()
                                }
                                mPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                    dismissLoading()
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    private val model by lazy { CashOutRecordModel() }
}