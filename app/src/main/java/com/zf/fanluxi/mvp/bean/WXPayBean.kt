package com.zf.fanluxi.mvp.bean


data class WXPayBean(
    val appid: String,
    val partnerid: String,
    val prepayid: String,
//    @SerializedName("package")
    val packageValue : String,
    val noncestr: String,
    val timestamp: String,
    val sign: String
)