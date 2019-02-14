package com.asche.wetalk.data;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;

import static com.asche.wetalk.data.DataUtils.imgAvatar_1;
import static com.asche.wetalk.data.DataUtils.imgAvatar_2;
import static com.asche.wetalk.data.DataUtils.random;

public class UserUtils {

    /**
     * 四个角色，编号0—4
     * @param index  角色编号，为空随机
     * @return
     */
    public static UserBean getUser(int... index) {
        UserBean userBean = new UserBean();
        int n;
        if (index.length == 0)
            n = random.nextInt(5);
        else
            n = index[0];
        switch (n) {
            case 4:
                userBean.setImgAvatar(imgAvatar_2);
                userBean.setImgBg(imgAvatar_2);
                userBean.setNickName("米洛斯");
                userBean.setUserName("user_3");
                userBean.setGender("男");
                userBean.setSignature("要随波逐浪，不可随波逐流。");
                userBean.setProfession("机械");
                userBean.setAddress("英国-伦敦");

                break;
            case 3:
                userBean.setImgAvatar(R.drawable.img_avatar_default + "");
                userBean.setImgBg(R.drawable.img_avatar_default + "");
                userBean.setNickName("Judy妞");
                userBean.setUserName("user_2");
                userBean.setGender("女");
                userBean.setSignature("不加香菜");
                userBean.setProfession("会计");
                userBean.setAddress("中国-广州");
                break;
            case 2:
                userBean.setImgAvatar(imgAvatar_1);
                userBean.setImgBg(imgAvatar_1);
                userBean.setNickName("Donna裹love橙");
                userBean.setUserName("user_1");
                userBean.setGender("女");
                userBean.setSignature("竭尽全力，问心无愧，落幕无悔");
                userBean.setProfession("专职作家");
                userBean.setAddress("中国-香港");
                userBean.setExpert(true);

                break;
            case 1:
                userBean.setImgAvatar(R.drawable.img_avatar + "");
                userBean.setImgBg(R.drawable.img_avatar + "");
                userBean.setNickName("Asche");
                userBean.setUserName("asche");
                userBean.setGender("男");
                userBean.setSignature("What doesn't kill you makes you stronger!");
                userBean.setDescription("博客小窝asche.top，欢迎来访！");
                userBean.setEmail("asche910@gmail.com");
                userBean.setProfession("Software Engineer");
                userBean.setAddress("美国-加利福尼亚州");
                userBean.setExpert(true);

                break;
            case 0:
                userBean.setImgAvatar(R.drawable.img_robot + "");
                userBean.setImgBg(R.drawable.img_robot + "");
                userBean.setNickName("小灵机器人");
                userBean.setUserName("robot");
                userBean.setGender("女");
                userBean.setSignature("无聊的时候来找小灵玩耍呀！");
                userBean.setProfession("全能");
                userBean.setAddress("中国-北京");
                break;
            default:
                userBean.setImgAvatar(R.drawable.img_avatar + "");
                userBean.setImgBg(R.drawable.img_avatar + "");
                userBean.setNickName("Asche");
                userBean.setUserName("asche");
                userBean.setGender("男");
                userBean.setSignature("What doesn't kill you makes you stronger!");
                userBean.setProfession("Software Engineer");
                userBean.setAddress("美国-加利福尼亚州");
                userBean.setExpert(true);

        }

        userBean.setFollowerNum(n * 10);
        userBean.setFollowNum(n * 10 + 5);

        int id = random.nextInt(999_999_999) + 100_000;
        userBean.setId(id + "");
        return userBean;
    }

}
