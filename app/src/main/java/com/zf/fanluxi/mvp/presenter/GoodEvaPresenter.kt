package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.GoodEvaContract
import com.zf.fanluxi.mvp.model.GoodEvaModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class GoodEvaPresenter : BasePresenter<GoodEvaContract.View>(), GoodEvaContract.Presenter {

    private val model: GoodEvaModel by lazy { GoodEvaModel() }

    private var mPage = 1

    override fun requestGoodEva(goodId: String, type: Int, page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodEva(goodId, type, mPage)
                .subscribe({
                    mRootView?.apply {
                        when (it.status) {
                            0 -> {
                                if (mPage == 1) {
                                    if (it.data != null && it.data.commentlist.isNotEmpty()) {
                                        setGoodEva(it.data)
                                    } else {
                                        setEmpty()
                                    }
                                } else {
                                    if (it.data != null && it.data.commentlist.isNotEmpty()) {
                                        setLoadMore(it.data)
                                    } else {
                                        setLoadComplete()
                                    }
                                }
                                if (it.data != null && it.data.commentlist.isNotEmpty()) {
                                    if (it.data.commentlist.size < UriConstant.PER_PAGE) {
                                        setLoadComplete()
                                    }
                                }
                                mPage += 1
                            }
                            else -> if (mPage == 1) {
                                showError(it.msg, it.status)
                            } else {
                                loadMoreError(it.msg, it.status)
                            }
                        }
                        dismissLoading()
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        if (mPage == 1) {
                            showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                        } else {
                            loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                        }
                    }
                })
        addSubscription(disposable)
    }

}