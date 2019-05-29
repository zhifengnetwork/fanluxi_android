package com.zf.fanluxi.mvp.bean

data class SpecBean(
//        val list: List<SpecList>
        val name: String,
        val item: String,
        val id: String
)

data class SpecCorrect(
        val name: String,
        val list: List<SpecBean>,
        var chooseId: String //选中的id
)

//data class SpecList(
//        val key: String,
//        val item: String?,
//        val item_id: String,
//        val price:String?
//)