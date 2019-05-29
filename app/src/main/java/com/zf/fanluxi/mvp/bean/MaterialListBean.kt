package com.zf.fanluxi.mvp.bean

data class MaterialListBean(
    val list:List<MaterialList>
)
data class MaterialList(
    val material_id:String,
    val title:String,
    val keywords:String,
    val add_time:String,
    val describe:String,
    val thumb:String
)