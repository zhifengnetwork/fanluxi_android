package com.zf.fanluxi.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.zf.fanluxi.MyApplication.Companion.context
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scan.*
import com.zf.fanluxi.utils.ImageUtil


/*
定制二维码扫描界面
*/
class ScanActivity:BaseActivity(){

    private var captureFragment: CaptureFragment = CaptureFragment()

    var isOpen = false//闪光灯开关

    /**
     * 选择系统图片Request Code
     */
    val REQUEST_IMAGE = 112

    override fun initToolBar() {

    }

    override fun layoutId(): Int {
         return com.zf.fanluxi.R.layout.activity_scan
    }

    override fun initData() {

    }

    override fun initView() {
        //为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment,R.layout.my_camera)
        captureFragment.setAnalyzeCallback(analyzeCallback)
        supportFragmentManager.beginTransaction().replace(R.id.fl_my_container, captureFragment).commit()

    }

    override fun initEvent() {
        //闪光灯
        light.setOnClickListener {
                if(!isOpen){
                    CodeUtils.isLightEnable(true)
                    isOpen=true
                }else{
                    CodeUtils.isLightEnable(false)
                    isOpen=false
                }
        }
        //相册
        photo.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE)

        }

    }

    override fun start() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE){
            if(data!=null){
                val uri=data.data
                try{
                    CodeUtils.analyzeBitmap(
                        ImageUtil.getImageAbsolutePath(context, uri),
                        object : CodeUtils.AnalyzeCallback {
                            override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
                                Toast.makeText(context, "解析结果:$result", Toast.LENGTH_LONG).show()
                            }

                            override fun onAnalyzeFailed() {
                                Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show()
                            }
                        })
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }


    /**
     * 二维码解析回调函数
     */
    internal var analyzeCallback: CodeUtils.AnalyzeCallback = object : CodeUtils.AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
            bundle.putString(CodeUtils.RESULT_STRING, result)
            resultIntent.putExtras(bundle)
            this@ScanActivity.setResult(RESULT_OK, resultIntent)
            this@ScanActivity.finish()
        }

        override fun onAnalyzeFailed() {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED)
            bundle.putString(CodeUtils.RESULT_STRING, "")
            resultIntent.putExtras(bundle)
            this@ScanActivity.setResult(RESULT_OK, resultIntent)
            this@ScanActivity.finish()
        }
    }
}