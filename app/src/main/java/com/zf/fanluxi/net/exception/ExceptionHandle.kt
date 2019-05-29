package com.zf.fanluxi.net.exception

import com.google.gson.JsonParseException
import com.zf.fanluxi.utils.LogUtils
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException


class ExceptionHandle {

    companion object {

        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "请求失败，请稍后重试"

        fun handleException(e: Throwable): String {
            e.printStackTrace()
            if (e is SocketTimeoutException) {//网络超时
                LogUtils.e("TAG", "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is ConnectException) { //均视为网络错误
                LogUtils.e("TAG", "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
            ) {   //均视为解析错误
                LogUtils.e("TAG", "数据解析异常: " + e.message)
                errorMsg = "数据解析异常"
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is ApiException) {//服务器返回的错误信息
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is UnknownHostException) {
                LogUtils.e("TAG", "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is IllegalArgumentException) {
                //参数错误
                errorMsg = "请求失败"
                errorCode = ErrorStatus.SERVER_ERROR
            } else {//未知错误
                try {
                    LogUtils.e("TAG", "错误: " + e.message)
                } catch (e1: Exception) {
                    LogUtils.e("TAG", "未知错误Debug调试 ")
                }
                errorMsg = "请求失败，请稍后重试"
                errorCode = ErrorStatus.UNKNOWN_ERROR
            }
            return errorMsg
        }

    }


}
