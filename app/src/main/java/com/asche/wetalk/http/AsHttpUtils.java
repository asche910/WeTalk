package com.asche.wetalk.http;

import android.util.Log;

import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.UserUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.asche.wetalk.activity.BaseActivity.getCurUser;
import static com.asche.wetalk.activity.BaseActivity.setCurUser;

public class AsHttpUtils {
    private static final String BASE_URL = "http://home.asche.top:8080";
    private static final String API_LOGIN = BASE_URL + "/hope/user/login.do"; // 登录
    private static final String API_LOGOUT = BASE_URL + "/hope/user/logout.do"; // 注销
    private static final String API_REGISTER = BASE_URL + "/hope/user/register.do"; // 注册

    private static final String API_UPDATE_USER = BASE_URL + "/hope/user/update_information.do"; // 更新个人信息
    private static final String API_UPLOAD = BASE_URL + "/hope/user/upload.do"; // 文件上传

    private static String cookie;

    private static OkHttpClient client = new OkHttpClient();

    private static final String TAG = "AsHttpUtils -------> ";

    public static boolean login(String userName, String password){
        if ("root".equals(userName) && "root".equals(password)){
            setCurUser(UserUtils.getUser(1));
            return true;
        }
        if (!ServerStatus.isServerNormal()){
            setCurUser(UserUtils.getUser(1));
            return false;
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userName", userName)
                .addFormDataPart("password", password)
                .build();

        Request request = new Request.Builder()
                .url(API_LOGIN)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            cookie = response.header("Set-Cookie");
            // Headers headers = response.headers();
            // Log.e(TAG, "login: " + headers.toString() );

            String content = response.body().string();
            Log.e(TAG, "login: " + content );

            JSONObject jsonObject = new JSONObject(content);
            int code = jsonObject.getInt("status");
            if ( code == 0){
                // 登录成功
                parseLoginData(jsonObject.getJSONObject("data"));
                return true;
            }
            Log.e(TAG, "login: --------------> 登录失败！" );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


// QQ   E/-------: {"unionid":"","gender":"2","pay_token":"906608D30AF1D67940EE2422CB6FC59C","icon":"http:\/\/thirdqq.qlogo.cn\/g?b=oidb&k=FicFicXv3ZtriaY68s3vThagg&s=100","secret":"","userID":"93761BBACE42C2C407A2E92705C7F8CC","expiresTime":1552828578173,"token":"2BB968BDA9EC460D50FDA0809A09A2D1","expiresIn":7776000,"pfkey":"f81f26f110c8dc9de7482128d18590b0","pf":"desktop_m_qq-10000144-android-2002-","secretType":"0","nickname":"Asche","iconQzone":"http:\/\/qzapp.qlogo.cn\/qzapp\/101550042\/93761BBACE42C2C407A2E92705C7F8CC\/100"}

// In   E/-------: {"expiresIn":5183999,"resume":"null","gender":"2","secretType":"-1","nickname":"Asche Xe","icon":"https:\/\/media.licdn.com\/dms\/image\/C5103AQElLgsj-8lEfw\/profile-displayphoto-shrink_100_100\/0?e=1558569600&v=beta&t=S_nxTsQkzaCwpUJLiRY1vR6CYIFG-9pBKHPSu3AOkNw","secret":"","snsUserUrl":"http:\/\/www.linkedin.com\/in\/asche-xe-88218b175","userID":"-Glt1xzPty","expiresTime":1552828721640,"token":"AQVhUd975ipNpRVdJrMqz4v6RuvonhPCxLdYu_azdqy4KKk18hbaPLqeYv1R8IC48Vbkh0Dn9aZU_yLtaldRdJs9_BaMwT2v9HW9nS8Dv7g-7nVrDPMCi5LS5mJyXO5Y5bS0HQMZP6LNMcmt7-6metzcTW6vUc03j4vPcwOLIX1kz9l5WhHmkpNPH2PpKgFdzH-Uw6-UqAkRBzhjovkYwPfbrEnA3yHW6d1TMtn4vTbBlSx8lne9EtvB2-3oMFO_4XXmKFpzajRLeJMMNmK3-1FA3Aa-ZKaSh03yLtjc24AuYF_6mFjYa9A5SX1aZI3kqazCBYzkSlfx3lrFAJPrMWtqBIX0Tw"}


    /**
     * 注销登录
     */
    public static void logout(){
        cookie = null;
        setCurUser(null);
    }

    public static boolean register(String userName, String password){
        if (!ServerStatus.isServerNormal()){
            setCurUser(UserUtils.getUser(1));
            return true;
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userName", userName)
                .addFormDataPart("password", password)
                .addFormDataPart("question", "Your userName?")
                .addFormDataPart("answer",userName)
                .build();
        Request request = new Request.Builder()
                .url(API_REGISTER)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String content = response.body().string();
            Log.e(TAG, "register: " + content );

            JSONObject jsonObject = new JSONObject(content);
            int code = jsonObject.getInt("status");

            if (code == 0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 更新个人信息
     */
    public static void updateUser(){
        UserBean userBean = BaseActivity.getCurUser();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userName", userBean.getUserName() + "") //  + "" 可解决空指针问题
                .addFormDataPart("nickname", userBean.getNickName() + "")
                .addFormDataPart("gender", userBean.getGender() + "")
                .addFormDataPart("imgavatar", userBean.getImgAvatar() + "")
                .addFormDataPart("imgbg", userBean.getImgBg() + "")
                .addFormDataPart("signature", userBean.getSignature() + "")
                .addFormDataPart("tel", userBean.getTel() + "")
                .addFormDataPart("address", userBean.getAddress() + "")
                .addFormDataPart("profession", userBean.getProfession() + "")
                .addFormDataPart("description", userBean.getDescription() + "")
                .addFormDataPart("follownum", userBean.getFollowNum() + "")
                .addFormDataPart("followernum", userBean.getFollowerNum() + "")
                .addFormDataPart("moneyvirtual", userBean.getMoneyVirtual() + "")
                .addFormDataPart("money", userBean.getMoney() + "")
                .build();

        Request request = new Request.Builder()
                .url(API_UPDATE_USER)
                .header("Cookie", cookie + "")
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.e(TAG, "updateUser: " + response.body().string() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析登录成功后返回的用户信息
     * @param obj
     */
    private static void parseLoginData(JSONObject obj){
        UserBean userBean = new UserBean();
        try {
            String userName = obj.getString("userName");
            if (userName.equals("root")){
                setCurUser(UserUtils.getUser(1));
                return;
            }

            String nickName = obj.getString("nickname");
            String gender = obj.getString("gender");
            String imgAvatar = obj.getString("imgavatar");
            String imgBg = obj.getString("imgbg");
            String signature = obj.getString("signature");
            String tel = obj.getString("tel");
            String address = obj.getString("address");
            String profession = obj.getString("profession");
            String description = obj.getString("description");
            int follownum = obj.getInt("follownum");
            int followernum = obj.getInt("followernum");
            int moneyVirtual = obj.getInt("moneyvirtual");
            int money = obj.getInt("money");

            Log.e(TAG, "parseLoginData: " + userName );

            userBean.setUserName(userName);
            userBean.setNickName(nickName);
            userBean.setGender(gender);
            userBean.setImgAvatar(imgAvatar);
            userBean.setImgBg(imgBg);
            userBean.setSignature(signature);
            userBean.setTel(tel);
            userBean.setAddress(address);
            userBean.setProfession(profession);
            userBean.setDescription(description);
            userBean.setFollowNum(follownum);
            userBean.setFollowerNum(followernum);
            userBean.setMoneyVirtual(moneyVirtual);
            userBean.setMoney(money);

            // Log.e(TAG, "parseLoginData: " + userBean.toString() );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setCurUser(userBean);
    }


    public static void parseThirdPartyData(String data){
        try {
            if (!ServerStatus.isServerNormal()){
                setCurUser(UserUtils.getUser(1));
                return ;
            }

            JSONObject jsonObject = new JSONObject(data);
            String gen = jsonObject.getString("gender");
            String userId = jsonObject.getString("userID");
            String gender = gen.equals("1") ? "女" : "男";
            String nickName = jsonObject.getString("nickname");
            String rawImgUrl = jsonObject.getString("icon");
            String imgUrl = rawImgUrl.replaceAll("\\\\", "");


            register(userId, "wetalk");
            login(userId, "wetalk");

            UserBean userBean = getCurUser();
            if (userBean == null){
                userBean = new UserBean();
            }
            userBean.setNickName(nickName);
            userBean.setGender(gender);
            userBean.setImgAvatar(imgUrl);
            setCurUser(userBean);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
