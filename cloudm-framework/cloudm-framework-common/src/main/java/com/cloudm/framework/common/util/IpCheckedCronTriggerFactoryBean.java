package com.cloudm.framework.common.util;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @description: work job触发器工厂，当部署在两台上，只有一台可以用
 * @author: Courser
 * @date: 2017/6/27
 * @version: V1.0
 */
public class IpCheckedCronTriggerFactoryBean extends CronTriggerFactoryBean {
    @Setter
    private String executeIp;
    private Logger log = LoggerFactory.getLogger(IpCheckedCronTriggerFactoryBean.class);

    @Override
    public void afterPropertiesSet() throws ParseException {
        List<String> ipList = new ArrayList<>();
        ipList.add("127.0.0.1");
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface nif = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        ipList.add(inetAddress.getHostAddress());
                    }
                }
            }
            // 检测server获取内网ip信息
            log.info("server获取内网ip信息: " + ipList);

            if (StringUtils.isEmpty(executeIp) || ipList.contains(executeIp.trim())) {
//        super.
            }else{
                super.setStartDelay(3162240000L);  //不需要运行,设置启动时间为几年后
            }
            super.afterPropertiesSet();
        } catch (SocketException ex) {
            throw new RuntimeException("获取本机ip异常，定时任务不能启动。");
        }
    }
}