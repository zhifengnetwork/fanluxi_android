package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AttriBute

interface GoodsAttrContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        //商品属性
        fun getGoodsAttr(bean:List<AttriBute>)
    }
    interface Presenter:IPresenter<View>{
        fun requestGoodsAttr(goods_id:String)
    }
}