package com.asche.wetalk.storage;

import android.os.Environment;
import android.util.Log;

import com.asche.wetalk.bean.ChatItemBean;
import com.asche.wetalk.util.FileUtils;
import com.asche.wetalk.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.asche.wetalk.MyApplication.getContext;

/**
 * /sdcard/WeTalk/chatrecord
 */
public class ChatStorage extends BaseStorage{

    private static final String TAG = "File";

    @Override
    public void store() {
    }

    @Override
    public void read() {
    }

    public static void storeChatRecord(List<ChatItemBean> list) throws IOException {
        File file = new File(CHAT_RECORD_PATH + "/chat.txt");

        File fileDir = new File(CHAT_RECORD_PATH);
        if (!fileDir.exists()){
            FileUtils.makedir(CHAT_RECORD_PATH);
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        for (ChatItemBean bean: list){
            String chatStr = bean.toString() + "\n";
            outputStream.write(chatStr.getBytes());
        }
        outputStream.close();
    }

    public static List<ChatItemBean> readChatRecord() throws Exception {
        File file = new File(CHAT_RECORD_PATH + "/chat.txt");

        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<ChatItemBean> list = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null){
            ChatItemBean bean = new ChatItemBean();
            StringUtils.stringToObj(line, bean);
            list.add(bean);
        }
        reader.close();
        inputStream.close();
        return list;
    }
}
