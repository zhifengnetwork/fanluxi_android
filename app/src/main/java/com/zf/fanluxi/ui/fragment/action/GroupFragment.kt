package com.zf.fanluxi.ui.fragment.action

import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.GroupBean
import com.zf.fanluxi.mvp.contract.GroupContract
import com.zf.fanluxi.mvp.presenter.GroupPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.activity.GroupDetailActivity
import com.zf.fanluxi.ui.adapter.GroupAdapter
import com.zf.fanluxi.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_group.*

/**
 * 拼团
 */
class GroupFragment : BaseFragment(), GroupContract.View {

    companion object {
        fun newInstance(): GroupFragment {
            val fragment = GroupFragment()
            return fragment
        }
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun loadMore(bean: List<GroupBean>) {
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
        mLayoutStatusView?.showEmpty()
    }

    override fun loadComplete() {
        refreshLayout?.finishLoadMoreWithNoMoreData()
    }

    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    override fun setGroup(bean: List<GroupBean>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun getLayoutId(): Int = R.layout.fragment_group

    private val data = ArrayList<GroupBean>()

    private val adapter by lazy { GroupAdapter(context, data) }

    private val presenter by lazy { GroupPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        presenter.requestGroup(1)
    }

    override fun initEvent() {

        refreshLayout.setOnLoadMoreListener {
            presenter.requestGroup(null)
        }

        adapter.itemClickListener = { bean ->
            GroupDetailActivity.actionStart(context, bean.team_id)
        }

    }
}