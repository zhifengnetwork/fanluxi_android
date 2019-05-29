package com.zf.fanluxi.ui.fragment.focus

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.FollowShopList
import com.zf.fanluxi.mvp.bean.MyFollowShopBean
import com.zf.fanluxi.mvp.bean.ShopList
import com.zf.fanluxi.mvp.contract.MyFollowShopContract
import com.zf.fanluxi.mvp.presenter.MyFollowShopPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.FocusShopAdapter
import com.zf.fanluxi.ui.adapter.LoveShopAdapter
import com.zf.fanluxi.view.recyclerview.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_focus_shop.*

class FocusShopFragment : BaseFragment(), MyFollowShopContract.View {
    override fun setLoadShopComplete() {
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

    //删除或添加关注
    override fun delMyFollowShop(msg: String) {
        showToast(msg)
        lazyLoad()
        love_shop_ly.visibility = View.GONE
        refreshLayout.setNoMoreData(false)
    }

    //获得第一页关注店铺列表
    override fun getMyFollowShop(bean: MyFollowShopBean) {
        mData.clear()
        mData.addAll(bean.list)
        shopSum.text = bean.count
        shopAdapter.notifyDataSetChanged()
        love_shop_ly.visibility = View.GONE
    }

    //猜你喜欢店铺列表
    override fun getShopList(bean: List<ShopList>) {
        shopData.clear()
        shopData.addAll(bean)
        loveAdapter.notifyDataSetChanged()
    }

    override fun setLoadShopMore(bean: List<ShopList>) {
        shopData.addAll(bean)
        loveAdapter.notifyDataSetChanged()
    }

    //当第一页数据为空时
    override fun freshEmpty() {
        shopSum.text = "0"
        mData.clear()
        shopAdapter.notifyDataSetChanged()

    }

    //获得之后页数据
    override fun setLoadFollowShopMore(bean: List<FollowShopList>) {
        mData.addAll(bean)
        shopAdapter.notifyDataSetChanged()
    }

    //实际获得数据小于一页最大数据时  加载完成
    override fun setLoadFollowShopComplete() {
        switch = false
        //显示推荐店铺布局
        love_shop_ly.visibility = View.VISIBLE
        presenter.requsetShopList(1, 6)
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

    companion object {
        fun newInstance(): FocusShopFragment {
            return FocusShopFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_shop

    // 关注的店铺
    private val shopAdapter by lazy { FocusShopAdapter(context, mData) }

    // 推荐的店铺
    private val loveAdapter by lazy { LoveShopAdapter(context, shopData) }

    //网络接收数据
    private var mData = ArrayList<FollowShopList>()

    private var shopData = ArrayList<ShopList>()
    //判断是否还需要请求关注列表
    private var switch = true

    private val presenter by lazy { MyFollowShopPresenter() }

    override fun initView() {
        presenter.attachView(this)


        /**  已关注店铺列表 */
        shopRecyclerView.layoutManager = LinearLayoutManager(context)
        shopRecyclerView.adapter = shopAdapter
        shopRecyclerView.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(context))

        /** 猜你喜欢店铺列表 */
        loveRecyclerView.layoutManager = LinearLayoutManager(context)
        loveRecyclerView.adapter = loveAdapter


    }

    override fun lazyLoad() {
        presenter.requestMyFollowShop(1)
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            if (switch) {
                presenter.requestMyFollowShop(null)
            } else {
                presenter.requsetShopList(null, 6)
            }


        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            switch = true
            lazyLoad()
        }
        //删除店铺
        shopAdapter.mClickListener = {
            presenter.requestDelMyFollowShop(it.seller_id, "0", it.collect_id)
        }
        //关注店铺
        loveAdapter.mClickListener={
            presenter.requestDelMyFollowShop(it,"1","")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}