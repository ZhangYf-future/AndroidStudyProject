#include "com_project_mystudyproject_jni_1study_JniTools"
JNIEXPORT jstring JNICALL Java_com_project_mystudyproject_jni_1study_JniTools_getStringFromNDK
  (JNIEnv *env, jclass){
    return (*env) -> NewStringUTF(env,"Hello Jni");
  }