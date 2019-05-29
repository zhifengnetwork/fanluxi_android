package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.DateHeadEntity
import com.zf.fanluxi.mvp.bean.MonthList
import com.zf.fanluxi.mvp.bean.MyFootBean
import com.zf.fanluxi.mvp.contract.MyFootContract
import com.zf.fanluxi.mvp.presenter.MyFootPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.FootAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.recyclerview.FloatingItemDecoration
import kotlinx.android.synthetic.main.activity_foot.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的足迹
 */
class FootActivity : BaseActivity(), MyFootContract.View {


    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    //获得足迹列表
    override fun getMyFoot(bean: List<MyFootBean>) {
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        mData.clear()
        data.addAll(bean)
        mData.addAll(data)

        //刷新初始化数据
        long = 0
//        size = 0
        monthData.clear()
        days.clear()
        keys.clear()
        monthData.addAll(getData())
//        size = monthData.size
        for (i in 0 until monthData.size) {
            keys[long] = DateHeadEntity(monthData[i].data, "")
            monthData[i].goodsList.let {
                days.addAll(it)
                long += it.size
            }
        }
        decoration.setKeys(keys)

        decoration.setmTitleHeight(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                resources.displayMetrics
            ).toInt()
        )
        adapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMore(bean: List<MyFootBean>) {
        data.addAll(bean)
        mData.addAll(data)
        //刷新初始化数据
        long = 0
//        size = 0
        monthData.clear()
        days.clear()
        keys.clear()
        monthData.addAll(getData())
//        size = monthData.size
        for (i in 0 until monthData.size) {
            keys[long] = DateHeadEntity(monthData[i].data, "")
            monthData[i].goodsList.let {
                days.addAll(it)
                long += it.size
            }
        }
        decoration.setKeys(keys)

        decoration.setmTitleHeight(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                resources.displayMetrics
            ).toInt()
        )
        adapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //编辑足迹
    override fun setMyFoot() {
        start()
        showToast("删除成功")
        RxBus.getDefault().post(UriConstant.UPDATE_COUNT_INFO, UriConstant.UPDATE_COUNT_INFO)
    }

    //清空足迹
    override fun clearMyFoot() {
        start()
        days.clear()
        adapter.notifyDataSetChanged()
        RxBus.getDefault().post(UriConstant.UPDATE_COUNT_INFO, UriConstant.UPDATE_COUNT_INFO)
        showToast("清空成功")

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, FootActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        rightLayout.visibility = View.GONE
        editOperation.visibility = View.VISIBLE
        titleName.text = "浏览记录"
        back.setOnClickListener {
            finish()
        }

        /** 点击编辑按钮 */
        editOperation.setOnClickListener {
            if (!editOperation.isSelected) {
                //编辑状态
                editOperation.isSelected = true
                operationLayout.visibility = View.VISIBLE
                editOperation.text = "完成"
                adapter.setIfCanEdit(true)
            } else {
                //不可编辑状态
                editOperation.isSelected = false
                operationLayout.visibility = View.GONE
                editOperation.text = "编辑"
                adapter.setIfAllChoose(false)
                adapter.setIfCanEdit(false)
                allChoose.isChecked = false
            }
        }


    }


    override fun layoutId(): Int = R.layout.activity_foot

    //网络请求
    private val presenter by lazy { MyFootPresenter() }

    //网络接收数据
    private var mData = ArrayList<MyFootBean>()
    private var data = ArrayList<MyFootBean>()

    private val adapter by lazy { FootAdapter(this, days) }

    private val decoration by lazy {
        FloatingItemDecoration(
            this, ContextCompat.getColor(this, R.color.colorBackground),
            2f, 12f
        )
    }

    private val keys = HashMap<Int, DateHeadEntity>()
    //    private var size = 0
    private var long = 0
    private val days = ArrayList<MyFootBean>()
    private val monthData = ArrayList<MonthList>()

    override fun initView() {
        presenter.attachView(this)

        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requesetMyFoot(null)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            presenter.requesetMyFoot(1)
        }
        adapter.setOnClickListener(object : FootAdapter.OnItemClickListener {
            override fun unCheckAll() {
                //全选
                allChoose.isChecked = false
            }

            override fun checkAll() {
                //不全选
                allChoose.isChecked = true
            }
        })

        /** 是否全部选中 */

        allChoose.setOnClickListener {
            adapter.setIfAllChoose(allChoose.isChecked)

        }

//        //删除按钮
        delete.setOnClickListener {
            //可以传实体类，存放id和position
            /** 重新请求数据渲染，如果直接移除item需要注意头布局 */
//            repeat(adapter.checkList.size) {
//                //注意！！！不是removeAt(it)
//                days.removeAt(adapter.checkList[it])
//            }
            //如果全选按钮被选中 则清空足迹
            if (allChoose.isChecked ) {
                if (days.isNotEmpty()){
                    presenter.requestclearMyFoot()
                }
            } else {
                var mId = ""
                if (adapter.checkList.isNotEmpty()) {
                    for (i in adapter.checkList.indices) {
                        mId = if (i == 0) adapter.checkList[i] else mId + "," + adapter.checkList[i]
                    }
                    adapter.checkList.clear()
                    presenter.requestsetMyFoot(mId)
                }

            }
        }

    }

    /**重组数据*/
    private fun getData(): ArrayList<MonthList> {
        val list = ArrayList<MonthList>()
        while (mData.size != 0) {
            var data = ""
            val goodsList = ArrayList<MyFootBean>()
            for (i in 0 until mData.size) {
                data = mData[0].date
                if (data == mData[i].date) {
                    goodsList.add(mData[i])
                }
            }
            list.add(MonthList(data, goodsList))
            mData.removeAll(goodsList)

        }
        return list
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requesetMyFoot(1)
    }
}