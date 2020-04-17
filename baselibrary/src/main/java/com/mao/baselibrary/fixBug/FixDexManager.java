package com.mao.baselibrary.fixBug;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * @author zhangkun
 * @time 2020-04-17 17:25
 * @Description
 */
public class FixDexManager {

    private static final String TAG = "FixDexManager";

    private Context mContext;
    private File mDexDir;


    public FixDexManager(Context context) {
        this.mContext = context;
        // 获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }


    /**
     * 加载全部的修复包
     */
    public void loadFixDex() throws Exception {
        File[] dexFiles = mDexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();
        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexFile);
            }
        }

        fixDexFiles(fixDexFiles);
    }

    /**
     * 修复dex
     *
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles) throws Exception {
        // 1. 先获取已经运行的 dexElement

        ClassLoader mApplicationClassLoader = mContext.getClassLoader();

        Object applicationDexElements = getDexElementsByClassLoader(mApplicationClassLoader);

        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
        // 修复
        for (File fixDexFile : fixDexFiles) {
            // String dexPath dex路径
            // File optimizedDirectory 解压路径
            // String librarySearchPath .so 文件
            // ClassLoader parent  父 ClassLoader
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getCanonicalPath(),
                    optimizedDirectory,
                    null,
                    mApplicationClassLoader
            );

            Object fixDexElements = getDexElementsByClassLoader(fixDexClassLoader);

            // 3. 把补丁的dexElement 插到已经运行的 dexElement 的最前面  合并
            // mApplicationClassLoader applicationDexElements 数组  合并 fixDexClassLoader 的fixDexElements 数组
            // 合并完成
            applicationDexElements = combineArray(fixDexElements, applicationDexElements);
        }

        // 把合并的数组 注入到原来的类中 mApplicationClassLoader
        injectDexElements(mApplicationClassLoader, applicationDexElements);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {

        // 2. 获取下载好的补丁的 dexElement

        // 2.1  移动到系统能够访问的 dex 目录下 ClassLoader
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath);
        }

        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            Log.d(TAG, "patch [" + fixDexPath + "] has be loaded.");
            return;
        }
        copyFile(srcFile, destFile);

        // 2.2 ClassLoader 读取 fixDexPath 路劲 为啥加入集合？因为一启动可能就要修复 BaseApplication

        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);
        fixDexFiles(fixDexFiles);
    }

    /**
     * 注入  把 dexElements 注入到 classLoader
     *
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {

        // 1. 先获取 BaseDexClassLoader 中的 DexPathList pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        // 反射
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);
        // 2. 获取 DexPathList pathList 中的  dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        dexElementsField.set(pathList, dexElements);
    }


    /**
     * 合并两个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; k++) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayLhs, k - i));
            }
        }
        return result;
    }


    /**
     * 从 classLoader 中获取 dexElement
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {

        // 1. 先获取 BaseDexClassLoader 中的 DexPathList pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        // 反射
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        // 2. 获取 DexPathList pathList 中的  dexElements

        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        return dexElementsField.get(pathList);


    }


    /**
     * copy file
     *
     * @param src  source file
     * @param dest target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

}
