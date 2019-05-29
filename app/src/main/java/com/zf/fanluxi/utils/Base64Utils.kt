package com.zf.fanluxi.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


/**
 * 将图片转换成Base64编码的字符串
 * @param path
 * @return base64编码的字符串
 */

class Base64Utils {
    companion object {

        /**
         * 这个函数不压缩图片，直接输出原图base64
         */
        fun imageToBase64(path: String): String? {
            if (TextUtils.isEmpty(path)) {
                return null
            }
            var `is`: InputStream? = null
            var data: ByteArray? = null
            var result: String? = null
            try {
                `is` = FileInputStream(path)
                //创建一个字符流大小的数组。
                data = ByteArray(`is`.available())
                //写入数组
                `is`.read(data)
                //用默认的编码格式进行编码
                result = Base64.encodeToString(data, Base64.DEFAULT)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (null != `is`) {
                    try {
                        `is`.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

            }
            return result
        }

        /**
         * 这个函数压缩图片，并输出压缩后的base64
         */
        fun bitmapToString(filePath: String): String {

            val bm = getSmallBitmap(filePath)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        //将字符串转换成Bitmap类型
        fun stringtoBitmap(string: String): Bitmap ?{
            var bitmap: Bitmap? = null
            try {
                val bitmapArray: ByteArray
                bitmapArray = Base64.decode(string, Base64.DEFAULT)
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

        // 根据路径获得图片并压缩，返回bitmap用于显示
        private fun getSmallBitmap(filePath: String): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, options)
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 480, 800)
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false

            return BitmapFactory.decodeFile(filePath, options)
        }

        //计算图片的缩放值
        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            return inSampleSize
        }

    }
}
