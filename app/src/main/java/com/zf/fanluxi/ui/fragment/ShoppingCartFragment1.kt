package com.zf.fanluxi.ui.fragment

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.*
import com.zf.fanluxi.mvp.contract.CartListContract
import com.zf.fanluxi.mvp.contract.CartOperateContract
import com.zf.fanluxi.mvp.contract.CommendContract
import com.zf.fanluxi.mvp.presenter.CartListPresenter
import com.zf.fanluxi.mvp.presenter.CartOperatePresenter
import com.zf.fanluxi.mvp.presenter.CommendPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.activity.ConfirmOrderActivity
import com.zf.fanluxi.ui.adapter.CartGoodsAdapter1
import com.zf.fanluxi.ui.adapter.CommendAdapter
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.RecDecoration
import com.zf.fanluxi.view.dialog.DeleteCartDialog
import com.zf.fanluxi.view.dialog.InputNumDialog
import com.zf.fanluxi.view.popwindow.CartSpecPopupWindow
import com.zf.fanluxi.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shoping_cart.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * 购物车页面
 * 区分商家
 */
class ShoppingCartFragment1 : BaseFragment(), CartListContract.View, CartOperateContract.View, CommendContract.View {

    override fun appSignSuccess(bean: AppSignBean) {
    }

    override fun setMe(bean: MeBean) {
    }

    //推荐为空
    override fun setEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    //推荐没有更多数据
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    //推荐刷新失败
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //推荐刷新
    override fun setRefreshCommend(bean: CommendBean) {
        refreshLayout.setEnableLoadMore(true)
        commendData.clear()
        commendData.addAll(bean.goods_list)
        commendAdapter.notifyDataSetChanged()
    }

    //推荐加载更多
    override fun setLoadMoreCommend(bean: CommendBean) {
        commendData.addAll(bean.goods_list)
        commendAdapter.notifyDataSetChanged()
    }

    //根据规格获取商品信息
    override fun setSpecInfo(bean: GoodsSpecInfo) {
        cartData[mGoodsPos].goods_price = bean.price
        bean.spec_img?.let {
            cartData[mGoodsPos].goods.original_img = bean.spec_img
        }
        cartData[mGoodsPos].spec_key_name = bean.key_name
        cartAdapter.notifyDataSetChanged()

        //更新popWindow
        popWindow?.update()
    }

    private var popWindow: CartSpecPopupWindow? = null

    /** 获取商品规格 */
    override fun setGoodsSpec(specBean: List<List<SpecBean>>) {
        val specList = ArrayList<SpecCorrect>()
        specBean.forEach {
            if (it.isNotEmpty()) {
                specList.add(SpecCorrect(it[0].name, it, ""))
            }
        }
        popWindow = object : CartSpecPopupWindow(
            activity as Activity,
            R.layout.pop_order_style,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            cartData[mGoodsPos],
            specList
        ) {}
        popWindow?.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        popWindow?.onNumberListener = {
            cartData[mGoodsPos].goods_num = it
            cartAdapter.notifyDataSetChanged()
            cartOperatePresenter.requestCount(cartData[mGoodsPos].id, it)
        }
        popWindow?.onSpecListener = { specId ->
            //确定 (规格回调)
            mSpecId = specId
            cartData[mGoodsPos].spec_key = specId
            cartOperatePresenter.requestChangeSpec(cartData[mGoodsPos].id, specId)
        }
    }

    //修改的规格id
    private var mSpecId = ""

    /** 修改商品规格 */
    override fun setChangeSpec(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
        cartOperatePresenter.requestSpecInfo(mSpecId, cartData[mGoodsPos].goods.goods_id)
    }

    /** 删除购物车 */
    override fun setDeleteCart(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
        //重组数据
        val goodsList = ArrayList<CartGoodsList>()
        for (shop in cartData) {
            if (shop.selected == "0") {
                goodsList.add(shop)
            }
        }
        cartData.clear()
        cartData.addAll(goodsList)
        cartAdapter.notifyDataSetChanged()
        if (cartData.isEmpty()) {
            mLayoutStatusView?.showEmpty()
            settleLayout.visibility = View.GONE
            management.visibility = View.GONE
        }
    }

    /** 全选状态 */
    override fun setCheckAll(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    /** 勾选状态 */
    override fun setSelect(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    /** 修改商品数量 */
    override fun setCount(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    //购物车操作失败
    override fun cartOperateError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //购物车为空
    override fun setCartEmpty() {
        settleLayout.visibility = View.GONE
        management.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
        commendPresenter.requestCommend("is_recommend", 1)
        ifCartComplete = true
    }

    //购物车加载完全
    override fun setCartLoadComplete() {
        commendPresenter.requestCommend("is_recommend", 1)
        ifCartComplete = true
    }

    override fun showCartError(msg: String, errorCode: Int) {
        settleLayout.visibility = View.GONE
        management.visibility = View.GONE
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    //加载下一页失败
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //购物车刷新成功
    override fun setRefreshCart(bean: CartBean) {
        refreshLayout.setEnableLoadMore(true)
        settleLayout.visibility = View.VISIBLE
        management.visibility = View.VISIBLE
        mLayoutStatusView?.showContent()
        cartData.clear()
        cartData.addAll(bean.list)
        cartAdapter.notifyDataSetChanged()
        price.text = "¥${bean.cart_price_info?.total_fee}"
        allChoose.isChecked = 1 == bean.selected_flag?.all_flag
    }

    //加载下一页成功
    override fun setLoadMoreCart(bean: CartBean) {
        cartData.addAll(bean.list)
        cartAdapter.notifyDataSetChanged()

        price.text = "¥${bean.cart_price_info?.total_fee}"
        allChoose.isChecked = 1 == bean.selected_flag?.all_flag
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun getInstance(): ShoppingCartFragment1 {
            return ShoppingCartFragment1()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_shoping_cart

    private var ifCartComplete = false

    private var mGoodsPos = 0

    //购物车适配器
    private var cartData = ArrayList<CartGoodsList>()

    private val commendData = ArrayList<CommendList>()
    private val commendAdapter by lazy { CommendAdapter(context, commendData) }

    private val cartAdapter by lazy { CartGoodsAdapter1(context, cartData) }
    private val cartListPresenter by lazy { CartListPresenter() }
    private val cartOperatePresenter by lazy { CartOperatePresenter() }

    private val commendPresenter by lazy { CommendPresenter() }

    private val divider by lazy {
        RecyclerViewDivider(
            context,
            LinearLayoutManager.VERTICAL,
            DensityUtil.dp2px(5f),
            ContextCompat.getColor(context!!, R.color.colorBackground)
        )
    }

    private fun initCart() {
        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.adapter = cartAdapter
        cartRecyclerView.addItemDecoration(divider)

        //推荐
        //推荐
        recommendRecyclerView.layoutManager = GridLayoutManager(context, 2)
        recommendRecyclerView.adapter = commendAdapter
        recommendRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(15f)))

    }

    override fun lazyLoad() {
        if (cartData.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        refreshLayout.setNoMoreData(false)
        refreshLayout.setEnableLoadMore(false)
        cartListPresenter.requestCartList(1)

    }

    override fun initView() {
        commendPresenter.attachView(this)
        cartOperatePresenter.attachView(this)
        cartListPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView


        //购物车 推荐
        initCart()


    }

    override fun initEvent() {

        RxBus.getDefault().subscribe<String>(this, UriConstant.FRESH_CART) {
            lazyLoad()
        }

        /** 全选反选按钮 */
        allChoose.setOnClickListener {
            cartData.forEach { shopList ->
                shopList.selected = if (allChoose.isChecked) "1" else "0"
            }
            cartAdapter.notifyDataSetChanged()
            cartOperatePresenter.requestCheckAll(if (allChoose.isChecked) 1 else 2)
        }

        /** 选中商品回调 */
        cartAdapter.checkListener = {
            val json = ArrayList<CartCheckBean>()
            var sum = 0
            cartData.forEach { it ->
                val cartBean = CartCheckBean()
                cartBean.id = it.id
                cartBean.selected = it.selected
                json.add(cartBean)
                if (it.selected == "1") sum += 1
            }
            allChoose.isChecked = sum == cartData.size
            val body =
                RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(json))
            cartOperatePresenter.requestSelect(body)
        }

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            if (ifCartComplete) {
                commendPresenter.requestCommend("is_recommend", null)
            } else {
                cartListPresenter.requestCartList(null)
            }
        }

        /** 更改商品数量加减*/
        cartAdapter.onCountListener = {
            cartOperatePresenter.requestCount(it.id, it.sum)
        }

        /** 商品数量*/
        cartAdapter.onInputListener = { bean ->
            InputNumDialog.showDialog(childFragmentManager, bean.sum)
                .onNumListener = { num ->
                cartOperatePresenter.requestCount(bean.id, num)
                bean.goodsPosition?.let { goodsPos ->
                    cartData[goodsPos].let {
                        it.goods_num = num
                    }

                }
                cartAdapter.notifyDataSetChanged()
            }
        }

        /** 商品规格 */
        cartAdapter.onSpecListener = { goodsPos ->
            //            mShopPos = shopPos
            mGoodsPos = goodsPos
            cartOperatePresenter.requestGoodsSpec(cartData[goodsPos].goods.goods_id)
        }

        settle.setOnClickListener { _ ->
            //获取选中的id
            if (management.isSelected) {
                /**
                 *  删除
                 *  如果未勾选到商品，不能删除
                 */
                var sum = 0
                cartData.forEach { shop ->
                    if (shop.selected == "1") sum += 1
                }
                if (sum < 1) {
                    showToast("请先选择商品")
                    return@setOnClickListener
                }
                DeleteCartDialog.showDialog(childFragmentManager, 1)
                    .onConfirmListener = {
                    val deleteList = ArrayList<HashMap<String, String>>()
                    cartData.forEach { goods ->
                        if (goods.selected == "1") {
                            val map = HashMap<String, String>()
                            map["id"] = goods.id
                            deleteList.add(map)
                        }
                    }
                    val body =
                        RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(deleteList))
                    cartOperatePresenter.requestDeleteCart(body)
                }

            } else {
                /**
                 * 结算  如果未勾选到商品，不能结算 */
                var sum = 0
                cartData.forEach { goods ->
                    if (goods.selected == "1") sum += 1
                }
                if (sum > 0) {
                    ConfirmOrderActivity.actionStart(context, 0, "", "", "", "", "")
                } else {
                    showToast("请先选择商品")
                }
            }
        }

        //管理
        management.setOnClickListener {
            management.isSelected = !management.isSelected
            if (management.isSelected) {
                settle.text = "删除"
                management.text = "完成"
                price.visibility = View.INVISIBLE
                totalTxt.visibility = View.INVISIBLE
            } else {
                settle.text = "结算"
                management.text = "管理"
                price.visibility = View.VISIBLE
                totalTxt.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroy() {
        cartListPresenter.detachView()
        commendPresenter.detachView()
        cartOperatePresenter.detachView()
        super.onDestroy()
    }


}