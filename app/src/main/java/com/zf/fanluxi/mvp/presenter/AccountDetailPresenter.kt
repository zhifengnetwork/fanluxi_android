package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.AccountDetailContract
import com.zf.fanluxi.mvp.model.AccountDetailModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class AccountDetailPresenter : BasePresenter<AccountDetailContract.View>(), AccountDetailContract.Presenter {

    private val model by lazy { AccountDetailModel() }

    private var mPage = 1

    override fun requestAccountDetail(type: String, page: Int?) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getAccountDetail(type, mPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getAccountDetail(it.data.list)
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

}