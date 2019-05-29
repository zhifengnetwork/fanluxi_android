package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.DetailRecordList
import com.zf.fanluxi.mvp.contract.DetailRecordContract
import com.zf.fanluxi.mvp.presenter.DetailRecordPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.DetailRecordAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_detail_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 佣金明细
 */
class DetailRecordActivity : BaseActivity(), DetailRecordContract.View {

    override fun setDetailRecord(bean: List<DetailRecordList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMore(bean: List<DetailRecordList>) {
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
            context?.startActivity(Intent(context, DetailRecordActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "佣金明细"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_detail_record

    override fun initData() {
    }

    private val data = ArrayList<DetailRecordList>()
    private val adapter by lazy { DetailRecordAdapter(this, data) }
    private val presenter by lazy { DetailRecordPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        refreshLayout.setOnLoadMoreListener {
            presenter.requestDetailRecord(null)
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
        presenter.requestDetailRecord(1)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}