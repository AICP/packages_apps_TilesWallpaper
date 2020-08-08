LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml
LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/java)
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res

LOCAL_STATIC_ANDROID_LIBRARIES := \
    androidx.core_core \
    androidx.preference_preference

LOCAL_PACKAGE_NAME := TilesWallpaper

LOCAL_SDK_VERSION := current

LOCAL_REQUIRED_MODULES := TilesWallpaperOverlay

include $(BUILD_PACKAGE)

# Overlay to set component name of the default wallpaper
include $(CLEAR_VARS)

LOCAL_RRO_THEME := WallpaperOverlay

LOCAL_PRODUCT_MODULE := true

# LOCAL_SRC_FILES := $(call all-subdir-java-files, overlay)
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/overlay/res
LOCAL_MANIFEST_FILE := overlay/AndroidManifest.xml

LOCAL_PACKAGE_NAME := TilesWallpaperOverlay
LOCAL_SDK_VERSION := current

include $(BUILD_RRO_PACKAGE)
