package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.SearchBean

interface SearchContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        //搜索结果
        fun setSearchList(bean: SearchBean)

        //加载结果
        fun setLoadMore(bean: SearchBean)

        //刷新无结果
        fun freshEmpty()

        //加载第二页失败
        fun loadMoreError(msg: String, status: Int)

        //全部加载完成
        fun loadComplete()

    }

    interface Presenter : IPresenter<View> {

        fun requestClassifySearch(
            id: String,
            brand_id: String,
            sort: String,
            sel: String,
            price: String,
            start_price: String,
            end_price: String,
            price_sort: String,
            page: Int?
        )

        fun requestSearch(
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
        )

    }

}