package com.cloudm.framework.common.util;

import com.cloudm.framework.common.sms.ExectTimeBO;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

/**
 * @description: 解析log日志数据工具类
 * @author: Courser
 * @date: 2017/6/6
 * @version: V1.0
 */
@Slf4j
public class StatisticesUtil {

    private static String APPLYSTRING = "Time==>";
    private static String SEARCHSTRING = "INFO";
    private static String USERTAG = "class";

    /**
     * 调用方法名，调用时间，方法名以key-value方式存储
     *
     */
    public static List<ExectTimeBO> readFileByLines(String fileName,Integer systemId) {
        List<ExectTimeBO> boList=new ArrayList<ExectTimeBO>();
        //首先获取本机使用系统
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(isr);
            String tempString = null;
            Map<String, List<ExectTimeBO>> map = new HashMap<>();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains(SEARCHSTRING)) {
                    //获取方法名开始位置
                    int classindex = tempString.indexOf(USERTAG);
                    //字符串分割获取方法名
                    String className = tempString.substring(classindex, tempString.indexOf(",", classindex));
                    //获取调用时间
                    int timeindex = tempString.indexOf(APPLYSTRING);
                    String time = tempString.substring(timeindex + 7, tempString.length() - 2);
                    // 将相关数据放入对象中
                    ExectTimeBO exectTimeBO=setExectTimeBO(className,time,systemId);
                    if (StringUtil.isNotEmpty(className)) {
                        try {
                            if (map.containsKey(className)) {
                                List<ExectTimeBO> timeBOList = map.get(className);
                                timeBOList.add(exectTimeBO);
                                map.put(className, timeBOList);
                            } else {
                                List<ExectTimeBO> exectTimeBOList = new ArrayList();
                                exectTimeBOList.add(exectTimeBO);
                                map.put(className, exectTimeBOList);
                            }
                        } catch (Exception e) {
                            log.error(e.getClass()+e.getMessage()+"出错了{}",e);
                        }
                    }
                }
            }
            reader.close();
            //获取数据到集合中
            boList=forCount(map);
        } catch (IOException e) {
            log.error(e.getClass()+"IO流错误",e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error(e1.getClass()+"关闭IO流异常",e1);
                }
            }
        }
        return boList;
    }

    //将解析的原始log日志数据封装进ExectTimeBO类
    public static ExectTimeBO setExectTimeBO(String className,String time,Integer systemId){
        ExectTimeBO exectTimeBO = new ExectTimeBO();
        exectTimeBO.setClassName(className);
        exectTimeBO.setTime(time);
        exectTimeBO.setSystemId(systemId);
        return  exectTimeBO;
    }



    //封装对象
    public static ExectTimeBO setStatistic(String className,int minTime,int maxTime,int avgTime,int calls,int systemId){
        ExectTimeBO exectTimeBO = new ExectTimeBO();
        exectTimeBO.setClassName(className);
        exectTimeBO.setMinTime(minTime);
        exectTimeBO.setMaxTime(maxTime);
        exectTimeBO.setAvgTime(avgTime);
        exectTimeBO.setCalls(calls);
        exectTimeBO.setSystemId(systemId);
        return exectTimeBO;
    }

    //获取平均值，最大最小时间和次数
    public static List<ExectTimeBO> forCount(Map<String, List<ExectTimeBO>> logMap) {
        ExectTimeBO exectTimeBO = new ExectTimeBO();
        List<ExectTimeBO> boList=new ArrayList<ExectTimeBO>();
        String className = null;
        int calls = 0;
        int maxTime = 0;
        int minTime = 0;
        int avgTime = 0;
        int systemId=0;

        Iterator<Map.Entry<String, List<ExectTimeBO>>> it = logMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<ExectTimeBO>> entry = it.next();
            //当只调用一次的方法得出平均，最大，最小时间和调用次数
            List infoList = entry.getValue();
            String[] times = new String[infoList.size()];
            int i = 0;
            Iterator iterator = infoList.iterator();
            //使用迭代器将BO类分出来进行算法得出所需数据
            while (iterator.hasNext()) {
                    ExectTimeBO dd = (ExectTimeBO) iterator.next();
                    className = dd.getClassName();
                    String str = dd.getTime();
                    systemId=dd.getSystemId();
                    times[i] = str;
                    i++;
                }
            int[] iTime = new int[times.length];
            for (int j = 0; j < times.length; j++) {
                    iTime[j] = Integer.parseInt(times[j]);
            }
            calls = iTime.length;
            maxTime = minTime = iTime[0];
            int count = 0;
            for (int j = 0; j < iTime.length; j++) {
                    if (iTime[j] > maxTime) {
                        maxTime = iTime[j];
                    }
                    if (iTime[j] < minTime) {
                        minTime = iTime[j];
                    }
                    count = count + iTime[j];
            }
            avgTime = count / calls;
            //将数据放入对象中
            exectTimeBO=setStatistic(className,minTime,maxTime,avgTime,calls,systemId);
            boList.add(exectTimeBO);
        }
        return boList;
    }
}
