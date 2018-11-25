package com.asche.wetalk.storage;

import android.os.Environment;

public abstract class BaseStorage {

    public static final String SD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String APP_PATH = SD_PATH  + "/WeTalk";
    public static final String CHAT_RECORD_PATH = APP_PATH + "/chatrecord";

    public abstract void store();
    public abstract void read();
}
