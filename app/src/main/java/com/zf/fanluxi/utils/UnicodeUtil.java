package com.zf.fanluxi.utils;

public class UnicodeUtil {

    //html5 字符转义
    public static String translation(String content) {
        String replace = content.replace("&lt;", "<");
        String replace1 = replace.replace("&gt;", ">");
        String replace2 = replace1.replace("&amp;", "&");
        String replace3 = replace2.replace("&quot;", "\"");
        return replace3.replace("&copy;", "©");
    }


}
