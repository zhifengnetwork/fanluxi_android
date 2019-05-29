package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.CartListContract
import com.zf.fanluxi.mvp.model.CartListModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class CartListPresenter : BasePresenter<CartListContract.View>(), CartListContract.Presenter {

    private val model: CartListModel by lazy { CartListModel() }

    private var mPage = 1

    override fun requestCartList(page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getCartList(mPage)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (mPage == 1) {
                                if (it.data != null && it.data.list.isNotEmpty()) {
                                    setRefreshCart(it.data)
                                } else {
                                    setCartEmpty()
                                }
                            } else {
                                if (it.data != null && it.data.list.isNotEmpty()) {
                                    setLoadMoreCart(it.data)
                                } else {
                                    setCartLoadComplete()
                                }
                            }
                            if (it.data != null && it.data.list.isNotEmpty()) {
                                if (it.data.list.size < UriConstant.PER_PAGE) {
                                   setCartLoadComplete()
                                }
                            }
                            mPage += 1
                        }
                        else -> if (mPage == 1) {
                            showCartError(it.msg, it.status)
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
                        showCartError( ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    } else {
                        loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                }
            })
        addSubscription(disposable)
    }

}