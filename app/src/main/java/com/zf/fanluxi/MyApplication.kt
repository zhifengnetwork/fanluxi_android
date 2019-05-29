package com.zf.fanluxi

import android.app.Application
import android.content.Context
import com.alivc.player.AliVcMediaPlayer
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import com.zf.fanluxi.utils.MediaLoader
import kotlin.properties.Delegates

class MyApplication : Application() {


    companion object {

        var context: Context by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //初始化album图像选择
        Album.initialize(AlbumConfig.newBuilder((this)).setAlbumLoader(MediaLoader()).build())

        //初始化阿里播放器，无关直播
        AliVcMediaPlayer.init(this)
    }

}
