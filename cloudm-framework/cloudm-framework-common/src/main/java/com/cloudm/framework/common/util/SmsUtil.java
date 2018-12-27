package com.cloudm.framework.common.util;

import com.cloudm.framework.common.helper.ConfigHelper;
import com.cloudm.framework.common.sms.CustResultDO;
import com.cloudm.framework.common.sms.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:发短信工具类
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 */
@Slf4j
public class SmsUtil {
    //请求地址
    private static String URL = ConfigHelper.getSmsUrl();
    //TOP分配给应用的AppKey
    private static String APP_KEY = ConfigHelper.getSmsAppKey();
    //短信签名AppKey对应的secret值
    private static String SECRET = ConfigHelper.getSmsSecret();
    //短信类型，传入值请填写normal
    private static String SMS_TYPE = ConfigHelper.getSmsType();
    //阿里大于账户配置的短信签名
    private static String SMS_SIGN = ConfigHelper.getSmsSign();
    //阿里大于账户配置的短信模板ID
    private static String SMS_TEMPLATE_CODE = ConfigHelper.getSmsTemplateCode();
    /**
     * 发送短信
     * @param url 正式环境请求地址http格式
     * @param appkey TOP分配给应用的AppKey,即阿里大于账户创建的应用
     * @param secret 签名的App Secret值
     * @param sms_free_sign_name （必须）短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。如“阿里大于”已在短信签名管理中通过审核，则可传入”阿里大于“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大于】欢迎使用阿里大于服务。
     * @param sms_param  （可选）短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
     * @param sms_template_code （必须）短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
     * @param mobile 手机号
     * @return ture 返回成功，false：失败
     */
    public static boolean send(String url,String appkey,String secret,String sms_free_sign_name,String sms_param,String sms_template_code,String mobile) {
        boolean sendResult=false;
        try {
            //发送短信
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("123456");
            req.setSmsType(SMS_TYPE);
            req.setSmsFreeSignName(sms_free_sign_name);
            req.setSmsParamString(sms_param);
            req.setRecNum(mobile);
            req.setSmsTemplateCode(sms_template_code);
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//            System.out.println(rsp.getBody());

            //解析返回结果git
            Gson gson = new Gson();
            CustResultDO<Body> bo = gson.fromJson(rsp.getBody(), new TypeToken<CustResultDO<Body>>(){}.getType());
            if (bo ==null){
                return  false ;
            }
            if (bo.getAlibaba_aliqin_fc_sms_num_send_response()!=null) {
                if (bo.getAlibaba_aliqin_fc_sms_num_send_response().getResult().getSuccess().equals("true")) {
                    sendResult=true;
                }
            }else{
                log.error("send sms is failed !,the mobile number is[{}],errors:{}",mobile,rsp.getBody());
                return  false ;
            }

        } catch (Exception e) {
            log.error("send sms is failed !,the mobile number is[{}],errors:{}",mobile,e);
             return false ;
        }

        return sendResult;
    }

    /**
     *  发送短信
     * @param sms_param 短信模板变量
     * @param mobile 手机号
     * @return
     */
    public static boolean send(String sms_param,String mobile){
        return send(sms_param,mobile,SMS_TEMPLATE_CODE);
    }
    /**
     *  发送短信
     * @param sms_param 短信模板变量
     * @param mobile 手机号
     * @param sms_template_code  模板号
     * @return
     */
    public static boolean send(String sms_param,String mobile,String sms_template_code){
        return send(URL,APP_KEY,SECRET,SMS_SIGN,sms_param,sms_template_code,mobile);
    }

    /**
     *  发送短信
     * @param sms_param 短信模板变量
     * @param mobile 手机号
     * @param sms_template_code  模板号
     * @param sms_free_sign_name 签名
     * @return
     */
    public static boolean send(String sms_param,String mobile,String sms_template_code,String sms_free_sign_name ){
        return send(URL,APP_KEY,SECRET,sms_free_sign_name,sms_param,sms_template_code,mobile);
    }


    /**
     * 内部类获取body，即返回的数据对象
     */
    private class Body {
        private Result result;
        private String request_id;
        public Result getResult() {
            return result;
        }
        public void setResult(Result result) {
            this.result = result;
        }
        public String getRequest_id() {
            return request_id;
        }
        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }
    }
}
