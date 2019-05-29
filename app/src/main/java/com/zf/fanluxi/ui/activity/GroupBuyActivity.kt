package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.GroupBuyList
import com.zf.fanluxi.mvp.contract.GroupBuyContract
import com.zf.fanluxi.mvp.presenter.GroupBuyPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.GroupBuyAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.view.RecDecoration
import kotlinx.android.synthetic.main.activity_group_buy.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 团购
 */
class GroupBuyActivity : BaseActivity(), GroupBuyContract.View {

    override fun setGroupBuy(bean: List<GroupBuyList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMoreGroupBuy(bean: List<GroupBuyList>) {
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        mLayoutStatusView?.showEmpty()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, GroupBuyActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "团购"
        rightLayout.visibility = View.INVISIBLE
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
    }

    override fun layoutId(): Int = R.layout.activity_group_buy

    override fun initData() {
    }

    private val data = ArrayList<GroupBuyList>()

    private val adapter by lazy { GroupBuyAdapter(this, data) }

    private val presenter by lazy { GroupBuyPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))
    }

    override fun initEvent() {

        adapter.itemClickListener = {
            GoodsDetail2Activity.actionStart(this, it.goods_id)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                defaultRb.id -> {
                    mType = ""
                    initLoad(1)
                }
                newRb.id -> {
                    mType = "new"
                    initLoad(1)
                }
                commentRb.id -> {
                    mType = "comment"
                    initLoad(1)
                }
            }
        }

        refreshLayout.setOnLoadMoreListener {
            initLoad(null)
        }
    }

    private var mType = ""

    private fun initLoad(page: Int?) {
        presenter.requestGroupBuy(mType, page)
    }

    override fun start() {
        mLayoutStatusView?.showLoading()
        refreshLayout.setNoMoreData(false)
        presenter.requestGroupBuy(mType, 1)
    }

    override fun onDestroy() {
        presenter.detachView()
        adapter.finishCountDown()
        super.onDestroy()
    }
}