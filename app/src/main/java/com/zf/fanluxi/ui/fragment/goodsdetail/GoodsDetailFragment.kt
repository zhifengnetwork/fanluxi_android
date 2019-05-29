package com.zf.fanluxi.ui.fragment.goodsdetail

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.fanluxi.MyApplication
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.livedata.UserInfoLiveData
import com.zf.fanluxi.mvp.bean.*
import com.zf.fanluxi.mvp.contract.GoodsDetailContract
import com.zf.fanluxi.mvp.presenter.GoodsDetailPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.activity.*
import com.zf.fanluxi.ui.adapter.GoodsDetailAdapter
import com.zf.fanluxi.ui.adapter.GoodsSpecsAdapter
import com.zf.fanluxi.ui.adapter.GuideAdapter
import com.zf.fanluxi.ui.adapter.LoveShopGoodsAdapter
import com.zf.fanluxi.ui.fragment.graphic.GraphicFragment
import com.zf.fanluxi.ui.fragment.graphic.OrderAnswerFragment
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.LogUtils
import com.zf.fanluxi.utils.TimeUtils
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.popwindow.RegionPopupWindow
import com.zf.fanluxi.view.recyclerview.HorizontalPageLayoutManager
import com.zf.fanluxi.view.recyclerview.PagingScrollHelper
import kotlinx.android.synthetic.main.fragment_goods_detail2.*
import kotlinx.android.synthetic.main.layout_buy.*
import kotlinx.android.synthetic.main.layout_detail_goods.*
import kotlinx.android.synthetic.main.layout_graphic.*
import kotlinx.android.synthetic.main.pop_detail_address.view.*
import kotlinx.android.synthetic.main.pop_detail_specs.view.*

class GoodsDetailFragment : BaseFragment(), GoodsDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    //商品详情
    override fun getGoodsDetail(bean: GoodsDetailBean) {
        mData = bean
        //加载轮播图
        initBanner(bean.goods.goods_images)
        //加载界面数据
        loadData(bean)
        //图文详情
        Graphic(bean.goods_content)
        //获得为您推荐商品列表
        presenter.requestRecommendGoods(bean.goods.cat_id)
        //请求规格
        presenter.requestGoodsSpec(mGoodsId)

        RxBus.getDefault().post(UriConstant.UPDATE_COUNT_INFO, UriConstant.UPDATE_COUNT_INFO)
    }

    //秒杀商品详情
    override fun setSecKillDetail(bean: SecKillDetailBean) {
        //加载轮播图
        initBanner(bean.info.goods_images)
        //界面赋值
        loadSecKill(bean)
        //图文详情
        Graphic(bean.goods_content)
        //获得为您推荐商品列表
        presenter.requestRecommendGoods(bean.info.cat_id)
    }

    //获得商品评论（默认15条 全部 第一页）
    override fun setGoodEva(bean: List<GoodEvaList>) {

    }

    //商品属性
    override fun getGoodsAttr(bean: List<AttriBute>) {

    }

    //地址列表
    override fun getAddress(bean: List<AddressBean>) {
        mAddress.clear()
        mAddress.addAll(bean)
        popAdapter.notifyDataSetChanged()
        //默认地址 邮费
        if (mAddress.isNotEmpty()) {
            for (i in 0 until mAddress.size) {
                if (mAddress[i].is_default == "1") {
                    addressId = mAddress[i].address_id
                    goods_address.text = mAddress[i].province_name + mAddress[i].city_name + mAddress[i].district_name
                    //邮费请求
                    presenter.requestGoodsFreight(mGoodsId, mAddress[i].city, "1")
                }
            }
        } else {
            goods_address.text = "请选择配送地址"
        }

    }


    //运费
    override fun getGoodsFreight(bean: GoodsFreightBean) {
        if (bean.freight == "0.00" || bean.freight == "0") fare.text = "运费：免邮费" else fare.text = "运费：" + bean.freight
    }

    //商品规格
    override fun getGoodsSpce(bean: List<List<GoodsSpecBean>>) {
        mSpec.clear()
        mSpec.addAll(bean)
        specsAdapter.notifyDataSetChanged()
        //重组ID
        for (i in 0 until mSpec.size) {
            itemId = if (i == 0) {
                mSpec[i][0].id
            } else {
                itemId + "_" + mSpec[i][0].id
            }
            //名字
            itemName = itemName + mSpec[i][0].item + " "
        }
        if (itemId != "") {
            specs.text = itemName
            presenter.requestPricePic(itemId, mGoodsId)
        }
    }

    //关注商品
    override fun setCollectGoods(msg: String) {
        showToast(msg)
        LogUtils.e(">>1")
        RxBus.getDefault().post(UriConstant.UPDATE_COUNT_INFO, UriConstant.UPDATE_COUNT_INFO)
    }

    //取消关注
    override fun delCollectGoods(msg: String) {
        showToast(msg)
        RxBus.getDefault().post(UriConstant.UPDATE_COUNT_INFO, UriConstant.UPDATE_COUNT_INFO)
    }

    //加入购物车
    override fun addCartSuccess(msg: String) {
        showToast(msg)
        specsPopWindow.onDismiss()
        cart.isChecked = true
        RxBus.getDefault().post(UriConstant.FRESH_CART, UriConstant.FRESH_CART)
    }

    //根据规格key获取图片，库存
    override fun getPricePic(bean: GoodsSpecInfo) {
        shop_price.text = bean.price
        mPrice = bean
        if (!no_off) {
            specsPopWindow.updata()
        }
    }

    //为您推荐
    override fun getRecommendGoods(bean: List<CommendList>) {
        mCommend.clear()
        mCommend.addAll(bean)
        adapter.notifyDataSetChanged()
        val sum = if (mCommend.size % 8 != 0) {
            mCommend.size / 8 + 1
        } else {
            mCommend.size / 8
        }
        //滑动指示器
        //先写四页，后面再改
        repeat(sum) {
            val view = View(context)
            view.background = ContextCompat.getDrawable(context!!, R.drawable.selector_same_indicator)
            val lp = LinearLayout.LayoutParams(DensityUtil.dp2px(5f), DensityUtil.dp2px(5f))
            lp.setMargins(DensityUtil.dp2px(3f), 0, DensityUtil.dp2px(3f), 0)
            indicatorLayout.addView(view, lp)
            indicatorLayout[0].isSelected = true
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    //传递过来的id
    private var mGoodsId = ""
    //传递过来的类型
    private var mType: String = ""
    //记录用户选择的地址ID
    private var addressId = ""
    //传递过来的规格ID
    private var itemId = ""
    //传递过来的规格名字
    private var itemName = ""
    //判断是点击加入购物车还是立即购买
    private var isChoice = true

    //接收地址列表
    private var mAddress = ArrayList<AddressBean>()
    //详细商品规格信息
    private var mPrice: GoodsSpecInfo? = null
    private val presenter by lazy { GoodsDetailPresenter() }
    //地址pop弹窗
    private lateinit var addressPopWindow: RegionPopupWindow
    //pop地址适配器
    private val popAdapter by lazy { GoodsDetailAdapter(MyApplication.context, mAddress) }
    //商品规格
    private var mSpec = ArrayList<List<GoodsSpecBean>>()
    //商品规格pop弹窗
    private lateinit var specsPopWindow: RegionPopupWindow
    //pop商品规格适配器
    private val specsAdapter by lazy { GoodsSpecsAdapter(MyApplication.context, mSpec) }
    //第一次打开pop商品规格默认请求
    private var no_off = true
    //点击规格弹出弹窗 显示加入购物车按钮
    private var mWhether = true
    //为你推荐
    private var mCommend = ArrayList<CommendList>()

    private val adapter by lazy { LoveShopGoodsAdapter(context, mCommend) }
    //商品详情
    var mData: GoodsDetailBean? = null

    companion object {

        fun newInstance(id: String, type: String): GoodsDetailFragment {
            val fragment = GoodsDetailFragment()
            fragment.mGoodsId = id
            fragment.mType = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_goods_detail2
    private val scrollHelper = PagingScrollHelper()
    override fun initView() {
        presenter.attachView(this)

        /** 给价格添加中划线 */
        market_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        floatingButton.hide()
        //图文详情
        Graphic("")
        //为您推荐
        initSame()

    }

    override fun lazyLoad() {
        //判断是 秒杀 团购 普通
        if (mType == "") {
            //请求商品详情
            presenter.requestGoodsDetail(mGoodsId)
        } else {
            presenter.requestSecKillDetail(mType)
        }

        //请求地址列表
        presenter.requestAddress()

    }


    override fun initEvent() {

        service.setOnClickListener {
            ServiceActivity.actionStart(context)
        }

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            //回到顶部按钮
            if (scrollY - oldScrollY > 0) {
                floatingButton.hide()
            } else {
                floatingButton.show()
            }

        }

        floatingButton.setOnClickListener {
            scrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP)
        }

        //收藏按钮
        collect.setOnClickListener {
            if (collect.isChecked) {
                presenter.requestCollectGoods(mData?.goods?.goods_id.toString())

            } else {
                presenter.requestDelCollectGoods(mData?.goods?.goods_id.toString())
            }
        }

        /**选择配送地址*/
        goods_address.setOnClickListener {
            addressPopWindow = object : RegionPopupWindow(
                activity as Activity, R.layout.pop_detail_address,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {

                    contentView?.apply {
                        pop_recyclerView.layoutManager = LinearLayoutManager(context)
                        pop_recyclerView.adapter = popAdapter
                        //将所选的的地址信息传递过来/计算出邮费
                        popAdapter.mClickListener = {
                            goods_address.text = it.province_name + it.city_name + it.district_name
                            //记录所选地址id
                            addressId = it.address_id
                            //邮费请求
                            presenter.requestGoodsFreight(mGoodsId, it.city, "1")
                            //关闭弹窗
                            addressPopWindow.onDismiss()
                        }
                        //新增地址
                        add_tv.setOnClickListener {
                            AddressEditActivity.actionStart(context, null)
                        }
                    }
                }
            }
            //更新视图
//            addressPopWindow.updata()
            addressPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }
        /**选择规格*/
        specs_ly.setOnClickListener {
            mWhether = true
            popWindow()
        }
        /**加入购物车*/
        shop_cat.setOnClickListener {
            mWhether = false
            isChoice = true
            popWindow()
        }
        /**立即购买*/
        shop_buy.setOnClickListener {
            mWhether = false
            isChoice = false
            popWindow()
        }
        /**购物车复选框*/
        cart.setOnClickListener {
            cart.isChecked = !cart.isChecked
            MainActivity.actionStart(context, 2)
        }
        //评价
        evaluate.setOnClickListener {
            GoodsDetail2Activity.actionStart(context, mGoodsId, index = 1)
        }
        //为您推荐监听回调
        adapter.mClickListener = {
            //RxBus.getDefault().post(it,GoodsDetailActivity.FRESH_ORDER)
            GoodsDetail2Activity.actionStart(context, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        countDownTimer?.cancel()
        countDownTimer = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            presenter.requestAddress()
        }
    }

    /**Banner轮播图*/
    private fun initBanner(goodsImg: List<String>) {

        val images = ArrayList<String>()
        val imageViews = ArrayList<ImageView>()
        //轮播图获取 给图片加头部
        for (i in 0 until goodsImg.size) {
            images.add(UriConstant.BASE_URL + goodsImg[i])
        }
        repeat(images.size) { pos ->
            val img = ImageView(context)

            Glide.with(this).load(images[pos]).into(img)

            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${images.size}"

        /** 滑动改变指示器 */
        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${images.size}"
            }
        })
    }

    //定时器
    private var countDownTimer: CountDownTimer? = null

    /**普通商品详情*/
    private fun loadData(data: GoodsDetailBean) {
        /**团购商品*/
        if (data.goods.prom_type == 2) {
            //商品抢购价格
            label_money.visibility = View.VISIBLE
            label_money.text = "团购价"
            shop_price.text = data.goods.prom_price
            //本店原价
            price.text = "原价"
            market_price.text = "￥" + data.goods.shop_price
            //计时器
            label_ly.visibility = View.VISIBLE
            activity_name.text = "限时团购"
            //当前时间戳
            val currentTime = System.currentTimeMillis()
            if (currentTime < data.goods.end_time * 1000) {
                val tickTime = (data.goods.end_time * 1000) - currentTime
                countDownTimer?.cancel()
                countDownTimer = null
                countDownTimer = object : CountDownTimer(tickTime, 1000) {
                    override fun onFinish() {

                    }

                    override fun onTick(millisUntilFinished: Long) {
                        label_time.text = TimeUtils.getCountTime2(millisUntilFinished)
                    }

                }
                countDownTimer?.start()
            } else {
                //过时，秒杀已到
                //倒计时
                label_ly.visibility = View.GONE
//                start()
            }
        } else {
            //商品价格
            shop_price.text = data.goods.shop_price
            //市场价格
            market_price.text = "￥" + data.goods.market_price
        }
        /**签到免费领*/
        if (data.goods.sign_free_receive == 1) {
            //隐藏加入购物车按钮
            shop_cat.visibility = View.GONE
            //改变立即购买 文字 背景
            shop_buy.text = "免费领取"
            shop_buy.setBackgroundColor(Color.rgb(218, 42, 32))
        }
        //商品名字
        goods_name.text = data.goods.goods_name
        //销量
        virtual_sales_sum.text = "销量：" + data.goods.virtual_collect_sum
        //是否收藏
        if (data.goods.is_collect == 1) {
            collect.isChecked = true
        }
        //是否在购物车
        if (data.goods.is_cart == 1) {
            cart.isChecked = true
        }
        //好评率
        comment_fr.text = data.goods.comment_fr.high_rate
        //总评数
        total_sum.text = data.goods.comment_fr.total_sum
        //地址
        adress.text = data.goods.seller_info.province_name + data.goods.seller_info.city_name
    }

    /**秒杀商品详情*/
    private fun loadSecKill(data: SecKillDetailBean) {

        //商品名字
        goods_name.text = data.info.goods_name
        //商品抢购价格
        label_money.visibility = View.VISIBLE
        label_money.text = "抢购价"
        shop_price.text = data.info.price
        //地址
        adress.text = data.seller_info.province_name + data.seller_info.city_name
        //本店原价
        price.text = "原价"
        market_price.text = "￥" + data.info.shop_price
        //销量
        virtual_sales_sum.text = "销量：" + data.info.sales_sum
        //计时器
        label_ly.visibility = View.VISIBLE
        activity_name.text = "限时抢购"
        //当前时间戳
        val currentTime = System.currentTimeMillis()
        if (currentTime < data.info.end_time * 1000) {
            val tickTime = (data.info.end_time * 1000) - currentTime
            countDownTimer?.cancel()
            countDownTimer = null
            countDownTimer = object : CountDownTimer(tickTime, 1000) {
                override fun onFinish() {

                }

                override fun onTick(millisUntilFinished: Long) {
                    label_time.text = TimeUtils.getCountTime2(millisUntilFinished)
                }

            }
            countDownTimer?.start()
        } else {
            //过时，秒杀已到
            //倒计时
            label_ly.visibility = View.GONE
//                start()
        }

    }


    /**
     * 为您推荐
     * pageRecyclerView
     */
    private fun initSame() {
        val manager = HorizontalPageLayoutManager(2, 4)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        scrollHelper.setUpRecycleView(recyclerView)
        scrollHelper.updateLayoutManger()
        scrollHelper.setOnPageChangeListener { pos ->
            indicatorLayout?.apply {
                repeat(this.childCount) {
                    //indicatorLayout可能出现空
                    this[it].isSelected = pos == it
                }
            }
        }

    }

    /**pop弹窗 加入购物车 立即购买*/
    private fun popWindow() {
        specsPopWindow = object : RegionPopupWindow(
            activity as Activity, R.layout.pop_detail_specs,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ) {
            @SuppressLint("SetTextI18n")
            override fun initView() {

                var sum = contentView?.card_number?.text.toString().toInt()
                //第一次打开默认请求第一个
                if (no_off) {
                    no_off = if (itemId != "") {
                        itemName = "已选择:$itemName"
                        false
                    } else {
                        true
                    }
                }
                contentView?.apply {
                    specs_rl.layoutManager = LinearLayoutManager(context)
                    specs_rl.adapter = specsAdapter
                    del_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.reduce))

                    card_number.setText(specs_sum.text.split("件")[0])
                    /**显示/隐藏确认键*/
                    if (mWhether) {
                        specs_ly.visibility = View.VISIBLE
                        specs_btn.visibility = View.GONE
                    } else {
                        specs_ly.visibility = View.GONE
                        specs_btn.visibility = View.VISIBLE
                    }
                    //没有规格的商品 根据no_off判断
                    if (no_off) {
                        //图片
                        GlideUtils.loadUrlImage(
                            context,
                            UriConstant.BASE_URL + mData?.goods?.original_img,
                            goods_img
                        )
                        //名称
//                            goodsName.text = mData?.goods?.goods_name
                        //价格
                        goods_price.text = mData?.goods?.shop_price
                        //规格
                        goods_stock.text = "请选择:数量"
                        pop_view.visibility = View.INVISIBLE
                    } else {
                        //图片
                        GlideUtils.loadUrlImage(context, mPrice?.spec_img, goods_img)
                        //名称
//                            goodsName.text = mData?.goods?.goods_name
                        //价格
                        goods_price.text = mPrice?.price
                        //规格
                        goods_stock.text = itemName
                        pop_view.visibility = View.VISIBLE

                        /**选择后详情页数据随之改变*/
                        //详情页价格
                        shop_price.text = mPrice?.price
                        //详情页规格
                        specs.text = itemName.split(":")[1]
                    }
                    //输入文本框
                    card_number.addTextChangedListener {
                        val text = it.toString()
                        val len = it.toString().length
                        if (len > 1 && text.startsWith("0")) {
                            it?.replace(0, 1, "")
                        }
                        sum = if (text == "") {
                            card_number.setText("1")
                            1
                        } else {
                            text.toInt()
                        }
                        if (sum > 1) {
                            del_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.reduce_b))
                        }
                        specs_sum.text = sum.toString() + "件"
                    }
                    //点击减少数量
                    del_btn.setOnClickListener {
                        sum -= 1
                        if (sum <= 0) {
                            sum = 1
                        }
                        if (sum == 1) {
                            del_btn.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.reduce
                                )
                            )
                        }
                        card_number.setText(sum.toString())

                    }
                    //点击增加数量
                    add_btn.setOnClickListener {
                        sum += 1
                        card_number.setText(sum.toString())
                        del_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.reduce_b))
                    }
                    /**确认按钮 加入购物车/立即购买*/
                    specs_btn.setOnClickListener {
                        if (isChoice) {
                            //商品ID 数量（默认1） 规格ID
                            presenter.requestAddCart(mData?.goods?.goods_id.toString(), sum, itemId)
                        } else {
                            /**判断用户是否已选配送地址*/
                            if (addressId == "") {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("你还没有选择收货地址")
                                builder.setMessage("是否去添加收货地址")
                                builder.setPositiveButton("是") { dialog, which ->
                                    startActivityForResult(Intent(context, AddressEditActivity::class.java), 1)
                                    dialog.dismiss()
                                }
                                builder.setNegativeButton("否") { dialog, which ->
                                    dialog.dismiss()
                                }
                                builder.show()
                            } else {
                                if (UserInfoLiveData.value?.is_distribut == "0" && mData?.goods?.sign_free_receive == 1) {
                                     showToast("成为分销商才能领取")
                                } else {
                                    ConfirmOrderActivity.actionStart(
                                        context,
                                        0,
                                        "1",
                                        mData?.goods?.goods_id.toString(),
                                        sum.toString(),
                                        itemId,
                                        "",
                                        addressId = addressId
                                    )
                                }
                            }
                            specsPopWindow.onDismiss()
                        }

                    }
                    /**加入购物车*/
                    shop_cat.setOnClickListener {
                        //商品ID 数量（默认1） 规格ID
                        presenter.requestAddCart(mData?.goods?.goods_id.toString(), sum, itemId)
                        specsPopWindow.onDismiss()
                    }
                    /**立即购买*/
                    shop_buy.setOnClickListener {
                        /**判断用户是否已选配送地址*/
                        if (addressId == "") {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("你还没有选择收货地址")
                            builder.setMessage("是否去添加收货地址")
                            builder.setPositiveButton("是") { dialog, which ->
                                AddressEditActivity.actionStart(context, null)
                                dialog.dismiss()
                            }
                            builder.setNegativeButton("否") { dialog, which ->
                                dialog.dismiss()
                            }
                            builder.show()
                        } else {
                            ConfirmOrderActivity.actionStart(
                                context,
                                0,
                                "1",
                                mData?.goods?.goods_id.toString(),
                                sum.toString(),
                                itemId,
                                "",
                                addressId = addressId
                            )
                        }
                        specsPopWindow.onDismiss()
                    }

                    //关闭弹窗
                    close_btn.setOnClickListener {
                        specsPopWindow.onDismiss()
                    }

                }
                //适配器监听回调
                specsAdapter.mClickListener = { id: String, name: String ->
                    itemId = id
                    itemName = "已选择:$name"
                    presenter.requestPricePic(itemId, mData?.goods?.goods_id.toString())
                }
            }

        }
        specsPopWindow.updata()
        specsPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
    }

    /**图文详情*/
    private fun Graphic(text: String) {
        val fmgs = arrayListOf(
            GraphicFragment.newInstance(text) as Fragment,
            OrderAnswerFragment.newInstance(mGoodsId) as Fragment
        )

        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("商品详情", 0, 0))
        entitys.add(TabEntity("规格参数", 0, 0))
        graphic_tab.setTabData(entitys, activity, R.id.graphic_fl, fmgs)
    }

}