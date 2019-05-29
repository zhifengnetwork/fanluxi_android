package com.zf.fanluxi.mvp.bean

data class ShopListBean(
    val list:List<ShopList>
)
data class ShopList(
    val store_id:String,
    val store_name:String,
    val avatar:String,
    val businesshours:String,
    val email:String,
    val seller_id:String,
    val goods:List<ShopGoodsList>,
    val collect_num:String,
    val is_collect:Int
)
data class ShopGoodsList(
    val goods_id:String,
    val goods_name:String,
    val shop_price:String,
    val original_img:String
)