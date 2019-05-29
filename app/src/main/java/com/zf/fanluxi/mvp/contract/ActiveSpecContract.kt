package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.GoodsSpecInfo
import com.zf.fanluxi.mvp.bean.SpecBean

interface ActiveSpecContract{
    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        //获取商品规格
        fun setGoodsSpec(specBean: List<List<SpecBean>>)

        //根据规格获取商品信息
        fun setSpecInfo(bean: GoodsSpecInfo)

    }

    interface Presenter : IPresenter<View> {

        fun requestSpec(id: String)

        fun requestSpecInfo(key: String, goodsId: String)
    }
}