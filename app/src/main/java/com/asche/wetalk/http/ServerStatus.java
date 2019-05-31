package com.asche.wetalk.http;

/**
 * 服务器状态类  以保证服务器未正常工作时app不会闪退！
 * @date 2019年5月31日
 */
public class ServerStatus {

//    private static final boolean SERVER_UP = true; // 服务器正常
//    private static final boolean SERVER_DOWN = false; // 服务器异常

    private static boolean SERVER_STATUS = false; // 服务器状态

    public static boolean isServerNormal() {
        return SERVER_STATUS;
    }

    public static void setServerStatus(boolean serverStatus) {
        SERVER_STATUS = serverStatus;
    }
}
