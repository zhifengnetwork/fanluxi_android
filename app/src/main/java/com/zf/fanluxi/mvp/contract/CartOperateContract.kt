package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.CartPrice
import com.zf.fanluxi.mvp.bean.GoodsSpecInfo
import com.zf.fanluxi.mvp.bean.SpecBean
import okhttp3.RequestBody

interface CartOperateContract {

    interface View : IBaseView {

        fun cartOperateError(msg: String, errorCode: Int)

        //修改数量
        fun setCount(bean: CartPrice)

        //修改选中状态
        fun setSelect(bean: CartPrice)

        //全选
        fun setCheckAll(bean: CartPrice)

        //删除
        fun setDeleteCart(bean: CartPrice)

        //获取商品规格
        fun setGoodsSpec(specBean: List<List<SpecBean>>)

        //修改商品规格
        fun setChangeSpec(bean: CartPrice)

        //根据规格获取商品信息
        fun setSpecInfo(bean: GoodsSpecInfo)
    }

    interface Presenter : IPresenter<View> {

        fun requestCount(id: String, num: Int)

        fun requestSelect(cart: RequestBody)

        fun requestCheckAll(flag: Int)

        fun requestDeleteCart(id: RequestBody)

        fun requestGoodsSpec(id: String)

        fun requestChangeSpec(cartId: String, specId: String)

        fun requestSpecInfo(key: String, goodsId: String)
    }

}