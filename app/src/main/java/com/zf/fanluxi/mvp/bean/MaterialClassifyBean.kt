package com.zf.fanluxi.mvp.bean

data class MaterialClassifyBean(
    val list: List<MaterialClassifyList>
)

data class MaterialClassifyList(
    val cat_id: String,
    val cat_name: String
)