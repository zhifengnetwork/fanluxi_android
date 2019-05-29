package com.zf.fanluxi.mvp.bean

data class ClassifyBean(
    val id:String,
    val name:String,
    val mobile_name:String,
    val parent_id:String,
    val parent_id_path:String,
    val level:String,
    val sort_order:String,
    val is_show:String,
    val image:String,
    val is_hot:String,
    val cat_group:String,
    val commission_rate:String
)