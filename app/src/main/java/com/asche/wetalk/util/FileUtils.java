package com.asche.wetalk.util;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    /**
     * 可多级创建
     * @param path
     * @return
     */
    public static String makedir(String path) {
        String sdPath = Environment.getExternalStorageDirectory().toString();
        String[] dirs = path.replace(sdPath, "").split("/");
        StringBuffer filePath = new StringBuffer(sdPath);
        for (String dir : dirs) {
            if (!"".equals(dir) && !dir.equals(sdPath)) {
                filePath.append("/").append(dir);
                File destDir = new File(filePath.toString());
                if (!destDir.exists()) {
                    boolean b = destDir.mkdirs();
                    if (!b) {
                        return null;
                    }
                }
            }
        }
        return filePath.toString();
    }
}
