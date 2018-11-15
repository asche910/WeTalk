package com.asche.wetalk.util;

import com.asche.wetalk.bean.UserBean;

import java.util.Random;

/**
 * generate simulated data
 */
public class DataUtils {

    private static Random random = new Random();

    public static UserBean getUser(){
        UserBean userBean = new UserBean();
        int id = random.nextInt(999_999_999) +  100_000;
        userBean.setId(id);
        userBean.setUserName("User_" + id);
        return userBean;
    }
}
