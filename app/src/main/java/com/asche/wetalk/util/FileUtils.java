package com.asche.wetalk.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.load.model.ResourceLoader;
import com.bumptech.glide.load.model.UriLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.asche.wetalk.MyApplication.getContext;

public class FileUtils {

    /**
     * 可多级创建目录
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


    public static File bitmapToFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), ".temp");
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getFileFromURL(String imgUrl){
        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separatorChar + "temp.png");
        try {
            URL url = new URL(imgUrl);
            InputStream inputStream = url.openStream();
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] by = new byte[1024];
            int n;
            while ((n = inputStream.read(by)) != -1){
                outputStream.write(by, 0, n);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
