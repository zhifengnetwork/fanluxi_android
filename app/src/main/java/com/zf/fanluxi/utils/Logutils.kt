package com.zf.fanluxi.utils

import android.util.Log


object LogUtils {

    /**
     * 当前是否release版本
     * 若是，不输入任何log
     * 是否开启日志
     * 需要由主App配置
     */
    var isRelease = false


    /**
     * 类名
     */
    private var className: String? = null
    /**
     * 方法名
     */
    private var methodName: String? = null
    /**
     * 行数
     */
    private var lineNumber: Int = 0

    //分段显示
    //规定每段显示的长度
    private val LOG_MAXLENGTH = 2000


    /**
     * 直接输出
     */
    fun print(msg: String) {
        print("Log.print:$msg")
    }


    private fun createLog(log: String): String {
        return methodName +
                "(Log：" + className + ":" + lineNumber + ")" +
                log
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun i(message: String) {
        if (isRelease) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun e(message: String) {
        if (isRelease) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }

    fun e(tag: String, message: String) {
        if (isRelease) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.e("$tag--$className", createLog(message))
    }

    fun d(message: String) {
        if (isRelease) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun v(message: String) {
        if (isRelease) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun w(message: String) {
        if (isRelease) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }

    fun longE(TAG: String, msg: String) {
        val strLength = msg.length
        var start = 0
        var end = LOG_MAXLENGTH
        for (i in 0..99) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(TAG + i, msg.substring(start, end))
                start = end
                end = end + LOG_MAXLENGTH
            } else {
                Log.e(TAG, msg.substring(start, strLength))
                break
            }
        }
    }

}
