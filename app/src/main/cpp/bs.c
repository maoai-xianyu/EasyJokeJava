//
// Created by Lebron Sn on 2017/2/6.
//
#include "com_mao_easyjokejava_uitl_PatchUtils.h"

JNIEXPORT jint JNICALL
Java_com_mao_easyjokejava_uitl_PatchUtils_diff
        (JNIEnv *env, jobject jclz, jstring old_apk_path, jstring new_apk_path,
         jstring patch_path) {
    /*const char *argv[4];
    argv[0] = "bsdiff";
    argv[1] = (*env)->GetStringUTFChars(env, oldpath_, 0);
    argv[2] = (*env)->GetStringUTFChars(env, newpath_, 0);
    argv[3] = (*env)->GetStringUTFChars(env, patch_, 0);
    mydiff(4, argv);
    (*env)->ReleaseStringUTFChars(env, oldpath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newpath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_, argv[3]);*/
//    free(argv[0]);
//    free(argv);

    int argc = 4;
    char *argv[4];

    // 封装参数
    char *old_apk_cstr = (char *) (*env)->GetStringUTFChars(env, old_apk_path, NULL);
    char *new_apk_cstr = (char *) (*env)->GetStringUTFChars(env, new_apk_path, NULL);
    char *patch_cstr = (char *) (*env)->GetStringUTFChars(env, patch_path, NULL);

    argv[0] = "diff";
    argv[1] = old_apk_cstr;
    argv[2] = new_apk_cstr;
    argv[3] = patch_cstr;

    // 调用CPP方法  int argc,char *argv[]
    mydiff(argc, argv);
    // 释放资源
    (*env)->ReleaseStringUTFChars(env, old_apk_path, old_apk_cstr);
    (*env)->ReleaseStringUTFChars(env, new_apk_path, new_apk_cstr);
    (*env)->ReleaseStringUTFChars(env, patch_path, patch_cstr);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_mao_easyjokejava_uitl_PatchUtils_patch
        (JNIEnv *env, jobject jclz, jstring old_apk_path, jstring new_apk_path,
         jstring patch_path) {
    // 1.封装参数
    int argc = 4;
    char *argv[4];
    // 1.1 转换  jstring -> char*
    char *old_apk_cstr = (char *) (*env)->GetStringUTFChars(env, old_apk_path, NULL);
    char *new_apk_cstr = (char *) (*env)->GetStringUTFChars(env, new_apk_path, NULL);
    char *patch_cstr = (char *) (*env)->GetStringUTFChars(env, patch_path, NULL);

    // 第0的位置随便给
    argv[0] = "combine";
    argv[1] = old_apk_cstr;
    argv[2] = new_apk_cstr;
    argv[3] = patch_cstr;
    // 2.调用上面的方法  int argc,char * argv[]
    mypatch(argc, argv);
    // 3.释放资源
    (*env)->ReleaseStringUTFChars(env, old_apk_cstr, argv[1]);
    (*env)->ReleaseStringUTFChars(env, new_apk_cstr, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_cstr, argv[3]);
    return 0;
}
