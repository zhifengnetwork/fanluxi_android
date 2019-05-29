package com.zf.fanluxi.ui.fragment.focus

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.CommendList
import com.zf.fanluxi.mvp.bean.MyFollowBean
import com.zf.fanluxi.mvp.bean.MyFollowList
import com.zf.fanluxi.mvp.contract.MyFollowContract
import com.zf.fanluxi.mvp.presenter.MyFollowPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.FocusGoodsAdapter
import com.zf.fanluxi.ui.adapter.FocusLoveGoodsAdapter
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.RecDecoration
import com.zf.fanluxi.view.recyclerview.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_focus_goods.*

class FocusGoodsFragment : BaseFragment(), MyFollowContract.View {


    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    //商品关注列表(第一页)
    override fun getMyFollowSuccess(bean: MyFollowBean) {
        mData.clear()
        mData.addAll(bean.list)
        goods_sum.text = bean.count
        goodsAdapter.notifyDataSetChanged()
        love_goods_ly.visibility = View.GONE
    }

    //获得之后页数据
    override fun setFollowLoadMore(bean: List<MyFollowList>) {
        mData.addAll(bean)
        goodsAdapter.notifyDataSetChanged()
    }

    //获得第一页猜你喜欢商品
    override fun getLoveGoods(bean: List<CommendList>) {
        loveData.clear()
        loveData.addAll(bean)
        loveAdapter.notifyDataSetChanged()
    }

    //获得之后页猜你喜欢商品
    override fun setGoodsLoadMore(bean: List<CommendList>) {
        loveData.addAll(bean)
        loveAdapter.notifyDataSetChanged()
    }

    //当第一页数据为空时
    override fun freshEmpty() {
//        mLayoutStatusView?.showEmpty()
//        refreshLayout.setEnableLoadMore(false)
        goods_sum.text = "0"
        mData.clear()
        goodsAdapter.notifyDataSetChanged()
    }

    //实际获得数据小于一页最大数据时 加载完成
    override fun setLoadFollowComplete() {
        switch = false
        //显示推荐商品布局
        love_goods_ly.visibility = View.VISIBLE
        presenter.requsetLoveGoods("is_recommend", 1)
    }

    override fun setLoadGoodsComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    //下拉加载错误
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setBuyError(msg: String) {

    }

    //删除关注商品
    override fun delCollectGoods(msg: String) {
        showToast(msg)
        refreshLayout.setNoMoreData(false)
        lazyLoad()
        love_goods_ly.visibility = View.GONE
        RxBus.getDefault().post(UriConstant.UPDATE_COUNT_INFO, UriConstant.UPDATE_COUNT_INFO)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun newInstance(): FocusGoodsFragment {
            return FocusGoodsFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_goods

    //已关注的商品列表
    private val goodsAdapter by lazy { FocusGoodsAdapter(context, mData) }

    //猜你喜欢的商品列表
    private val loveAdapter by lazy { FocusLoveGoodsAdapter(context, loveData) }

    private val presenter by lazy { MyFollowPresenter() }
    //接收网络数据值
    private var mData = ArrayList<MyFollowList>()

    private var loveData = ArrayList<CommendList>()
    //判断是否还需要请求关注列表
    private var switch = true

    override fun initView() {
        presenter.attachView(this)
        //已关注商品的列表
        goodsRecyclerView.layoutManager = LinearLayoutManager(context)
        goodsRecyclerView.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(context))
        goodsRecyclerView.adapter = goodsAdapter


        //猜你喜欢的商品
        val manager = GridLayoutManager(context, 2)
        loveGoodsRecyclerView.layoutManager = manager
        loveGoodsRecyclerView.adapter = loveAdapter
        loveGoodsRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))


        /** 动态添加筛选按钮 */
//        repeat(8) {
//            val radioButton = layoutInflater.inflate(R.layout.radiobutton_tag, null) as RadioButton
//            radioButton.text = "有券$it"
//            radioButton.tag = it
//            radioGroup.addView(radioButton)
//            val spaceView = Space(context)
//            val lp = LinearLayout.LayoutParams(DensityUtil.dp2px(18f), 1)
//            spaceView.layoutParams = lp
//            radioGroup.addView(spaceView)
//        }


    }

    override fun lazyLoad() {
//        refreshLayout.setEnableLoadMore(false)
        presenter.requestMyFollow(1)
    }

    override fun initEvent() {
//        //分类
//        classify.setOnClickListener {
//            val popWindow = object : FocusClassifyPopupWindow(
//                activity as Activity, R.layout.pop_focus_classify,
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            ) {}
//            popWindow.showBashOfAnchor(classifyLayout, LayoutGravity(LayoutGravity.ALIGN_RIGHT), 0, 0)
//
//        }
//        /** 筛选按钮点击事件 */
//        radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val radioBtn = group.findViewById<RadioButton>(checkedId)
//            LogUtils.e("tag:" + radioBtn.tag)
//        }
        //删除商品点击事件
        goodsAdapter.mClickListener = {
            presenter.requestDelCollectGoods(it)
        }
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {

            if (switch) {
                presenter.requestMyFollow(null)
            } else {
                presenter.requsetLoveGoods("is_recommend", null)
            }

        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            switch = true
            lazyLoad()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}