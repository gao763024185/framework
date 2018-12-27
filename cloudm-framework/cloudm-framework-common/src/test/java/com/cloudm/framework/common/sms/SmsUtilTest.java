package com.cloudm.framework.common.sms;

import com.cloudm.framework.common.helper.JPushHelper;
import com.cloudm.framework.common.util.SmsUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Sms 单元测试
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 */
public class SmsUtilTest {
 public static void main(String[] args) throws Exception{
     Demo demo  =  new Demo();
     demo.setProductName("黄栋测试");
     System.out.print(SmsUtil.send(new Gson().toJson(demo),"18273128032"));
//     String msg ="msg";
//     String title = null ;
//     Map<String, String> map = new HashMap<String, String>();
//     map.put("type", "9");
//     map.put("url","1111");
//     JPushHelper.defaultPushByAlias(106L,msg,null,1,map);
 }

}
class Demo{
    private String productName ;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}