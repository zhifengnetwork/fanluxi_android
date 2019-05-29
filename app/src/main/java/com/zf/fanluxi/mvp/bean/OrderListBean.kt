package com.zf.fanluxi.mvp.bean

import java.io.Serializable

data class OrderListBean(

    val order_sn: String,
    val consignee: String,
    val country: String,
    val province: String,
    val city: String,
    val district: String,
    val twon: String,
    val address: String,
    val seller_name: String,
    val order_prom_amount: String,
    val province_name: String?,
    val city_name: String?,
    val district_name: String?,
    val goods_num: String,
    val shipping_price: String,
    val total_amount: String,
    val integral_money: String,
    val sign_price: String,
    val coupon_price: String,
    val twon_name: String?,
    val avatar: String,
    val pay_time: String,
    val pay_name: String,
    val mobile: String,
    val user_money: String,

    val order_id: String,
    val order_amount: String,
    val add_time: Long,
    val goods_name: String,
    val num: String,
    val goods_price: String,
    val spec_key_name: String,
    val store_name: String,
    val original_img: String,
    val pay_status: String, //pay_status 0未支付 1已支付 2部分支付 3已退款 4拒绝退款
    //订单状态
    val order_status: String,
    //发货状态
    val shipping_status: String,
    //是否货到付款
    val pay_code: String,
    val goods: List<OrderGoodsList>
) : Serializable

data class OrderGoodsList(
    val goods_id: String,
    val original_img: String,
    val final_price: String,
    val goods_name: String,
    val spec_key_name: String,
    val goods_num: String,
    var evaluateContent: String, //用户评价的内容 *
    var imgList: ArrayList<String>? = ArrayList<String>(),  //用户评价的图片 *
    var deliverRank: String, //配送评分 *
    var goodsRank: String, //商品评分 *
    var serviceRank: String, //服务评分 *
    var is_anonymous: String //是否匿名 *

) : Serializable

