package com.zf.fanluxi;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.zf.fanluxi.utils.CustomBaseGlideUrlLoader;

import java.io.InputStream;

@com.bumptech.glide.annotation.GlideModule
public class GlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.append(String.class, InputStream.class, CustomBaseGlideUrlLoader.factory);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }


}
