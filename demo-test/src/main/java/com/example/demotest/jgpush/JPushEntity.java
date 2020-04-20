package com.example.demotest.jgpush;

import java.util.Map;

/**
 * @author xuj231
 * @description 极光推送对象
 * @date 2019/11/27 10:43
 */
public class JPushEntity {
    /**
     * 平台 
     * 指定平台 ["android", "ios"]
     * 所有平台 all
     */
    private String platform;

    /**
     * 推送目标
     * 广播 all
     * 指定目标 https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/
     */
    private String audience;

    /**
     * 通知内容
     */
    private Notification notification;

    public JPushEntity(String platform, String audience, Notification notification) {
        this.platform = platform;
        this.audience = audience;
        this.notification = notification;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

/**
 * 通知对象
 */
class Notification {

    /**
     * 通知内容
     * 这里统一定义，如果平台有自己的定义，则覆盖这里定义
     */
    private String alert;

    /**
     * 安卓通知参数
     */
    private Android android;

    /**
     * ios通知参数
     */
    private Ios ios;

    public Notification(String alert, Android android, Ios ios) {
        this.alert = alert;
        this.android = android;
        this.ios = ios;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    public Ios getIos() {
        return ios;
    }

    public void setIos(Ios ios) {
        this.ios = ios;
    }
}

class Android {

    /**
     * 通知标题
     * 不指定显示app名称
     */
    private String title;
    
    /**
     * 通知内容
     * 如果安卓自己定义了通知内容，则覆盖Notification的通知内容
     */
    private String alert;

    /**
     * 扩展参数，供业务使用，与极光参数无关
     */
    private Map<String,String> extras;

    public Android(Map<String, String> extras) {
        this.extras = extras;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}

class Ios {
    /**
     * 通知提示声音或警告通知
     */
    private String sound;

    /**
     * 应用角标
     * 如果不填，表示不改变角标数字，否则把角标数字改为指定的数字；
     * 为 0 表示清除。JPush 官方 SDK 会默认填充 badge 值为 "+1"
     */
    private String badge;

    /**
     * 扩展参数，供业务使用，与极光参数无关
     */
    private Map<String,String> extras;

    public Ios(String sound, String badge, Map<String, String> extras) {
        this.sound = sound;
        this.badge = badge;
        this.extras = extras;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
