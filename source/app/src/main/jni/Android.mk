LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := jnidemotest
LOCAL_SRC_FILES := jniTest.c
include $(BUILF_SHARED_LIBRARY)