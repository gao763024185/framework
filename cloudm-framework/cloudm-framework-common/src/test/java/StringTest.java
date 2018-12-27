import com.cloudm.framework.common.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Courser
 * @date: 2017/5/23
 * @version: V1.0
 */
public class StringTest {
    public static void main(String[] args){
        int i  =  1 ;
        int j = 4;
        System.out.println(String.format("%0"+j+"d",i));
        HttpUtil httpUtil =  new HttpUtil();
        Map<String,Object> map = new HashMap<>();
        map.put("deviceId",100005);
        map.put("type",1);

        httpUtil.post("http://localhost:8092/mars/json/admin/fenceInfo/getFence",map);
    }
}
