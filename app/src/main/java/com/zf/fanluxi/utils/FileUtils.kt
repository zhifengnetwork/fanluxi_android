package com.zf.fanluxi.utils

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore


object FileUtils {



    fun pathToUri(context: Activity, picPath: String): Uri? {
        /**
         *   path 转uri
         *   从这个格式       /storage/emulated/0/Android/data/com.takephoto_android70/1533109354195.jpg
         *   转换为这个格式（输出结果）   content://com.android.providers.media.documents/document/image%3A624
         *   val path = Environment.getExternalStorageDirectory().absolutePath + File.separator + "Android" + File.separator + "data" + File.separator + "com.takephoto_android70" + File.separator + System.currentTimeMillis() + ".png"
         *
         */
        val mUri = Uri.parse("content://media/external/images/media")
        var mImageUri: Uri? = null

        /**  下面这个方法过时了 */
//        val cursor = context.managedQuery(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER)

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER
        )

        if (cursor != null) {

            LogUtils.e(">>>>????图片不为空")

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val data = cursor.getString(
                    cursor
                        .getColumnIndex(MediaStore.MediaColumns.DATA)
                )

                if (picPath == data) {
                    val ringtoneID = cursor.getInt(
                        cursor
                            .getColumnIndex(MediaStore.MediaColumns._ID)
                    )
                    mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID)
                    /** 关闭cursor，后面添加的 */
                    cursor.close()


                    LogUtils.e(">>>>imageUri" + mImageUri)

                    return mImageUri
                }
                cursor.moveToNext()
            }

        } else {
            LogUtils.e(">>>>>图片为空")
        }

        LogUtils.e(">>>>???????cccc" + mImageUri)
        return mImageUri
    }
}