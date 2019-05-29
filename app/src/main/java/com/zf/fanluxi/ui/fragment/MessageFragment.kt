package com.zf.fanluxi.ui.fragment


import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.MessageList
import com.zf.fanluxi.mvp.contract.MessageContract
import com.zf.fanluxi.mvp.presenter.MessagePresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseFragment(), MessageContract.View {

    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    //第一页获得数据
    override fun getMessage(bean: List<MessageList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        mData.clear()
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    //当第一页数据为空时
    override fun freshEmpty() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    //不是第一页获得数据
    override fun setLoadMore(bean: List<MessageList>) {
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    //实际获得数据小于一页最大数据时
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()

    }

    //下拉加载错误
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setBuyError(msg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mType = ""

    companion object {
        const val BUY = "BUY"
        const val SELL = "SELL"
        fun newInstance(type: String): MessageFragment {
            val fragment = MessageFragment()
            fragment.mType = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_message

    private val adapter by lazy { MessageAdapter(context, mData) }

    private var mData = ArrayList<MessageList>()

    private val presenter by lazy { MessagePresenter() }

    override fun initView() {
        presenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            if (mType == BUY) {
                presenter.requestMessage(null, "2")
            } else {
                presenter.requestMessage(null, "1")
            }
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestMessage(1, if (mType == BUY) "2" else "1")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}