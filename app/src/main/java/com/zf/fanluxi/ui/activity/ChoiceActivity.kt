package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

import androidx.fragment.app.Fragment

import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.TabEntity

import com.zf.fanluxi.ui.fragment.choice.*
import com.zhouwei.mzbanner.holder.MZViewHolder

import kotlinx.android.synthetic.main.layout_choice_centre.*
import kotlinx.android.synthetic.main.layout_choice_poster.*


class ChoiceActivity:BaseActivity(){
    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,ChoiceActivity::class.java))
        }
    }
//中间导航数据
    private val mTitles = arrayOf("精选榜单", "新品速递", "CEO种草", "各国好物","更多精彩")

    private val mIconUnSelectIds = intArrayOf(
        R.drawable.choice,
        R.drawable.newpd,
        R.drawable.nice,
        R.drawable.commodity,
        R.drawable.more
    )
   private val fgms= arrayListOf(


       ListFragment.getInstance() as Fragment,
       NewFragment.getInstance() as Fragment,
       CEOFragment.getInstance() as Fragment,
       CountryFragment.getInstance() as Fragment,
       MoreFragment.getInstance() as Fragment

   )


    private val mTabEntities = ArrayList<CustomTabEntity>()

    //选中后更换的图片
//    private val mIconSelectIds = intArrayOf(
//        R.drawable.ic_sy_se,
//        R.drawable.ic_fl_se,
//        R.drawable.ic_gwc_se,
//        R.drawable.ic_wo_se,
//        R.drawable.ic_wo_se
//    )

    private var mIndex = 0

    /*---------------------------------------
底部轮播图片数据*/
    val RES  = intArrayOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )


    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            //第一个文字 第二个选择后的图标 第三个未选择的图标
            TabEntity(mTitles[it], mIconUnSelectIds[it],mIconUnSelectIds[it])

        }
        choice_tabLy.setTabData(mTabEntities)
        choice_tabLy.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {}
            override fun onTabSelect(position: Int) {

            }
        })
    }

    //底部图片轮播



    private fun initbaner() {

        //定义存图片集合
        val bannerList = java.util.ArrayList<Int>()

        for (i in RES.indices) {
            bannerList.add(RES[i])
        }
        //绑定数据
        choice_banner.setIndicatorVisible(false)//去除指示器 小圆点
        choice_banner.setPages(bannerList as List<Nothing>, { BannerViewHolder() })

    }


    class BannerViewHolder : MZViewHolder<Integer> {
        private var mImageView: ImageView? = null

        override fun createView(p0: Context?): View {
         var view=LayoutInflater.from(p0).inflate(R.layout.choice_banner_item,null)
            mImageView = view.findViewById(R.id.banner_image)
           return  view

        }
        override fun onBind(p0: Context?, p1: Int, p2: Integer?) {

            mImageView!!.setImageResource(p2!!.toInt())

            //底部轮播每个图片的点击事件
            mImageView!!.setOnClickListener {

            }
        }

    }

    override fun initToolBar() {

    }

    override fun layoutId(): Int = R.layout.activity_choice

    override fun initData() {

    }

    override fun initView() {

        //中间商品左右滑动
        initTab()
        choice_tabLy.currentTab = mIndex
        choice_tabLy.setTabData(mTabEntities,this,R.id.fl_choice,fgms)
        //底部轮播
        initbaner()

    }

    override fun initEvent() {

        //底部轮播点击事件   不知道为啥点击无效
        choice_banner.setBannerPageClickListener { view, i ->

        }

    }

    override fun start() {

    }

    override fun onPause() {
        choice_banner.pause()
        super.onPause()
    }

    override fun onResume() {
        choice_banner.start()
        super.onResume()
    }

}








