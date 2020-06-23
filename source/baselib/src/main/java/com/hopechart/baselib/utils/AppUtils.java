package com.hopechart.baselib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断 App 是否安装
     *
     * @param action   action
     * @param category category
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = AppManager.getApp().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断 App 是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 打开 App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) return;
        AppManager.getApp().startActivity(IntentUtils.getLaunchAppIntent(packageName));
    }

    /**
     * 打开 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 关闭 App
     */
    public static void exitApp() {
        AppManager.getInstance().finishAllActivity();
        System.exit(0);
    }

    /**
     * 获取 App 包名
     *
     * @return App 包名
     */
    public static String getAppPackageName() {
        return AppManager.getApp().getPackageName();
    }

    /**
     * 获取 App 具体设置
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 具体设置
     *
     * @param packageName 包名
     */
    public static void getAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) return;
        AppManager.getApp().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
    }

    /**
     * 获取 App 名称
     *
     * @return App 名称
     */
    public static String getAppName() {
        return getAppName(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 名称
     *
     * @param packageName 包名
     * @return App 名称
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 图标
     *
     * @return App 图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 图标
     *
     * @param packageName 包名
     * @return App 图标
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 路径
     *
     * @return App 路径
     */
    public static String getAppPath() {
        return getAppPath(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 路径
     *
     * @param packageName 包名
     * @return App 路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本号
     *
     * @return App 版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 版本号
     *
     * @param packageName 包名
     * @return App 版本号
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本码
     *
     * @return App 版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 版本码
     *
     * @param packageName 包名
     * @return App 版本码
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(AppManager.getApp().getPackageName());
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return isAppDebug(AppManager.getApp().getPackageName());
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 App 签名
     *
     * @return App 签名
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 签名
     *
     * @param packageName 包名
     * @return App 签名
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断 App 是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager) AppManager.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(AppManager.getApp().getPackageName());
            }
        }
        return false;
    }


    /**
     * 封装 App 信息的 Bean 类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        名称
         * @param icon        图标
         * @param packageName 包名
         * @param packagePath 包路径
         * @param versionName 版本号
         * @param versionCode 版本码
         * @param isSystem    是否系统应用
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(AppManager.getApp().getPackageName());
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = AppManager.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到 AppInfo 的 Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo 类
     */
    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 获取所有已安装 App 信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}（名称，图标，包名，包路径，版本号，版本 Code，是否系统应用）</p>
     * <p>依赖上面的 getBean 方法</p>
     *
     * @return 所有已安装的 AppInfo 列表
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = AppManager.getApp().getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    /**
     * 清除 App 所有数据
     *
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(final String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除 App 所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(final File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDbs();
        isSuccess &= CleanUtils.cleanInternalSP();
        isSuccess &= CleanUtils.cleanInternalFiles();
        isSuccess &= CleanUtils.cleanExternalCache();
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //安装软件
    public static void installApk(final File file, final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent newIntent = new Intent(Intent.ACTION_VIEW);
            Uri contentUri =
                    FileProvider.getUriForFile(context, "com.hopechart.zqy.provider", file);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            newIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            //                                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    newIntent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            context.startActivity(newIntent);
        } else {
            String cmd = "chmod 777 " + file.getAbsolutePath();
            try {
                //Logger.d("download success$cmd")
                Runtime.getRuntime().exec(cmd); //data/data下面需要权限才能安装
                Uri apk = Uri.parse("file://" + file.getAbsolutePath());
                //Logger.d("download onSuccess url $apk")
                Intent intents = new Intent();
                intents.setAction("android.intent.action.VIEW");
                intents.addCategory("android.intent.category.DEFAULT");
                intents.setType("application/vnd.android.package-archive");
                intents.setData(apk);
                intents.setDataAndType(apk, "application/vnd.android.package-archive");
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intents.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intents);

                } else {
                    ToastUtils.showShort("无法安装当前应用，请手动安装");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}