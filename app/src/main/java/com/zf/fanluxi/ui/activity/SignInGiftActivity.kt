package com.zf.fanluxi.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.AppSignBean
import com.zf.fanluxi.mvp.bean.AppSignDayBean
import com.zf.fanluxi.mvp.contract.AppSignDayContract
import com.zf.fanluxi.mvp.presenter.AppSignDayPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.RegistrationAdapter
import com.zf.fanluxi.view.gridview.SpecialCalendar
import com.zf.fanluxi.view.popwindow.RegionPopupWindow
import kotlinx.android.synthetic.main.activity_sign_in_gift.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.pop_sign_success.view.*
import java.util.*

class SignInGiftActivity : BaseActivity(), AppSignDayContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //签到成功
    override fun appSignSuccess(bean: AppSignBean) {
        signData = bean
        window = object : RegionPopupWindow(
            this, R.layout.pop_sign_success,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ) {
            @SuppressLint("SetTextI18n")
            override fun initView() {
                contentView?.apply {
                    //当前总积分
                    points.text = signData?.points
                    //连续签到天数
                    continue_sign.text = "已经连续签到" + signData?.continue_sign + "天"
                    //签到加积分
                    add_point.text = "+" + signData?.add_point + "分"
                }
            }
        }
        window.updata()
        window.showAtLocation(parentLayout, Gravity.CENTER, 0, 0)
        //重新刷新页面
        onResume()
    }

    //签到列表
    @SuppressLint("SetTextI18n")
    override fun getAppSignDay(bean: AppSignDayBean) {
        mData = bean

//        mAdapter.notifyDataSetChanged()

        //签到按钮
        if (bean.today_sign) {
            sign_tv.text = "已签到"
            sign_tv.isEnabled = false
        } else {
            sign_tv.text = "签到"
            sign_tv.isEnabled = true
        }
        //签到加积分
        add_point.text = "+" + bean.add_point + "分"
        //连续签到天数
        continue_sign.text = bean.continue_sign + "天"
        //累计签到天数
        accumulate_day.text = bean.accumulate_day + "天"

        val mAdapter = RegistrationAdapter(this, mDays, week, mDay, year, month, mData)
        mAdapter.notifyDataSetChanged()
        calendar_gv.adapter = mAdapter
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SignInGiftActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text = "累计积分"
        back.setOnClickListener {
            finish()
        }
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_sign_in_gift

    private var mDetector: GestureDetector? = null
    private var mYear = 0//年
    private var mMonth = 0//月
    private var mDay = 0//日
    private var mDays: Int = 0
    private var week: Int = 0
    private var month: Int = 0
    private var year: Int = 0

//    private val adapter by lazy { RegistrationAdapter(this, mDays, week, mDay, year, month, mData) }

    private val presenter by lazy { AppSignDayPresenter() }

    private var mData: AppSignDayBean? = null

    private var signData: AppSignBean? = null
    //pop弹窗
    private lateinit var window: RegionPopupWindow

    override fun initData() {

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        presenter.attachView(this)

        //已签到按钮图片
        val img = resources.getDrawable(R.drawable.rili)
        img.setBounds(0, 2, 60, 60)//第一0是距左边距离，第二0是距上边距离，70分别是长宽
        sign_tv.setCompoundDrawables(img, null, null, null)

        //签到日历
        val calendar = Calendar.getInstance()
        mYear = calendar.get(Calendar.YEAR)//获取年

        year = mYear//记录当前年

        mMonth = calendar.get(Calendar.MONTH)//获取当前月份以0开头

        month = mMonth//记录当前月

        mDay = calendar.get(Calendar.DAY_OF_MONTH)// 获取当前天以（0开头）

        val mCalendar = SpecialCalendar()
        val isLeapYear = mCalendar.isLeapYear(mYear)//看是否为闰年
        mDays = mCalendar.getDaysOfMonth(isLeapYear, mMonth + 1)//得到当月一共几天
        week = mCalendar.getWeekdayOfMonth(mYear, mMonth)//得到当月第一天星期几

        calendar_gv.isClickable = false
//        calendar_gv.setPressed(false)
//        calendar_gv.setEnabled(false)
//        val mAdapter = RegistrationAdapter(this, mDays, week, mDay, year, month, mData)
//        calendar_gv.adapter = adapter//绑定适配器
//
//        adapter.notifyDataSetChanged()

        date.text = mYear.toString() + "." + (mMonth + 1)
        //左右滑动
        mDetector = GestureDetector(this, MyGesture())
        calendar_gv.isLongClickable = true


    }

    override fun initEvent() {
        //签到点击事件
        sign_tv.setOnClickListener {
            //判断是否有权限签到
            if (mData?.auth == 1) {
                //请求签到接口
                presenter.requestAppSign()
            } else {
                showToast("您没有签到权限")
            }
        }
        //点击事件
        //上一个月
        upmonth.setOnClickListener {

            upMonth()
        }

        //下一个月
        downmonth.setOnClickListener {

            downMoth()
        }

        //左右滑动
        calendar_gv.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            mDetector!!.onTouchEvent(
                motionEvent
            )
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onResume() {
        super.onResume()
        presenter.requestAppSignDay()
    }

    override fun start() {
        presenter.requestAppSignDay()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mDetector != null) {
            return mDetector!!.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    //下一个月方法
    @SuppressLint("SetTextI18n")
    fun downMoth() {
        month++
        if (month < 12) {
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            val mAdapter = RegistrationAdapter(this, mDays, week, mDay, year, month, mData)
            mAdapter.notifyDataSetChanged()
            calendar_gv.adapter = mAdapter
            date.text = year.toString() + "." + (month + 1)
        } else {
            month = 0
            year++
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            val mAdapter = RegistrationAdapter(this, mDays, week, mDay, year, month, mData)
            mAdapter.notifyDataSetChanged()
            calendar_gv.adapter = mAdapter
            date.text = year.toString() + "." + (month + 1)
        }

    }

    //上一个月实现方法
    @SuppressLint("SetTextI18n")
    fun upMonth() {

        month--
        if (month > 0) {
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            val mAdapter = RegistrationAdapter(this, mDays, week, mDay, year, month, mData)
            mAdapter.notifyDataSetChanged()
            calendar_gv.adapter = mAdapter
            date.text = year.toString() + "." + (month + 1)
        } else {
            month = 11
            year--
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            val mAdapter = RegistrationAdapter(this, mDays, week, mDay, year, month, mData)
            calendar_gv.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
            date.text = year.toString() + "." + (month + 1)
        }

    }


    inner class MyGesture : GestureDetector.OnGestureListener {

        override fun onDown(motionEvent: MotionEvent): Boolean {
            return true
        }

        override fun onShowPress(motionEvent: MotionEvent) {

        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            return false
        }

        override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
            return false
        }

        override fun onLongPress(motionEvent: MotionEvent) {

        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1.x - e2.x > 80 && Math.abs(velocityX) > 200) {
                downMoth()

                return true
            } else if (e2.x - e1.x > 80 && Math.abs(velocityX) > 200) {
                upMonth()

                return true
            }
            return false
        }
    }
}