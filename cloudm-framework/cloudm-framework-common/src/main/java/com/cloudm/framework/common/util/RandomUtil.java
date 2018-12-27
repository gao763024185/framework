package com.cloudm.framework.common.util;

import java.util.Random;

/**
 * 随机数相关utils
 * Created by jackson on 2017/6/26.
 */
public class RandomUtil {
    public static final Random rd = new Random();

    /**
     * 获取length位数字和字符组合的随机数
     * @param length
     * @return
     */
    public static String getRandomStr(int length){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i <length;i++){
            sb.append(Integer.toString(rd.nextInt(36),36));
        }
        return sb.toString();
    }
}
