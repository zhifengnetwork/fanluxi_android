package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.SearchContract
import com.zf.fanluxi.mvp.model.SearchModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private val model: SearchModel by lazy { SearchModel() }

    private var mPage = 1

    override fun requestClassifySearch(
        id: String,
        brand_id: String,
        sort: String,
        sel: String,
        price: String,
        start_price: String,
        end_price: String,
        price_sort: String,
        page: Int?
    ) {
        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable =
            model.requestClassifySearch(id, brand_id, sort, sel, price, start_price, end_price, price_sort, mPage)
                .subscribe({
                    mRootView?.apply {
                        when (it.status) {
                            0 -> {
                                if (mPage == 1) {
                                    if (it.data?.goods_list != null && it.data.goods_list.isNotEmpty()) {
                                        setSearchList(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    if (it.data?.goods_list != null && it.data.goods_list.isNotEmpty()) {
                                        setLoadMore(it.data)
                                    } else {
                                        loadComplete()
                                    }
                                }
                                if (it.data?.goods_list != null && it.data.goods_list.isNotEmpty()) {
                                    if (it.data.goods_list.size < UriConstant.PER_PAGE) {
                                        loadComplete()
                                    }
                                }
                                mPage += 1
                            }
                            else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)

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

    override fun requestSearch(
        q: String,
        id: String,
        brand_id: String,
        sort: String,
        sel: String,
        price: String,
        start_price: String,
        end_price: String,
        price_sort: String,
        page: Int?
    ) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable =
            model.requestSearch(q, id, brand_id, sort, sel, price, start_price, end_price, price_sort, mPage)
                .subscribe({
                    mRootView?.apply {
                        when (it.status) {
                            0 -> {
                                if (mPage == 1) {
                                    if (it.data?.goods_list != null && it.data.goods_list.isNotEmpty()) {
                                        setSearchList(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    if (it.data?.goods_list != null && it.data.goods_list.isNotEmpty()) {
                                        setLoadMore(it.data)
                                    } else {
                                        loadComplete()
                                    }
                                }
                                if (it.data?.goods_list != null && it.data.goods_list.isNotEmpty()) {
                                    if (it.data.goods_list.size < UriConstant.PER_PAGE) {
                                        loadComplete()
                                    }
                                }
                                mPage += 1
                            }
                            else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)

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

}