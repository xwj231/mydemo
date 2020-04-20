package com.example.demotest.jgpush;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.Base64;

/**
 * @author xuj231
 * @description
 * @date 2019/11/28 11:03
 */
public class JPushUtils {
    public static final String JPUSH_URL = "https://api.jpush.cn/v3/push";
    
    public static final String APP_KEY = "601fc1b7850555ada76fee64";
    
    public static final String MASTER_SECRET = "5cdf3368c890c178ee1bd342";
    
    /**
     * 获取极光推送Authorization
     * Authorization: 'Basic base64_auth_string'
     * base64_auth_string 的生成算法为：base64(appKey:masterSecret)
     * @param appKey
     * @param masterSecret
     * @return
     */
    public static String getAuthorization(String appKey,String masterSecret) {
        Base64.Encoder encoder = Base64.getEncoder();
        return "Basic "+encoder.encodeToString((appKey+":"+masterSecret).getBytes());
    }

    /**
     * 消息推送
     * @param jPushEntity
     * @return
     */
    public static HttpResponse push(JPushEntity jPushEntity){
        return HttpRequest.post(JPUSH_URL)
                .header("Authorization", JPushUtils.getAuthorization(APP_KEY, MASTER_SECRET))
                .contentType("application/json")
                .charset("utf-8")
                .body(JSON.toJSONString(jPushEntity))
                .send();
    }
}
