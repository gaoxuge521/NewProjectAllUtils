package com.gxg.alltils.projectallutils.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 作者：Administrator on 2017/11/14 15:04
 * 邮箱：android_gaoxuge@163.com
 * 整个应用级别的activity堆栈式管理
 */
public class ActivityManage {
    private static ActivityManage instance;

    // 记录Activity
    private static Map<String, Activity> activityMap;
    private static List<Activity> activityList = new ArrayList<>();

    private static LinkedList<String> tagLists;
    /** 是否是远程的打开 */
    private boolean mIsPending;
    private ActivityManage() {

    }
    /**
     * 单一实例
     */
    public static ActivityManage getAppManager() {
        if (instance == null) {
            instance = new ActivityManage();
        }
        return instance;
    }
    public boolean isPending() {
        return mIsPending;
    }
    public void setmIsPending(boolean mIsPending) {
        this.mIsPending = mIsPending;
    }
    public synchronized void addActivity(Activity activity) {
        activityList.add(activity);
        if(tagLists == null){
            tagLists = new LinkedList<>();
        }
        if (activityMap == null) {
            activityMap = new HashMap<>();
        }
        String tag = activity.getClass().getSimpleName();
        if(tagLists.contains(tag)){
            tag = tag + UUID.randomUUID().toString();	// 避免相同键覆盖
            tagLists.add(tag);
            activityMap.put(tag, activity);
        }else {
            tagLists.add(tag);
            activityMap.put(tag, activity);
        }
    }

    public Activity getActivity(Class<?> cls){
        if (activityMap != null && activityMap.size() > 0) {
            return activityMap.get(cls.getSimpleName());
        }else{
            return null;
        }
    }
    public Activity getActivity(){
        if (activityMap.size() > 0 && tagLists.size() > 0 && tagLists.size() == activityMap.size()) {
            return activityMap.get(tagLists.getLast());
        }else
            return null;
    }
    public void finishActivity() {
        if (activityMap.size() > 0 && tagLists.size() > 0 && tagLists.size() == activityMap.size()) {
            finishActivity(tagLists.getLast());
        }
    }
    public void finishActivity(String tag) {
        try {
            if (activityMap.size() > 0) {
                Activity activity = activityMap.remove(tag);
                if (activityList.contains(activity)){
                    activityList.remove(activity);
                }
                if(activity!=null){
                    tagLists.remove(tag);
                    activity.finish();
                    activity = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishActivity(Class<?> cls){
        finishActivity(cls.getSimpleName());
    }

    public void finishAllActivityExcept(Class<?> cls){
        finishAllActivityExcept(cls.getSimpleName());
    }

    public void finishAllActivityExcept(String activityTag){
        ArrayList<String> tags = getActivityTags();
        for(String tag : tags){
            if(!tag.equals(activityTag)){
                finishActivity(tag);
            }
        }
    }

    /**
     * 关闭所有的activity
     */
    public void finishAllActivity() {
        if (activityMap.size() > 0) {
            ArrayList<String> tags = getActivityTags();
            for(String tag : tags){
                finishActivity(tag);
            }
        }
    }

    /**
     * 返回所有的activity的tag
     */
    private ArrayList<String> getActivityTags(){
        Set<String> tags = activityMap.keySet();
        ArrayList<String> tagList = new ArrayList<>();
        tagList.addAll(tags);
        return tagList;
    }
    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // 杀掉该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
