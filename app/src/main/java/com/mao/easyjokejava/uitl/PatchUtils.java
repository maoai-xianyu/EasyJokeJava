package com.mao.easyjokejava.uitl;

/**
 * @author zhangkun
 * @time 2020-05-11 16:38
 * @Description
 */
public class PatchUtils {

    /**
     * 合并差分包
     * @param oldApkPath  原来的apk 1.0 本地安装的apk
     * @param nawApkPath  合并后新的apk路径，需要生成的2.0
     * @param patchPath   差分贝包路径，从服务器下载
     */
    public static native void combine(String oldApkPath, String nawApkPath, String patchPath);

}
