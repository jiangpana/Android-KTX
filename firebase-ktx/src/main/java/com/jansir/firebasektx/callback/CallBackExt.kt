package com.jansir.firebasektx.callback

import com.jansir.firebasektx.util.ReflectUtils
import com.jansir.firebasektx.util.UnityUtil


sealed class UseEnv {
    object COCOS : UseEnv();
    object UNITY : UseEnv();
    object ANDROID : UseEnv();
}

interface MethodCallBack {
    fun <T> call(method: String, args: T)

    class CocosMethodCallBack : MethodCallBack {
        override fun <T> call(method: String, args: T) {
            val className = "org.cocos2dx.javascript.HuaWeiManager"
            ReflectUtils.reflect(className).method(method, args)
        }
    }

    class AndroidCallBack : MethodCallBack {
        override fun <T> call(method: String, args: T) {
            val className = "org.cocos2dx.javascript.HuaWeiManager"
            ReflectUtils.reflect(className).method(method, args)
        }
    }

    class UnityCallBack : MethodCallBack {
        override fun <T> call(method: String, args: T) {
            val className = "HuaWeiManager"
            UnityUtil.callUnity(className, method, args.toString())
        }
    }
}

private val useEnv by lazy {
    try {
        Class.forName("org.cocos2dx.lib.Cocos2dxActivity")
        return@lazy UseEnv.COCOS
    } catch (_: ClassNotFoundException) {
    }
    try {
        Class.forName("com.unity3d.player.UnityPlayer")
        return@lazy UseEnv.UNITY
    } catch (_: ClassNotFoundException) {
    }
    return@lazy UseEnv.ANDROID

}

val methodCallback by lazy {
    return@lazy when (useEnv) {
        UseEnv.COCOS -> MethodCallBack.CocosMethodCallBack()
        UseEnv.UNITY -> MethodCallBack.UnityCallBack()
        else -> MethodCallBack.AndroidCallBack()
    }

}

 inline fun <reified T> callCocosUnityAndroidMethod(method: String, args: T) {
    println("callCocosUnityMethod  ,method name = $method")
    methodCallback.call(method,args)
}
