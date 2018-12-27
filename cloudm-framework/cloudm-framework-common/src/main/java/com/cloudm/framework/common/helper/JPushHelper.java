package com.cloudm.framework.common.helper;


import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.cloudm.framework.common.util.CastUtil;
import com.cloudm.framework.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 极光推送工具类
 * @author: Courser
 * @date: 2017/3/16
 * @version: V1.0
 */
@Slf4j
public class JPushHelper {

    private static final String appKey = ConfigHelper.getJpushAppKey();

    private static final String masterSecret = ConfigHelper.getJpushMasterSecret();

    private static final boolean apnsProduction = ConfigHelper.getJpushApnsProduction();

    /**
     * ios默认的推送声音类型
     */
    private static final String DEFAULT  = "default";

    /**
     * map中存放声音类型的key
     */
    private static final String SOUND  = "sound";

    /**
     * map中存放的对应的key: appKey
     */
    private static final String KEY  = "appKey";

    /**
     * map中存放的对应的key: appSecret
     */
    private static final String SECRET  = "appSecret";

    /**
     * map中存放的是否推送对应的key: status
     */
    private static final String STATUS = "status";

    /**
     * 按别名发送到单个用户
     * @param map 参数(type属性必须传入，如：map.put("type","***");)
     * @param alias 别名（即登录的ID）
     * @param msg 主体消息
     * @param title 标题
     * @param time
     * @return 0:不成功 1:android成功,IOS不成功 2:IOS成功,android不成功 3.都成功
     */
    public static long defaultPushByAlias(long alias, String msg, String title, int time,Map<String, String> map) throws Exception{
        long l = 0;
        // 如果没有传相关配置，则使用默认的配置
        String key = appKey;
        String secret = masterSecret;
        boolean status = apnsProduction;
        if (map.containsKey(KEY) && map.containsKey(SECRET) && map.containsKey(STATUS)){
            String str = map.get(KEY);
            String str1 = map.get(SECRET);
            String str2 = map.get(STATUS);
            if (!StringUtil.isEmpty(str) && !StringUtil.isEmpty(str1) && !StringUtil.isEmpty(str2)){
                key = str;
                secret = str1;
                status = CastUtil.castBoolean(str2);
            }
        }
        JPushClient jpushClient = new JPushClient(secret, key, 3);
        PushPayload pushPayload =null ;
        try {        //android 推送
            pushPayload = getPushPayload(Platform.android(),Notification.android(msg, title, map),alias, time, status);
            PushResult result = jpushClient.sendPush(pushPayload);
            //推送成功，返回true，l+1
            if(null!=result && result.isResultOK()==true){
                if(log.isInfoEnabled()){
                    log.info("result android={}" , result.isResultOK());
                }
                l += 1;
            }
        }catch (Exception e){
            log.error("as JPushHepler push msg is failed !params is ==>{},exception is ==>{}",pushPayload,e);
        }



        jpushClient = new JPushClient(secret, key, 3);
        PushPayload pushPayloadIOS =null ;
        try {
            // 如果没有传声音类型 则使用默认声音
            String sound = DEFAULT;
            if (map.containsKey(SOUND)){
                String str = map.get(SOUND);
                if (!StringUtil.isEmpty(str)){
                    sound = str;
                }
            }
            //ios 推送
            Notification iosNotification = Notification.newBuilder().addPlatformNotification(
                    IosNotification.newBuilder()
                            .setSound(sound)
                            .setAlert(msg)
                            .addExtras(map).build())
                    .build();

            pushPayloadIOS = getPushPayload(Platform.ios(),iosNotification,alias, time, status);
            PushResult resultIOS = jpushClient.sendPush(pushPayloadIOS);
            //推送成功，返回true，l+2
            if(null!=resultIOS && resultIOS.isResultOK()==true){
                if(log.isInfoEnabled()){
                    log.info("result ios={}" , resultIOS.isResultOK());
                }
                l += 2;
            }
        }catch (Exception e){
            log.error("ios JPushHepler push msg is failed !params is ==>{},exception is ==>{}",pushPayloadIOS,e);
        }
        return l;

    }


    /**
     * 按别名发送到单个用户, 传入 type
     * @param type 参数(type属性必须传入，如：map.put("type","***");)
     * @param alias 别名（即登录的ID）
     * @param msg 主体消息
     * @param title 标题
     * @param time
     * @return 0:不成功 1:android成功,IOS不成功 2:IOS成功,android不成功 3.都成功
     */
    public static long pushByAliasType(long alias, String msg, String title, int time, int type) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf(type));
        return defaultPushByAlias(alias, msg, title, time,  map);
    }

    /**
     * 按别名发送到单个用户 type bizId
     * @param type 参数(type属性必须传入，如：map.put("type","***");)
     * @param alias 别名（即登录的ID）
     * @param msg 主体消息
     * @param title 标题
     * @param time
     * @return 0:不成功 1:android成功,IOS不成功 2:IOS成功,android不成功 3.都成功
     */
    public static long pushByAliasTypeId(long alias, String msg, String title, int time, int type, int bizId) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf(type));
        map.put("id", String.valueOf(bizId));
        return defaultPushByAlias(alias, msg, title, time,  map);
    }

    /**
     * 按别名发送到单个用户 type bizId (支持推送到其他的app)
     *
     * @param jpushParam
     * @return 0:不成功 1:android成功,IOS不成功 2:IOS成功,android不成功 3.都成功
     */
    public static long pushWithAnotherApp(JpushParam jpushParam) throws Exception{
        Map<String, String> map = new HashMap<>();
        if (jpushParam.getType() != null){
            map.put("type", String.valueOf(jpushParam.getType()));
        }
        if (jpushParam.getBizId() != null){
            map.put("id", String.valueOf(jpushParam.getBizId()));
        }
        String sound = jpushParam.getSound();
        if (!StringUtil.isEmpty(sound)){
            map.put(SOUND, jpushParam.getSound());
        }
        String key = jpushParam.getAppKey();
        if (!StringUtil.isEmpty(key)){
            map.put(KEY, jpushParam.getAppKey());
        }
        String secret = jpushParam.getAppSecret();
        if (!StringUtil.isEmpty(secret)){
            map.put(SECRET,secret);
        }
        Boolean status = jpushParam.getStatus();
        if (status != null){
            map.put(STATUS, CastUtil.castString(status));
        }
        return defaultPushByAlias(jpushParam.getAlias(), jpushParam.getMsg(), jpushParam.getTitle(), jpushParam.getTime(),  map);
    }


    /**
     * 构造私有PushPayLoad
     * @param platform 平台
     * @param notification 通知
     * @param alias 别名
     * @param time 持续时间
     * @param status 是否开启线上推送
     * @return
     */
    private static PushPayload getPushPayload(Platform platform,Notification notification,long alias, int time, boolean status) {
        return PushPayload
                .newBuilder()
                .setOptions(Options.newBuilder().setApnsProduction(status).setBigPushDuration(time).build())
                .setPlatform(platform)
                .setAudience(Audience.alias(String.valueOf(alias)))
                .setNotification(notification)
                .build();
    }
}
