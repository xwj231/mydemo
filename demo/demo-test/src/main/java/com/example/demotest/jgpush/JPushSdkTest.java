package com.example.demotest.jgpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xuj231
 * @description
 * @date 2019/11/28 11:38
 */
public class JPushSdkTest {
    
    public static void main(String[] args) {
        JPushClient jpushClient = new JPushClient(JPushUtils.MASTER_SECRET, JPushUtils.APP_KEY, null, ClientConfig.getInstance());

        Set<String> userIds = new HashSet<>();
        userIds.add("TEST_10245d820c6a92b13310ab930d84");
        
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.alias(userIds))
//                .setAudience(Audience.alias("963"))
                .setAudience(Audience.tag("tag4"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(IosAlert.newBuilder().setTitleAndBody("张三",null,"{\"type\":\"1000\",\"text\":\"123\"}").build())
                                .setBadge(-1)
//                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert("测试推送消息1")
                                .setTitle("李四")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .build();

        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            e.printStackTrace();

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            e.printStackTrace();
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
        }
    }
}
