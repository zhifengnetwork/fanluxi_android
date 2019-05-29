package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.DistributeOrderList
import com.zf.fanluxi.mvp.contract.DistributeOrderContract
import com.zf.fanluxi.mvp.presenter.DistributeOrderPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.DistributeOrderAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_distribute_order.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 分销订单
 */
class DistributeOrderActivity : BaseActivity(), DistributeOrderContract.View {

    override fun setDistributeOrder(bean: List<DistributeOrderList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMore(bean: List<DistributeOrderList>) {
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun showError(msg: String, errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
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
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, DistributeOrderActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "直属下级分销订单"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_distribute_order

    override fun initData() {
    }

    private val data = ArrayList<DistributeOrderList>()
    private val adapter by lazy { DistributeOrderAdapter(this, data) }
    private val presenter by lazy { DistributeOrderPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        refreshLayout.setOnLoadMoreListener {
            presenter.requestDistributeOrder(null)
        }

        refreshLayout.setOnRefreshListener {
            start()
        }

    }

    override fun start() {
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        refreshLayout.setNoMoreData(false)
        presenter.requestDistributeOrder(1)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}