package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class UserBean {
    private String id;
    private String userName;
    private String password;
    private String nickName;
    private String gender;
    private String imgAvatar;
    private String imgBg; // 背景墙
    private String signature;
    private String tel;
    private String address;
    private String profession; // 职业
    private String description; // 个人描述
    private int followNum; // 关注数量
    private int followerNum; // 粉丝数量

    private int moneyVirtual;
    private int money;

    public UserBean(String id, String userName, String password, String nickName, String gender, String imgAvatar,
                    String imgBg, String signature, String tel, String address, String profession, String description,
                    int followNum, int followerNum, int moneyVirtual, int money) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.gender = gender;
        this.imgAvatar = imgAvatar;
        this.imgBg = imgBg;
        this.signature = signature;
        this.tel = tel;
        this.address = address;
        this.profession = profession;
        this.description = description;
        this.followNum = followNum;
        this.followerNum = followerNum;
        this.moneyVirtual = moneyVirtual;
        this.money = money;
    }

    public UserBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getImgBg() {
        return imgBg;
    }

    public void setImgBg(String imgBg) {
        this.imgBg = imgBg;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMoneyVirtual() {
        return moneyVirtual;
    }

    public void setMoneyVirtual(int moneyVirtual) {
        this.moneyVirtual = moneyVirtual;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            return StringUtils.objToString(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
