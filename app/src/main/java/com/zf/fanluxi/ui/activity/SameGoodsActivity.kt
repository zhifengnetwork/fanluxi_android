package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.CommendList
import com.zf.fanluxi.mvp.bean.MyFollowList
import com.zf.fanluxi.mvp.contract.EquallyGoodsContract
import com.zf.fanluxi.mvp.presenter.EquallyGoodsPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.SameGoodsAdapter
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.view.RecDecoration
import kotlinx.android.synthetic.main.activity_same_goods.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 关注->商品->同款商品
 */
class SameGoodsActivity : BaseActivity(), EquallyGoodsContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    override fun getEquallyGoods(bean: List<CommendList>) {
        mData.clear()
        mData.addAll(bean)
        adapter.notifyDataSetChanged()

    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<CommendList>) {
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun actionStart(context: Context?, data: MyFollowList) {
            val intent = Intent(context, SameGoodsActivity::class.java)
            intent.putExtra("goods", data)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "查看同款"
        rightLayout.visibility = View.INVISIBLE
    }

    private var data: MyFollowList? = null

    private val presenter by lazy { EquallyGoodsPresenter() }

    private val adapter by lazy { SameGoodsAdapter(this, mData) }

    private val mData = ArrayList<CommendList>()

    override fun layoutId(): Int = R.layout.activity_same_goods

    override fun initData() {
        data = intent.getSerializableExtra("goods") as MyFollowList
    }


    override fun initView() {
        presenter.attachView(this)
        //商品图片
        GlideUtils.loadUrlImage(this, UriConstant.BASE_URL + data?.original_img, goodsIcon)
        //商品名字
        goodsName.text = data?.goods_name
        //商品价格
        goodsPrice.text = data?.shop_price
        //市场价格
        oldPrice.text = data?.market_price




        oldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(10f)))
    }

    override fun initEvent() {
        /**上啦加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestEquallyGoods(data?.cat_id.toString(), null, 6)
        }
        same.setOnClickListener {
            GoodsDetail2Activity.actionStart(this, data?.goods_id ?: "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestEquallyGoods(data?.cat_id.toString(), 1, 6)
    }
}