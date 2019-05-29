package com.zf.fanluxi.mvp.bean

data class CartBean(
        var list: ArrayList<CartGoodsList>,
        val seller_name: String, //商家名字
        var selected: String? = "", //1选中 0非选中
        val cart_price_info: CartPrice? = null,
        val selected_flag: CheckAllFlag? = null
)

//商品列表，但是也要有商家的名字
data class CartGoodsList(
        val seller_id: String,
        val seller_name: String? = "", //商家名字
        val id: String,          //商品id
        var goods_price: String,
        var goods_num: Int,
        var goods_name: String,
        var original_img: String,
        var spec_key: String, //5-12-17
        var spec_key_name: String, //颜色，规格，套餐
        val goods: Goods,
        val cat_id: String,
        var selected: String //商品是否选中
)

data class Goods(
        val goods_name: String, //商品名字
        var original_img: String, //商品图片
        val cat_id: String,
        val shop_price: String,
        val goods_num: String,
        val goods_id: String
)

data class CheckAllFlag(
        val all_flag: Int //1全选 2非全选
)