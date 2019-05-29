package com.zf.fanluxi.api;

public class TestJava {









    String a = "java test";


    String[] newDisplayedValues  =new  String[0] ;

    public static String unicodetoString(String unicode) {
        if (unicode == null || "".equals(unicode)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }




    public static String htmlReplace(String str){



        str = str.replace("&ldquo;", "“");
        str = str.replace("&rdquo;", "”");
        str = str.replace("&nbsp;", " ");
        str = str.replace("&", "&amp;");
        str = str.replace("&#39;", "'");
        str = str.replace("&rsquo;", "’");
        str = str.replace("&mdash;", "—");
        str = str.replace("&ndash;", "–");
        return str;
    }


}
