#include "com_hc_essay_library_util_NDKTools.h"

JNIEXPORT jstring JNICALL Java_com_hc_essay_library_util_NDKTools_getStringFromNDK
  (JNIEnv *env, jobject obj){
     return (*env)->NewStringUTF(env,"Hellow World，这是隔壁老李头的NDK的第一行代码");
  }