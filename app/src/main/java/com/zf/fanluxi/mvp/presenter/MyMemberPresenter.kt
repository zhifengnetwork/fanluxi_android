package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.MyMemberContract
import com.zf.fanluxi.mvp.model.MyMemberModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class MyMemberPresenter : BasePresenter<MyMemberContract.View>(), MyMemberContract.Presenter {
    private val model by lazy { MyMemberModel() }
    private var mPage = 1
    override fun requestMyMember(page: Int?, next_user_id: String) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMyMember(mPage, PER_PAGE, next_user_id)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.isNotEmpty()) {
                                        getMyMember(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data)
                                }
                                if (it.data.size < PER_PAGE) {
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