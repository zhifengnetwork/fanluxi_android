package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.FollowShopList
import com.zf.fanluxi.mvp.bean.MyFollowShopBean
import com.zf.fanluxi.mvp.bean.ShopList


interface MyFollowShopContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //获得关注店铺列表
        fun getMyFollowShop(bean: MyFollowShopBean)

        //添加或删除店铺
        fun delMyFollowShop(msg: String)

        //猜你喜欢店铺
        fun getShopList(bean:List<ShopList>)

        fun freshEmpty()

        fun setLoadFollowShopMore(bean: List<FollowShopList>)

        fun setLoadShopMore(bean:List<ShopList>)

        fun setLoadFollowShopComplete()

        fun setLoadShopComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyFollowShop(page: Int?)

        fun requestDelMyFollowShop(seller_id: String, type: String, collect_id: String)
        
        fun requsetShopList(page: Int?,goodsnum:Int)
    }
}