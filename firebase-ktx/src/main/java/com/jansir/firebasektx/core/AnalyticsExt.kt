package com.jansir.firebasektx.core

import androidx.core.os.bundleOf
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

fun String.logEvent(){
    Firebase.analytics.logEvent(this, bundleOf(this to this))
}