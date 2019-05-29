package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.MemberOrderContract
import com.zf.fanluxi.mvp.model.MemberOrderModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class MemberOrderPresenter : BasePresenter<MemberOrderContract.View>(), MemberOrderContract.Presenter {
    private val model by lazy { MemberOrderModel() }
    private var mPage = 1
    override fun requestMemberOrder(page: Int?,next_user_id: String) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMemberOrder(mPage, PER_PAGE, next_user_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMenberOrder(it.data)
                                    }else{
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