package com.asche.wetalk.http;

import android.os.AsyncTask;
import android.util.Log;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.ChatItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.asche.wetalk.activity.ChatActivity.chatItemBeanList;

public class ChatHttp extends AsyncTask<Void, Void, String> {

    final String url = "http://openapi.tuling123.com/openapi/api/v2";

    private String text;

    private HttpCallBack httpCallBack;

    public ChatHttp(String text) {
        this.text = text;
    }

    public void setMessage(String text){
        this.text = text;
    }

    public void send(){
        super.execute();
    }

    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), getJson(text));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String result = response.body().string();
            Log.e("Chat", "doInBackground: \n" + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            String result = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);
                String type = jb.getString("resultType");
                if (type.equals("text")) {
                    if (jb.getJSONObject("values").has("text")) {
                        result = jb.getJSONObject("values").getString("text");
                        chatItemBeanList.add(new ChatItemBean(1, result, R.drawable.img_avatar_default + ""));
                    }
                }else if (type.equals("image")){
                    result =  jb.getJSONObject("values").getString("image");
                    chatItemBeanList.add(new ChatItemBean(1,null,  result, R.drawable.img_avatar_default + ""));

                }else if (type.equals("url")){
                    result =  jb.getJSONObject("values").getString("url");
                    chatItemBeanList.add(new ChatItemBean(1, result, R.drawable.img_avatar_default + ""));
                }
            }

            httpCallBack.callBack("update");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static String getJson(String text) {
        return "{\n" +
                "\t\"reqType\":0,\n" +
                "    \"perception\": {\n" +
                "        \"inputText\": {\n" +
                "            \"text\": \"" + text + "\"\n" +
                "        }\n" +
                "    \n" +
                "    },\n" +
                "    \"userInfo\": {\n" +
                "        \"apiKey\": \"a43e23735a1642a98efc9ad891d5bc57\",\n" +
                "        \"userId\": \"249590\"\n" +
                "    }\n" +
                "}";
    }
}
