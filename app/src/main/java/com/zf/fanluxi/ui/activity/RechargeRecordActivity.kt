package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.RechargeRecordList
import com.zf.fanluxi.mvp.contract.RechargeContract
import com.zf.fanluxi.mvp.presenter.RechargePresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.RechargeRecordAdapter
import com.zf.fanluxi.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_recharge_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 充值记录
 * */
class RechargeRecordActivity : BaseActivity(), RechargeContract.View {
    override fun showError(msg: String, errorCode: Int) {
        if (errorCode== ErrorStatus.NETWORK_ERROR){
            mLayoutStatusView?.showNoNetwork()
        }else{
            mLayoutStatusView?.showError()
            showToast(msg)
        }
        refreshLayout.setEnableLoadMore(false)
    }

    override fun getRechargeList(bean: List<RechargeRecordList>) {
        refreshLayout.setEnableLoadMore(true)
        mLayoutStatusView?.showContent()
        mData.clear()
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<RechargeRecordList>) {
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
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
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, RechargeRecordActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text = "充值记录"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_recharge_record

    private val presenter by lazy { RechargePresenter() }

    private val mAdapter by lazy { RechargeRecordAdapter(this, mData) }

    private var mData = ArrayList<RechargeRecordList>()

    private val divider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            2,
            ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        recharge_rl.layoutManager = LinearLayoutManager(this)
        recharge_rl.addItemDecoration(divider)
        recharge_rl.adapter = mAdapter
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestRechargeList(null)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            refreshLayout.setEnableLoadMore(false)
            presenter.requestRechargeList(1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestRechargeList(1)
    }

}