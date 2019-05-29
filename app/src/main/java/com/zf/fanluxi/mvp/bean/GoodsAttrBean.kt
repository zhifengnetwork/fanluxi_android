package com.zf.fanluxi.mvp.bean

data class GoodsAttrBean(
    val goods_attribute:List<AttriBute>
)

data class AttriBute(
    val attr_id:String,
    val attr_name:String,
    val attr:List<AttrList>
)
data class AttrList(
    val goods_attr_id:String,
    val goods_id:String,
    val attr_id:String,
    val attr_value:String,
    val attr_price:String
)