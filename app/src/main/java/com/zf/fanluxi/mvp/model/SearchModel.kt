package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.SearchBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class SearchModel {

    fun requestClassifySearch(
        id: String,
        brand_id: String,
        sort: String,
        sel: String,
        price: String,
        start_price: String,
        end_price: String,
        price_sort: String,
        page: Int
    ): Observable<BaseBean<SearchBean>> {
        return RetrofitManager.service.requestClassifySearch(
            id,
            brand_id,
            sort,
            sel,
            price,
            start_price,
            end_price,
            price_sort,
            page,
            UriConstant.PER_PAGE
        )
            .compose(SchedulerUtils.ioToMain())
    }

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
        page: Int
    ): Observable<BaseBean<SearchBean>> {
        return RetrofitManager.service.getSearchList(
            q,
            id,
            brand_id,
            sort,
            sel,
            price,
            start_price,
            end_price,
            price_sort,
            page,
            UriConstant.PER_PAGE
        )
            .compose(SchedulerUtils.ioToMain())
    }
}