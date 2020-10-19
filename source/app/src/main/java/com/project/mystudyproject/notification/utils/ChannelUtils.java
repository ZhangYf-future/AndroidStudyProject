package com.project.mystudyproject.notification.utils;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * 通知渠道渠道工具类
 */
public class ChannelUtils {

    //默认的通知渠道信息
    private static final String DEFAULT_CHANNEL_ID = "defaultChannel";
    private static final String DEFAULT_CHANNEL_NAME = "默认的通知渠道";
    private static final int DEFAULT_CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private static final String DEFAULT_CHANNEL_DESCRIPTION = "当前应用配置的默认的通知渠道";

    private static ChannelUtils instance;

    private Context context;

    private ChannelUtils() {

    }

    /**
     * 单例模式
     *
     * @return
     */
    public static ChannelUtils getInstance(Context context) {
        if (instance == null)
            instance = new ChannelUtils();
        instance.context = context;
        return instance;
    }

    public String getDefaultChannelId() {
        return DEFAULT_CHANNEL_ID;
    }

    /**
     * 获取默认通知渠道的信息
     *
     * @return 默认通知渠道信息
     */
    public NotificationChannel getDefaultChannelInfo() {
        return getChannelInfo(DEFAULT_CHANNEL_ID);
    }

    /**
     * 根据渠道ID获取渠道信息
     *
     * @param channelId 渠道ID
     * @return 渠道信息
     */
    public NotificationChannel getChannelInfo(String channelId) {
        if (moreThan8()) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            return manager.getNotificationChannel(channelId);
        }
        return null;
    }

    //创建通知渠道最终的执行类
    public void createChannel(
            String channelId,
            String channelName,
            int importance,
            String description,
            String groupId
    ) {
        if (moreThan8()) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(description);
            if (!TextUtils.isEmpty(groupId))
                channel.setGroup(groupId);
            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * 根据渠道id，渠道名称,渠道重要性,渠道说明来创建一个渠道信息
     *
     * @param channelId
     * @param channelName
     * @param importance
     * @param description
     */
    public void createChannelWithAll(
            String channelId,
            String channelName,
            int importance,
            String description
    ) {
        createChannel(channelId, channelName, importance, description, null);
    }

    /**
     * 创建一个默认的通知渠道
     *
     * @return 返回默认通知渠道的id
     */
    public String createDefaultChannel() {
        createChannelWithAll(DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, DEFAULT_CHANNEL_IMPORTANCE, DEFAULT_CHANNEL_DESCRIPTION);
        return DEFAULT_CHANNEL_ID;
    }

    /**
     * 创建一个高等级的默认的通知渠道
     * 这个渠道中的信息都使用默认信息，只有重要性这个属性使用IMPORTANCE_HIGH这个属性
     *
     * @return 默认渠道id
     */
    public String createHighDefaultChannel(String channelId, String channelName,String description) {
        createChannelWithAll(channelId, channelName, NotificationManager.IMPORTANCE_HIGH, description);
        return channelId;
    }

    /**
     * 根据渠道id创建一个通知渠道，渠道相关的属性都会设置为这个id
     *
     * @param id 渠道id
     */
    public void createChannelWithId(String id) {
        createChannelWithAll(id, id, DEFAULT_CHANNEL_IMPORTANCE, id);
    }

    /**
     * 根据渠道id和渠道名称创建一个渠道，渠道说明仍然使用name属性
     *
     * @param id   渠道id
     * @param name 渠道名称
     */
    public void createChannelWithIdName(String id, String name) {
        createChannelWithAll(id, name, DEFAULT_CHANNEL_IMPORTANCE, name);
    }

    /**
     * 根据渠道id，渠道名称和渠道重要性来创建一个通知渠道
     *
     * @param id         渠道id
     * @param name       渠道名称
     * @param importance 渠道重要性
     */
    public void createChannelWithIdNameImportance(String id, String name, int importance) {
        createChannelWithAll(id, name, importance, name);
    }

    /**
     * 创建渠道分组
     *
     * @param id   渠道分组id
     * @param name 渠道分组名称
     */
    public void createChannelGroup(String id, String name) {
        if (moreThan8()) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannelGroup group = new NotificationChannelGroup(id, name);
            manager.createNotificationChannelGroup(group);
        }
    }

    /**
     * 根据渠道id删除指定的通知渠道
     *
     * @param id 渠道id
     */
    public void deleteChanelWithId(String id) {
        if (moreThan8()) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.deleteNotificationChannel(id);
        }
    }

    /**
     * 根据渠道分组id删除指定的通知分组
     * @param groupId 渠道分组id
     */
    public void deleteChannelGroupWithId(String groupId) {
        if (moreThan8()) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.deleteNotificationChannelGroup(groupId);
        }
    }


    //跳转到渠道设置页面
    public void toChannelSettingActivity(String id) {
        if (moreThan8()) {
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, id);
            context.startActivity(intent);
        }
    }

    //判断当前SDK版本是否大于Android8
    private static boolean moreThan8() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}
