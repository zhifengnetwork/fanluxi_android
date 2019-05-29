package com.zf.fanluxi.utils


import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

internal class CustomBaseGlideUrlLoader(cl: ModelLoader<GlideUrl, InputStream>, mc: ModelCache<String, GlideUrl>) :
    BaseGlideUrlLoader<String>(cl, mc) {


    val patern = Pattern.compile("__w-((?:-?\\d+)+)__")

    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m = patern.matcher(model)
        var bestBucket = 0
        if (m.find()) {
            val found = m.group(1).split("-")
            for (item in found) {
                bestBucket = item.toInt()
                if (bestBucket >= width) break
            }
        }
        return model
    }

    override fun handles(model: String): Boolean = true

    companion object {
        val urlCache = ModelCache<String, GlideUrl>(150)
        @JvmField
        val factory = object : ModelLoaderFactory<String, InputStream> {
            override fun teardown() {

            }

            override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
                return CustomBaseGlideUrlLoader(
                    multiFactory.build(GlideUrl::class.java, InputStream::class.java),
                    urlCache
                )
            }

        }
    }

}