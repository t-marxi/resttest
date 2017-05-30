package com.klimigo.application.conversation.authorization;

public class TimeUtils {

    public static long getCurrentTimePlus(long seconds) {
        long ct = System.currentTimeMillis();
        return ct + seconds * 1000;
    }

    public static long countTimePlus(long time, long seconds) {
        return time + seconds * 1000;
    }
}
