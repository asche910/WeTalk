package com.asche.wetalk.util;

import android.net.Uri;

import static com.asche.wetalk.MyApplication.getContext;

public class ResourcesUtils {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
