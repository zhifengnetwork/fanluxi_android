package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.OrderListContract
import com.zf.fanluxi.mvp.model.OrderListModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class OrderListPresenter : BasePresenter<OrderListContract.View>(), OrderListContract.Presenter {

    private var mPage: Int = 1

    override fun requestOrderList(type: String, page: Int?, keyWord: String) {
        checkViewAttached()

        mPage = page ?: mPage

        //加载图在activity中写,showLoading只做结束refreshLayout的finish事件
        val disposable = model.requestOrderList(type, mPage, keyWord)
                .subscribe({
                    mRootView?.apply {
                        when (it.status) {
                            0 -> {
                                if (mPage == 1) {
                                    if (it.data != null && it.data.isNotEmpty()) {
                                        setFinishRefresh(it.data)
                                    } else {
                                        setEmptyOrder()
                                    }
                                } else {
                                    if (it.data != null && it.data.isNotEmpty()) {
                                        setFinishLoadMore(it.data)
                                    } else {
                                        setLoadComplete()
                                    }
                                }
                                if (it.data != null && it.data.isNotEmpty()) {
                                    if (it.data.size < UriConstant.PER_PAGE) {
                                        setLoadComplete()
                                    }
                                }
                                mPage += 1
                            }
                            else -> {
                                if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                            }
                        }
                        dismissLoading()
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        if (mPage == 1) showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                        else loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)

                    }

                })
        addSubscription(disposable)
    }

    private val model: OrderListModel by lazy { OrderListModel() }

}