package com.hc.essay.library.util;

public class NDKTools {

    static {
        System.loadLibrary("ndkdemotest-jni");
    }

    public static native String getStringFromNDK();

}