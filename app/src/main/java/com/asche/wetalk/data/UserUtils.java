package com.asche.wetalk.data;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.IDUtils;

import static com.asche.wetalk.util.DataUtils.imgAvatar_1;
import static com.asche.wetalk.util.DataUtils.imgAvatar_2;
import static com.asche.wetalk.util.DataUtils.random;

public class UserUtils {

    /**
     * 四个角色，编号0—3
     * @param index  角色编号，为空随机
     * @return
     */
    public static UserBean getUser(int... index) {
        UserBean userBean = new UserBean();
        int n;
        if (index.length == 0)
            n = random.nextInt(4);
        else
            n = index[0];
        switch (n) {
            case 3:
                userBean.setImgAvatar(imgAvatar_2);
                userBean.setNickName("米洛斯");
                userBean.setGender("男");
                userBean.setSignature("要随波逐浪，不可随波逐流。");

                break;
            case 2:
                userBean.setImgAvatar(R.drawable.img_avatar_default + "");
                userBean.setNickName("Judy妞");
                userBean.setGender("女");
                userBean.setSignature("不加香菜");

                break;
            case 1:
                userBean.setImgAvatar(imgAvatar_1);
                userBean.setNickName("Donna裹love橙");
                userBean.setGender("女");
                userBean.setSignature("竭尽全力，问心无愧，落幕无悔");

                break;
            case 0:
                userBean.setImgAvatar(R.drawable.img_avatar + "");
                userBean.setNickName("Asche");
                userBean.setGender("男");
                userBean.setSignature("What doesn't kill you makes you stronger!");

                break;
            default:
        }

        userBean.setFollowerNum(n * 10);
        userBean.setFollowNum(n * 10 + 5);
        userBean.setUserName(IDUtils.getUserName());

        int id = random.nextInt(999_999_999) + 100_000;
        userBean.setId(id + "");
        return userBean;
    }

}
