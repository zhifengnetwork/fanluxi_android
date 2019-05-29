package com.zf.fanluxi.ui.fragment.graphic

import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_graphic.*


class GraphicFragment : BaseFragment() {

    //接收详情信息
    companion object {
        fun newInstance(data: String?): GraphicFragment {
            val fragment = GraphicFragment()
            fragment.htmlText = data
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_graphic

    //接收传递过来的H5
    private var htmlText: String? = ""

    override fun initView() {
        RichText.initCacheDir(context)
        RichText.from(htmlText).bind(activity).into(textHtml)

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

    }


}