package com.zf.fanluxi.ui.fragment.choice

import com.zf.fanluxi.ui.adapter.ChoiceGoodsAdapter


import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_choice_new.*

import android.widget.LinearLayout


class NewFragment:BaseFragment(){
    companion object {
        fun getInstance():NewFragment{
            return NewFragment()
        }
    }

    override fun getLayoutId(): Int = com.zf.fanluxi.R.layout.fragment_choice_new

    private val adapter by lazy { ChoiceGoodsAdapter(context) }

    override fun initView() {
       //设置横向RecyclerView
        val ms=LinearLayoutManager(context)
        ms.setOrientation(LinearLayoutManager.HORIZONTAL)

        scroll_recycler_view.layoutManager = ms


        scroll_recycler_view.adapter=adapter

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayout.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter



    }

    override fun lazyLoad() {

    }

    override fun initEvent() {
    }

}