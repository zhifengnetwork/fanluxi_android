package com.zf.fanluxi.utils

import android.content.Context
import android.os.Environment
import java.io.File

class DurbanUtils {

    companion object {

        fun getAppRootPath(context: Context): File {
            return if (sdCardIsAvailable()) {
                Environment.getExternalStorageDirectory()
            } else {
                context.filesDir
            }
        }

        fun sdCardIsAvailable(): Boolean {
            return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                Environment.getExternalStorageDirectory().canWrite()
            } else
                false
        }
    }
}