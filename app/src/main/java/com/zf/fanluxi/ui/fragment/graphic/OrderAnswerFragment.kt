package com.zf.fanluxi.ui.fragment.graphic

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.MyApplication
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.AttriBute
import com.zf.fanluxi.mvp.contract.GoodsAttrContract
import com.zf.fanluxi.mvp.presenter.GoodsAttrPresenter
import com.zf.fanluxi.ui.adapter.OrderInfoAdapter
import com.zf.fanluxi.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_order_answer.*

class OrderAnswerFragment : BaseFragment(), GoodsAttrContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getGoodsAttr(bean: List<AttriBute>) {
        mAttr.clear()
        for (i in 0 until bean.size) {
            if (bean[i].attr.isNotEmpty()) {
                mAttr.add(bean[i])
            }
        }
        attrAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    private var mGoodsId = ""

    private val presenter by lazy { GoodsAttrPresenter() }

    //商品属性adapter
    private val attrAdapter by lazy { OrderInfoAdapter(context, mAttr) }

    //商品属性
    private var mAttr = ArrayList<AttriBute>()

    companion object {
        fun newInstance(id: String): OrderAnswerFragment {
            val fragment = OrderAnswerFragment()
            fragment.mGoodsId = id
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_order_answer

    override fun initView() {
        presenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                2,
                ContextCompat.getColor(context ?: MyApplication.context, R.color.colorLine)
            )
        )
        recyclerView.adapter = attrAdapter
    }

    override fun lazyLoad() {
        presenter.requestGoodsAttr(mGoodsId)
    }

    override fun initEvent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}