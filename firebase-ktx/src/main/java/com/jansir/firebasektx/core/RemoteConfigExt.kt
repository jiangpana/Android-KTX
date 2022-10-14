package com.jansir.firebasektx.core

import android.app.Activity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private val remoteConfig  by lazy {
    Firebase.remoteConfig.apply {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        setConfigSettingsAsync(configSettings)
    }
}

internal fun Activity.initFirebaseRemoteConfig(success:()->Unit) {
    remoteConfig.fetchAndActivate()
        .addOnCompleteListener(this) { task ->
            remoteConfigInit =true
            success()
        }
}
@Volatile
private var remoteConfigInit =false

fun String.getString():String{
    if (!remoteConfigInit)return ""
   return  remoteConfig.getString(this)
}


