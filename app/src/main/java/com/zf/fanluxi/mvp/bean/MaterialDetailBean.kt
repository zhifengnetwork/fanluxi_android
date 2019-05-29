package com.zf.fanluxi.mvp.bean

data class MaterialDetailBean(
    val info: MaterialDetail,
    val content: String
)

data class MaterialDetail(
    val material_id: String,
    val cat_id: String,
    val title: String,
    val keywords: String,
    val add_time: Long,
    val describe: String,
    val click: String,
    val video: String,
    val thumb: String
)