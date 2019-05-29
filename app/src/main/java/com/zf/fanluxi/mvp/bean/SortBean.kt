package com.zf.fanluxi.mvp.bean

/**
 * 左侧列表
 */
data class SortBean(

        var bigSortId: Int? = null,
        var bigSortName: String? = null,

        var isSelected: Boolean? = null,
        var list: List<ListBean>? = null
)

data class ListBean(
        var smallSortId: Int? = null,
        var smallSortName: String? = null
)

data class SortItem(
        var id: Int? = null,
        var viewType: Int? = null,
        var name: String? = null,
        var position: Int = -1
)