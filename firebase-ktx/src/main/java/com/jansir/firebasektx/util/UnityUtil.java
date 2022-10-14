package com.jansir.firebasektx.util;

import android.app.Activity;

import java.lang.reflect.Method;


public class UnityUtil {
    /**
     * unity项目启动时的的上下文
     */
    private static Activity _unityActivity;
    /**
     * 获取unity项目的上下文
     * @return
     */
    public static Activity getActivity(){
        if(null == _unityActivity) {
            try {
                Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
                Activity activity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
                _unityActivity = activity;
            } catch (ClassNotFoundException e) {

            } catch (IllegalAccessException e) {

            } catch (NoSuchFieldException e) {

            }
        }
        return _unityActivity;
    }

    /**
     * 调用Unity的方法
     * @param gameObjectName    调用的GameObject的名称
     * @param functionName      方法名
     * @param args              参数
     * @return                  调用是否成功
     */
    public static boolean callUnity(String gameObjectName, String functionName, String args){
        System.out.println("UnityUtil" + " -> callUnity ,ObjectName = " + gameObjectName +",functionName = "+functionName);
        try {
            Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
            Method method =classtype.getMethod("UnitySendMessage", String.class,String.class,String.class);
            method.invoke(classtype,gameObjectName,functionName,args);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TAG" + " ->callUnity false" + gameObjectName + "->"+functionName);
        return false;
    }


    public static boolean isUnityEnv(){
        try {
             Class.forName("com.unity3d.player.UnityPlayer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
