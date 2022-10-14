package com.jansir.firebasektx.core

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.jansir.firebasektx.task.TaskResult


private  var storageRef =  Firebase.storage.reference


fun String.getDownloadUrl(): TaskResult<String> {
    return TaskResult<String>().apply {
        storageRef.child(this@getDownloadUrl).downloadUrl.addOnSuccessListener {
            success(it.toString())
        }.addOnFailureListener() {
            failure(it)
        }
    }


}