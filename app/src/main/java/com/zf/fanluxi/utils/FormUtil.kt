package com.zf.fanluxi.utils

import android.widget.EditText
import java.util.regex.Pattern

class FormUtil {
    companion object {

        /**
         * 手机号验证
         * 是 true
         */
        fun isMobile(str: String): Boolean {
            val p: Pattern = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$")
            val b: Boolean
            val m = p.matcher(str)
            b = m.matches()
            return b
        }

        /**
         * 验证邮箱
         *  是 true
         */
        fun isEmail(email: String): Boolean {
            return try {
                val check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
                val regex = Pattern.compile(check)
                val matcher = regex.matcher(email)
                matcher.matches()
            } catch (e: Exception) {
                false
            }
        }

        /**
         * 密码长度限制6位
         */
        fun pwdIfVerify(editText: EditText): Boolean {
            //小于8返回true ,否则false
            return editText.length() < 6
        }
    }


}