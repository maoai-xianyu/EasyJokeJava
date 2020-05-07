package com.mao.easyjokejava.util;

import android.graphics.Bitmap;

/**
 * @author zhangkun
 * @time 2020-04-16 22:18
 * @Description: 图片处理
 */
public class ImageUtil {
    static {
        System.loadLibrary("jpeg");
        System.loadLibrary("compressimg");
    }

    /**
     * 图片压缩
     * @param bitmap 图片bitmap
     * @param quality 压缩的质量
     * @param fileName 压缩后的路径
     */
    public static void compressImage(Bitmap bitmap,int quality,
                                     String fileName){
        compressBitmap(bitmap,quality,fileName);
    }


    /**
     * NDK方法加载图片
     * @param bitmap 图片bitmap
     * @param quality 压缩的质量
     * @param fileName 压缩后的路径
     * @return
     */
    public native static int compressBitmap(Bitmap bitmap,int quality,
                                           String fileName);
}
