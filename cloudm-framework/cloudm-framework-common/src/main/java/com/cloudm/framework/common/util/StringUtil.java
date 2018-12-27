package com.cloudm.framework.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @description: 字符串工具类
 * @author: Courser
 * @date: 2017/3/15
 * @version: V1.0
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str !=null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str){
        return  !isEmpty(str);
    }

    /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

    /**
     * 通过循环对象数组参数列表(中间用分隔符相隔)，获取新的字符串
     * @param separator 分隔符 如："_"
     * @param arrays 如：String "abc", String "ddd"
     * @return 获取新的字符串 如： "abc_ddd"
     * @author liuting
     */
    public static String getStringByArraySplitSeparator(String separator, Object[] arrays) {
        if (isEmpty(separator) || arrays == null || arrays.length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (Object val : arrays) {
            if (val != null) {
                stringBuilder.append(val).append(separator);
            }
        }
        String targetVar = stringBuilder.toString();
        if (isEmpty(targetVar)) {
            return targetVar;
        }
        return targetVar.substring(0, stringBuilder.length() - separator.length());
    }

    /**
     * 判断字符串是否是空白
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    /**
     * 判断字符串是否不是空白
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断字符串数组中 是否有空白字符串
     *
     * <pre>
     * StringUtils.isAnyBlank(null)             = true
     * StringUtils.isAnyBlank(null, "foo")      = true
     * StringUtils.isAnyBlank(null, null)       = true
     * StringUtils.isAnyBlank("", "bar")        = true
     * StringUtils.isAnyBlank("bob", "")        = true
     * StringUtils.isAnyBlank("  bob  ", null)  = true
     * StringUtils.isAnyBlank(" ", "bar")       = true
     * StringUtils.isAnyBlank("foo", "bar")     = false
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return {@code true} if any of the CharSequences are blank or null or whitespace only
     * @since 3.2
     */
    public static boolean isAnyBlank(CharSequence... css) {

        return StringUtils.isAnyBlank(css);
    }

    /**
     * 判断字符串数组，所有字符串是否都是非空白
     *
     * <pre>
     * StringUtils.isNoneBlank(null)             = false
     * StringUtils.isNoneBlank(null, "foo")      = false
     * StringUtils.isNoneBlank(null, null)       = false
     * StringUtils.isNoneBlank("", "bar")        = false
     * StringUtils.isNoneBlank("bob", "")        = false
     * StringUtils.isNoneBlank("  bob  ", null)  = false
     * StringUtils.isNoneBlank(" ", "bar")       = false
     * StringUtils.isNoneBlank("foo", "bar")     = true
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return {@code true} if none of the CharSequences are blank or null or whitespace only
     * @since 3.2
     */
    public static boolean isNoneBlank(CharSequence... css) {
        return !isAnyBlank(css);
    }

    /**
     * 将byte数组转化为String
     * <pre>
     *       byte[] b = {0x0F,0xDD}
     *     StringUtil.byteToString(b)="0FDD"
     *
     * <pre/>
     * @param data
     * @return
     */
    public static String byteToString(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(String.format("%02X", data[i]));
        }
        return buf.toString();
    }

    /**
     * 根据当前的流水号，生成下一个流水号
     * 当前是001 下一个002
     * 当前是005 下一个006
     * @param curGlideNumber 当前的流水号
     * @return
     * String 10005  ----整形  10005
     * 整形 10005+1    ----整形 10006
     * 整形10006      ----String 10006
     * String 10006  ----截取 0006
     */
    public  static String geneNextGlideNumber(String curGlideNumber) {

        curGlideNumber="1"+curGlideNumber;
        Integer icurGlideNumber=Integer.parseInt(curGlideNumber);
        icurGlideNumber++;
        curGlideNumber=icurGlideNumber+"";
        curGlideNumber=curGlideNumber.substring(1);
        return curGlideNumber;
    }

    /**
     * 生成序列号
     * @param number 数字
     * @param length 长度
     * @return
     */
    public static String genGildeNumber(Integer number,Integer length){
        return String.format("%0"+length+"d",number);
    }


}
