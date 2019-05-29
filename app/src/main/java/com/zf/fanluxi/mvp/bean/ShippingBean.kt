package com.zf.fanluxi.mvp.bean

data class ShippingBean(
        val invoice_no: String,
        val result: List<ShippingList>
)

data class ShippingList(
        val time: String,
        val status: String
)