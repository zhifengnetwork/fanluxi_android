package com.zf.fanluxi.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alivc.live.pusher.AlivcLivePushConfig
import com.alivc.live.pusher.AlivcLivePusher
import com.alivc.live.pusher.AlivcPreviewOrientationEnum
import com.alivc.live.pusher.AlivcResolutionEnum
import com.google.android.material.snackbar.Snackbar
import com.zf.fanluxi.BuildConfig
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.showToast
import kotlinx.android.synthetic.main.activity_live.*
import java.io.File

class LiveActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, LiveActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_live

    override fun initView() {

        initPermission()

        initConfig()
    }

    private var mAlivcLivePushConfig: AlivcLivePushConfig? = null

    private fun initConfig() {

        //创建AlivcLivePushConfig对象，并设置相关参数。
        mAlivcLivePushConfig = AlivcLivePushConfig()
        mAlivcLivePushConfig?.let {
            if (it.previewOrientation == AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT.orientation ||
                it.previewOrientation == AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT.orientation
            ) {
                it.networkPoorPushImage =
                    Environment.getExternalStorageDirectory().path + File.separator + "alivc_resource/poor_network_land.png"
                it.pausePushImage =
                    Environment.getExternalStorageDirectory().path + File.separator + "alivc_resource/background_push_land.png"
            } else {
                it.networkPoorPushImage =
                    Environment.getExternalStorageDirectory().path + File.separator + "alivc_resource/poor_network.png"
                it.pausePushImage =
                    Environment.getExternalStorageDirectory().path + File.separator + "alivc_resource/background_push.png"
            }
            AlivcLivePushConfig.setMediaProjectionPermissionResultData(null)

            //设置分辨率
            it.setResolution(AlivcResolutionEnum.RESOLUTION_540P)
        }
    }

    private var mAlivcLivePusher: AlivcLivePusher? = null

    override fun initEvent() {

        playVd.setOnClickListener {
            PlayerActivity.actionStart(this)
        }

        //切换相机位置
        switchCamera.setOnClickListener {
            mAlivcLivePusher?.switchCamera()
        }

        // 闪光灯
        flashLight.setOnClickListener {
            flashLight.isSelected = !flashLight.isSelected
            mAlivcLivePusher?.setFlash(flashLight.isSelected)
        }

        // 预备直播
        startLive.setOnClickListener {
            showToast("正在直播中。。。。")
            //推流url
            mAlivcLivePusher = AlivcLivePusher()
            try {
                mAlivcLivePusher?.init(applicationContext, mAlivcLivePushConfig)
            } catch (e: Exception) {
                showToast("推流错误")
            }
            mAlivcLivePusher?.startPreviewAysnc(preview_view)
        }

        start.setOnClickListener {
            //是否暂停状态
            mAlivcLivePusher?.startPushAysnc(
                "rtmp://push.zhifengwangluo.com/dc/user123123123?auth_key=1558453484-0-0-90aba8cfc169751ed66538882f5607e6"
            )
        }
    }

    override fun onPause() {
        if (mAlivcLivePusher != null) {
            try {
                mAlivcLivePusher?.pause()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onPause()
    }

    override fun onResume() {
        if (mAlivcLivePusher != null) {
            try {
                mAlivcLivePusher?.resumeAsync()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onResume()
    }

    override fun onDestroy() {
        if (mAlivcLivePusher != null) {
            try {
                mAlivcLivePusher?.destroy()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        mAlivcLivePusher = null
        super.onDestroy()
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    private val REQUESTCODE = 34

    private fun initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //没有权限则申请权限
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, REQUESTCODE)
                }
            }
        } else {
            //权限申请成功
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUESTCODE -> {
                for (grantResult in grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        //权限申请成功
                    } else {
                        for (permission in permissions) {
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                                //权限被永远拒绝
                                showSnackBar("申请权限", "设置", View.OnClickListener {
                                    val intent = Intent()
                                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                    intent.data = uri
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                })
                            } else {
                                showSnackBar("申请权限", "确定", View.OnClickListener {
                                    ActivityCompat.requestPermissions(this, permissions, REQUESTCODE)
                                })
                            }
                        }
                    }
                }

            }
        }

    }

    private fun showSnackBar(contentText: String, buttonText: String, listener: View.OnClickListener) {
        Snackbar.make(
            findViewById(android.R.id.content),
            contentText,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(buttonText, listener).show()
    }

    override fun initData() {
    }

    override fun start() {
    }
}