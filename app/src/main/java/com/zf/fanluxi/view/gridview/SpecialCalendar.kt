package com.zf.fanluxi.view.gridview

import java.util.Calendar

class SpecialCalendar {
    /**
     * 判断是否是闰年
     */
    fun isLeapYear(year: Int): Boolean {
        if (year % 100 == 0 && year % 400 == 0) {
            return true
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true
        }
        return false
    }

    /**
     * 得到某月多少天
     * 1357810腊三十一天永不差
     * 469冬三十日平年二月28
     * 闰年再把一天加
     */
    fun getDaysOfMonth(isLeapYear: Boolean, month: Int): Int {
        var days = 0
        when (month) {
            1 -> days = 31
            3 -> days = 31
            5 -> days = 31
            7 -> days = 31
            8 -> days = 31
            10 -> days = 31
            12 -> days = 31
            4 -> days = 30
            6 -> days = 30
            9 -> days = 30
            11 -> days = 30
            2 -> if (isLeapYear) {
                days = 29
            } else {
                days = 28
            }
        }
        return days
    }

    /**
     * 得到某年某月一号是星期几  （0-6 日-六）
     */
    fun getWeekdayOfMonth(mYear: Int, mMonth: Int): Int {
        val cal = Calendar.getInstance()
        cal.set(mYear, mMonth, 1)
        return cal.get(Calendar.DAY_OF_WEEK) - 1
    }
}
