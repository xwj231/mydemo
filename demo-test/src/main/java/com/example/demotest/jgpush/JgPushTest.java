package com.example.demotest.jgpush;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.generic.NEW;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuj231
 * @description 极光推送测试
 * @date 2019/11/27 10:24
 */
public class JgPushTest {
    
    public static void main(String[] args) {
        //安卓扩展参数
        Map<String,String> androidExtras = new HashMap<>(1);
        androidExtras.put("android-key1","android-value1");
        
        //ios扩展参数
        Map<String,String> iosExtras = new HashMap<>(1);
        iosExtras.put("ios-key1","ios-value1");
        
        //构建通知参数
        JPushEntity jPushEntity = new JPushEntity("all", "all", new Notification("Hi,JPush3 !", new Android(androidExtras), new Ios("sound.caf", "+1", iosExtras)));

        jPushEntity.getNotification().getAndroid().setTitle("张三");
        
        System.out.println("通知参数："+JSON.toJSONString(jPushEntity));
        
        HttpResponse resp = JPushUtils.push(jPushEntity);

        System.out.println("推送结果："+resp.bodyText());
    }
    
}
