#include <jni.h>
#include <string>
#include <android/log.h>

extern "C"
jstring
Java_com_example_ruanxuan_demolive555_RtpClient_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from rx ++";
    jclass jclass1 = env->FindClass("com/example/ruanxuan/demolive555/RtpClient");
    jobject jobject1 = env->AllocObject(jclass1);
    jmethodID jmethodIDTestCallback = env->GetMethodID(jclass1,"testCallback",
                                                       "(II)V");
    env->CallVoidMethod(jobject1,jmethodIDTestCallback,1,2);
    __android_log_print(ANDROID_LOG_INFO, "rx","test Hello ");
    return env->NewStringUTF(hello.c_str());
}

