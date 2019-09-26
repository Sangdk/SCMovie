package com.t3h.scmovie.utils;

public class StringUtils {

    public static String getImageUrl(String path){
        return new StringBuilder().append(Const.BASE_IMAGE_PATH)
                .append(Const.IMAGE_SIZE_W500)
                .append(path).toString();
    }
}
