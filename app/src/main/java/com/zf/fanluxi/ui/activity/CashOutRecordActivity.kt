package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.CashOutList
import com.zf.fanluxi.mvp.contract.CashOutRecordContract
import com.zf.fanluxi.mvp.presenter.CashOutRecordPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.CashOutAdapter
import kotlinx.android.synthetic.main.activity_cash_out_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class CashOutRecordActivity : BaseActivity(), CashOutRecordContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode== ErrorStatus.NETWORK_ERROR){
            mLayoutStatusView?.showNoNetwork()
        }else{
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    override fun getCashOutList(bean: List<CashOutList>) {
        refreshLayout.setEnableLoadMore(true)
        mLayoutStatusView?.showContent()
        mData.clear()
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
        mLayoutStatusView?.showEmpty()
    }

    override fun setLoadMore(bean: List<CashOutList>) {
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
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, CashOutRecordActivity::class.java))
        }
    }

    override fun initToolBar() {

        titleName.text = "提现记录"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    private val presenter by lazy { CashOutRecordPresenter() }

    private val mData = ArrayList<CashOutList>()

    private val adapter by lazy { CashOutAdapter(this, mData) }

    override fun layoutId(): Int = R.layout.activity_cash_out_record

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        record_rl.layoutManager = LinearLayoutManager(this)
        record_rl.adapter = adapter

    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestCashOutList(null)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            refreshLayout.setEnableLoadMore(false)
            presenter.requestCashOutList(1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestCashOutList(1)
    }

}