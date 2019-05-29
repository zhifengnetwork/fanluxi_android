package com.zf.fanluxi.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.base.NotLazyBaseFragment
import com.zf.fanluxi.mvp.bean.AdvertList
import com.zf.fanluxi.mvp.bean.SecKillList
import com.zf.fanluxi.mvp.contract.SecKillListContract
import com.zf.fanluxi.mvp.presenter.SecKillListPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.ui.adapter.SecKillAdapter
import com.zf.fanluxi.utils.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_seckill_page.*

/**
 * 秒杀页面多个布局
 */
class SecKillPagerFragment : NotLazyBaseFragment(), SecKillListContract.View {
    override fun getAdList(bean: List<AdvertList>) {
        initBanner(bean)
    }

    override fun setSecKillList(bean: List<SecKillList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMore(bean: List<SecKillList>) {
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        refreshLayout.setEnableLoadMore(false)
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
            showToast(msg)
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

    var mStartTime = ""
    var mEndTime = ""

    companion object {
        fun newInstance(startTime: String, endTime: String): SecKillPagerFragment {
            val fragment = SecKillPagerFragment()
            fragment.mStartTime = startTime
            fragment.mEndTime = endTime
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_seckill_page

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        //商品列表
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

//        initBanner()
    }

    private val data = ArrayList<SecKillList>()

    private val adapter by lazy { SecKillAdapter(context, data) }

    private val presenter by lazy { SecKillListPresenter() }


    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        presenter.requestAdList("9")
        presenter.requestSecKillList(mStartTime, mEndTime, 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun initEvent() {

        refreshLayout.setOnLoadMoreListener {
            presenter.requestSecKillList(mStartTime, mEndTime, null)
        }


    }

    private fun initBanner(bean: List<AdvertList>) {
        // 在最后需要start, start()在点击事件之后
        val images = ArrayList<String>()
        for (img in bean) {
            images.add(BASE_URL + img.ad_code)
        }
        banner.setImageLoader(GlideImageLoader())
        banner.setImages(images)
        banner.start()
        banner.setOnBannerListener {
            if (bean[it].goods_id.isNotEmpty()) {
                GoodsDetail2Activity.actionStart(context, bean[it].goods_id)
            }
        }
    }
}