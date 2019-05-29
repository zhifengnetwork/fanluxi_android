package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.AppSignDayBean
import com.zf.fanluxi.view.gridview.SpecialCalendar
import com.zf.fanluxi.view.gridview.SquareRelativeLayout

class RegistrationAdapter(
    val context: Context,
    private val days: Int,
    private val week: Int,
    private val mday: Int,
    private val year: Int,
    private val month: Int,
    val data: AppSignDayBean?
) : BaseAdapter() {

    private val dayNumber = Array(42) { 0 }
    private var viewHolder: ViewHolder? = null
    private var tab: Int = 0//记录日期下标
    //接收日期处理方法
    private fun setDate() {
        if (data?.date != null) {
            for (e in data.date) {
                //截取年份
                val yy: Int = e.split("/")[0].toInt()
                //截取月份
                val mm: Int = e.split("/")[1].toInt() + 1
                //截取日
                val dd: Int = e.split("/")[2].toInt()
                //判断截取数据并在日历设置签到样式

                //上月
                if (tab < week) {
                    if (yy == year && mm == month && dd == dayNumber[tab]) {
                        viewHolder?.day?.setBackgroundResource(R.drawable.rili)
                        viewHolder?.sqly?.setPadding(30, 30, 30, 30)
                        viewHolder?.day?.text = ""
                        viewHolder?.back?.setBackgroundResource(R.drawable.shape_calendar_bg)
                    }
                }
                //本月
                if (tab < days + week && tab >= week) {
                    if (yy == year && mm == (month + 1) && dd == dayNumber[tab]) {
                        viewHolder?.day?.setBackgroundResource(R.drawable.rili)
                        viewHolder?.sqly?.setPadding(30, 30, 30, 30)
                        viewHolder?.day?.text = ""
                        viewHolder?.back?.setBackgroundResource(R.drawable.shape_calendar_bg)
                    }
                }
                //下月
                if (tab >= days + week) {
                    if (yy == year && mm == (month + 2) && dd == dayNumber[tab]) {
                        viewHolder?.day?.setBackgroundResource(R.drawable.rili)
                        viewHolder?.sqly?.setPadding(30, 30, 30, 30)
                        viewHolder?.day?.text = ""
                        viewHolder?.back?.setBackgroundResource(R.drawable.shape_calendar_bg)
                    }
                }
            }
        }

    }


    init {
        getEveryDay()
    }


    override fun getCount(): Int {
        return if ((week > 4 && days > 30) || (week > 5 && days > 29)) {
            42
        } else {
            35
        }
    }

    override fun getItem(i: Int): String? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return dayNumber[i].toLong()
    }//点击时

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var mView = view
        if (null == mView) {
            mView = LayoutInflater.from(context).inflate(R.layout.item_registrationadatper, null)
            viewHolder = ViewHolder(mView!!)
            mView.tag = viewHolder
        } else {
            viewHolder = mView.tag as ViewHolder
        }

        when {
            //上个月日期
            i < week -> {
                viewHolder?.day?.text = dayNumber[week - 1 - i].toString()
                viewHolder?.day?.setTextColor(Color.rgb(214, 214, 214))
            }
            //下个月日期
            i >= days + week -> {
                viewHolder?.day?.text = dayNumber[i].toString()
                viewHolder?.day?.setTextColor(Color.rgb(214, 214, 214))
            }
            //本月日期
            else -> viewHolder?.day?.text = dayNumber[i].toString()
        }


        //判断签到了的日子并显示样式
        tab = i//记录日期下标
        setDate()

        return mView
    }

    private inner class ViewHolder(view: View) {
        val day: TextView = view.findViewById(R.id.day) as TextView
        val back: LinearLayout = view.findViewById(R.id.calendar_bg) as LinearLayout
        val sqly: SquareRelativeLayout = view.findViewById(R.id.squarerly) as SquareRelativeLayout

    }

    /**
     * 得到42格子 每一格子的值
     */
    private fun getEveryDay() {
        val mCalendar = SpecialCalendar()
        val isLeapYear = mCalendar.isLeapYear(year)
        //当前月为1月时 上一个月为上一年的12月
        val mUpDays = if (month == 0) {
            val isLeapYear = mCalendar.isLeapYear(year - 1)
            mCalendar.getDaysOfMonth(isLeapYear, 12)
        } else {
            mCalendar.getDaysOfMonth(isLeapYear, month)//得到上月一共几天
        }

        var a = 1
        for (i in 0..41) {
            if (i < days + week && i >= week) {
                dayNumber[i] = i - week + 1
            } else if (i < week) {
                //上一个月日期
                dayNumber[i] = mUpDays - i
            } else if (i >= days + week) {
                //下个月日期
                dayNumber[i] = a
                a += 1
            }
        }
    }
}
