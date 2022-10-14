package com.jansir.firebasektx.core

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import com.jansir.firebasektx.callback.callCocosUnityAndroidMethod

fun Activity.openGoogleReview( isTest:Boolean =false){
    val manager =  if (!isTest)ReviewManagerFactory.create(this) else FakeReviewManager(this)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            val flow = manager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener { task1 ->
                if (task1.isSuccessful){
                    callCocosUnityAndroidMethod("googleReviewSuccess","")
                }else{
                    val exception = (task1.getException() )
                    exception?.printStackTrace()
                    callCocosUnityAndroidMethod("googleReviewFail","${exception?.message}")
                }
            }
        } else {
            val exception = (task.getException() )
            exception?.printStackTrace()
            callCocosUnityAndroidMethod("googleReviewFail","${exception?.message}")
        }
    }

}

