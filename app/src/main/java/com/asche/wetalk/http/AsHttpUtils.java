package com.asche.wetalk.http;

public class AsHttpUtils {
    private static final String API_LOGIN = "http://47.107.124.93:8080/hope/user/login.do"; // 登录
    private static final String API_LOGOUT = "http://47.107.124.93:8080/hope/user/logout.do"; // 注销
    private static final String API_REGISTER = "http://47.107.124.93:8080/hope/user/register.do"; // 注册

    private static final String API_UPDATE_USER = "http://47.107.124.93:8080/hope/user/update_information.do"; // 更新个人信息
    private static final String API_UPLOAD = "http://47.107.124.93:8080/hope/user/upload.do"; // 文件上传

    // private static final String API_ = "";

    private static String cookie;

    public static boolean login(String userName, String password){
        return false;
    }

    public static void logout(){

    }

}
