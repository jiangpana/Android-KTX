package com.jansir.firebasektx

import android.app.Activity
import com.jansir.firebasektx.core.initFirebaseRemoteConfig

object FirebaseKTX {

    fun initRemoteConfig(activity: Activity):FirebaseKTX{
        activity.initFirebaseRemoteConfig{

        }
        return this
    }
}