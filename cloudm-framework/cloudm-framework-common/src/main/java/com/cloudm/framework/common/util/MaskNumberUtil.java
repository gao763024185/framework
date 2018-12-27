package com.cloudm.framework.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;

/**
 * @description 关键信息加星隐藏工具
 * @author: lx.xu
 * @date: 2018/9/7 下午5:09
 * @version: v1.0
 */
public class MaskNumberUtil {
    /**
     * 用户相关私密性号码的打码隐藏加星号加*
     * <p>参数异常直接返回null</p>
     *
     * @param number 号码串
     * @param front  需要显示前几位
     * @param end    需要显示末几位
     * @return 处理完成的号码
     */
    public static String mask(String number, int front, int end) {
        // 身份证不能为空
        if (TextUtils.isEmpty(number)) {
            return null;
        }
        // 需要截取的长度不能大于号码长度
        if ((front + end) > number.length()) {
            return null;
        }
        // 需要截取的不能小于0
        if (front < 0 || end < 0) {
            return null;
        }
        // 计算*的数量
        int asteriskCount = number.length() - (front + end);
        StringBuffer asteriskStr = new StringBuffer();
        for (int i = 0; i < asteriskCount; i++) {
            asteriskStr.append("*");
        }
        String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
        return number.replaceAll(regex, "$1" + asteriskStr + "$3");
    }

    /**
     * 用户相关私密性字符(包括汉字)打码隐藏加星号加*
     * <p>参数异常直接返回null</p>
     *
     * @param str
     * @param front 需要显示前几位
     * @param end   需要显示末几位
     * @return 处理完成的文字
     */
    public static String replaceCharacter(String str, int front, int end) {
        // 字符串长度校验
        if (checkStrLength(str, front, end)) {
            return null;
        }

        //计算*的数量
        int asteriskCount = str.length() - (front + end);
        String star = StringUtils.repeat("*", asteriskCount);
        return str.replaceAll("(.{" + front + "})(.+)(.{" + end + "})", "$1" + star + "$3");
    }

    /**
     * 用户相关私密性字符(包括汉字)打码隐藏加星号加*
     * <p>参数异常直接返回null</p>
     *
     * @param str
     * @param front 需要显示前几位
     * @param end   需要显示末几位
     * @return 处理完成的文字
     */
    public static String replaceByStringBuffer(String str, int front, int end) {
        // 字符串长度校验
        if (checkStrLength(str, front, end)) {
            return null;
        }

        StringBuilder sb = new StringBuilder(str);
        for (int i = front; i < str.length() - end; i++) {
            sb.setCharAt(i, '*');
        }

        return sb.toString();
    }

    /**
     * 待替换的字符串长度校验
     *
     * @param str
     * @param front
     * @param end
     * @return
     */
    private static boolean checkStrLength(String str, int front, int end) {
        //身份证不能为空
        if (StringUtil.isEmpty(str)) {
            return true;
        }
        //需要截取的长度不能大于号码长度
        if ((front + end) > str.length()) {
            return true;
        }
        //需要截取的不能小于0
        if (front < 0 || end < 0) {
            return true;
        }
        return false;
    }
}
