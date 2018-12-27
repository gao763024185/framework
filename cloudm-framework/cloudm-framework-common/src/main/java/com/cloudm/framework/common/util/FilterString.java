package com.cloudm.framework.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @description: 过滤特殊字符
 * @author: sunyinhui
 * @date 21/09/18.
 * @version: v1.0
 */
public class FilterString {
    /**
     * 判断特殊字符
     * @Title : FilterStr
     * @Type : FilterString
     * @date : 2014年2月28日 下午11:01:21
     * @Description : 判断特殊字符
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String filterStr(String str) throws PatternSyntaxException {
        /**
         * 特殊字符
         */
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_ +]";

        /**
         * Pattern p = Pattern.compile("a*b");
         * Matcher m = p.matcher("aaaaab");
         * boolean b = m.matches();
         */
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);

        /**
         * 返回替换结果
         */
        return mat.replaceAll("").trim();
    }

    /**
     * @Title : main
     * @Type : FilterString
     * @date : 2014年2月28日 下午10:58:47
     * @Description : 过滤字符
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 测试字符串
         */
        String totalStr = "~`<>?^&*()you@##%$$#^%^h^&&*&*()<>?ai@#@$~~`_+|dong?><:";
        /**
         * 打印测试字符串
         */
        System.out.println("打印测试字符串:" + totalStr);

        /**
         * 调用过滤字符串的方法
         */
        String filterStr = filterStr(totalStr);
        /**
         * 打印过滤字符串
         */
        System.out.println("打印过滤字符串:" + filterStr);

    }





}
