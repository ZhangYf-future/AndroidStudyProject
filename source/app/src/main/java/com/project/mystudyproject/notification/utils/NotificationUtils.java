package com.project.mystudyproject.notification.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知工具类
 */
public class NotificationUtils {

    //默认的通知id
    private static final int DEFAULT_NOTIFICATION_ID = 1000;
    //发送通知的id
    private int mChannelId = 10000;
    //默认的通知组Key
    private static final String DEFAULT_NOTIFICATION_GROUP_KEY = "defaultNotificationGroup";
    //通知分组的id
    private int mNotificationGroupId = 10000000;
    //发送通知分组id的集合
    private Map<String, Integer> mNotificationGroupIdMap = new HashMap<String, Integer>();

    private static NotificationUtils instance;
    private Context context;

    private NotificationUtils() {

    }

    public static NotificationUtils getInstance(Context context) {
        if (instance == null)
            instance = new NotificationUtils();
        instance.context = context;
        return instance;
    }


    public NotificationCompat.Builder createNotification(
            String channelId,
            int smallImageRes,
            Integer largeImageRes,
            String title,
            String content,
            PendingIntent pendingIntent,
            NotificationCompat.Style style,
            boolean cancel,
            int priority,
            String groupKey
    ) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        //小图标
        builder.setSmallIcon(smallImageRes);
        //大图标，可以不设置
        if (largeImageRes != null)
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeImageRes));
        //标题
        builder.setContentTitle(title);
        //内容
        builder.setContentText(content);
        //点击之后执行的操作，跳转页面或者发送通知等
        if (pendingIntent != null)
            builder.setContentIntent(pendingIntent);

        //设置样式
        if (style != null)
            builder.setStyle(style);

        //是否点击后可以取消
        builder.setAutoCancel(cancel);
        //重要性，这里需要和渠道中的重要性保持一致
        builder.setPriority(priority);
        //不允许添加默认操作按钮
        builder.setAllowSystemGeneratedContextualActions(false);
        if (!TextUtils.isEmpty(groupKey))
            builder.setGroup(groupKey);
        return builder;
    }

    //一个普通的通知栏消息
    public NotificationCompat.Builder createNotification(String channelId, int smallImage, String title, String content) {
        return createNotification(channelId, smallImage, null, title, content, null, null, true, NotificationCompat.PRIORITY_DEFAULT, null);
    }

    //带有大图标的通知栏消息
    public NotificationCompat.Builder createNotification(String channelId, int smallImage, int largeImage, String title, String content) {
        return createNotification(channelId, smallImage, largeImage, title, content, null, null, true, NotificationCompat.PRIORITY_DEFAULT, null);
    }

    //带有点击操作的通知栏消息
    public NotificationCompat.Builder createNotification(String channelId, int smallImage, String title, String content, PendingIntent pendingIntent) {
        return createNotification(channelId, smallImage, null, title, content, pendingIntent, null, true, NotificationCompat.PRIORITY_DEFAULT, null);
    }

    //带有点击操作和大图标的通知栏消息
    public NotificationCompat.Builder createNotification(String channelId, int smallImage, int largeImage, String title, String content, PendingIntent pendingIntent) {
        return createNotification(channelId, smallImage, largeImage, title, content, pendingIntent, null, true, NotificationCompat.PRIORITY_DEFAULT, null);
    }

    //带有通知样式的通知栏消息
    public NotificationCompat.Builder createNotification(String channelId, int smallImage, String title, String content, NotificationCompat.Style style) {
        return createNotification(channelId, smallImage, null, title, content, null, style, true, NotificationCompat.PRIORITY_DEFAULT, null);
    }

    //带有通知分组的通知栏消息
    public NotificationCompat.Builder createNotification(String channelId, int smallImage, String title, String content, NotificationCompat.Style style, PendingIntent pendingIntent, String groupKey) {
        return createNotification(channelId, smallImage, null, title, content, pendingIntent, style, true, NotificationCompat.PRIORITY_DEFAULT, groupKey);
    }


    //创建一个通知组
    public NotificationCompat.Builder createNotificationGroup(String channelId, String groupKey, int smallImage, String title, String content,String subText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(smallImage);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setGroup(groupKey);
        builder.setGroupSummary(true);
        builder.setSubText(subText);
        return builder;
    }


    //发送通知
    public void sendNotification(int id, Notification notification) {
        NotificationManagerCompat.from(context).notify(id, notification);
    }

    //不指定通知id发送一条通知
    public void sendNotification(Notification notification) {
        NotificationManagerCompat.from(context).notify(mChannelId, notification);
        mChannelId++;
    }

    //使用默认的通知id发送通知
    public void sendDefaultNotification(Notification notification) {
        NotificationManagerCompat.from(context).notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    //发送通知分组
    public void sendNotificationGroup(Notification group) {
        if (TextUtils.isEmpty(group.getGroup())) {
            sendDefaultNotification(group);
        } else {
            Integer id = mNotificationGroupIdMap.get(group.getGroup());
            if (id == null) {
                id = mNotificationGroupId;
                mNotificationGroupId++;
                mNotificationGroupIdMap.put(group.getGroup(), id);
            }
            NotificationManagerCompat.from(context).notify(id, group);
        }
    }


    //发送一条默认通知
    public void sendDefaultNotification(int smallImage, String title, String content) {
        ChannelUtils channelUtils = ChannelUtils.getInstance(context);
        channelUtils.createDefaultChannel();
        NotificationCompat.Builder builder = createNotification(channelUtils.getDefaultChannelId(), smallImage, title, content);
        sendNotification(DEFAULT_NOTIFICATION_ID, builder.build());
    }


}
