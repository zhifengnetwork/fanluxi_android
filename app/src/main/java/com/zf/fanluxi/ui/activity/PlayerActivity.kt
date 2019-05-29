package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import com.alivc.player.AliVcMediaPlayer
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity() {

    override fun initToolBar() {
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, PlayerActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_player

    override fun initData() {
    }

    private var mPlayer: AliVcMediaPlayer? = null

    override fun initView() {
        //初始化播放器
        mPlayer = AliVcMediaPlayer(this, surfaceView)
        //准备播放
        mPlayer?.prepareToPlay("http://112.253.22.157/17/p/a/d/o/padoeqmsgejhkqpvvuguuzhvcyhcqx/sh.yinyuetai.com/70C80159F0CE44EC16C52799F76C8556.mp4")

    }

    override fun initEvent() {
        goPlay.setOnClickListener {
            //播放

            mPlayer?.play()
        }
    }

    override fun start() {
    }

}