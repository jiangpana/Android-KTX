package com.jansir.firebasektx;

import androidx.annotation.Nullable;

import com.jansir.firebasektx.core.AnalyticsExtKt;
import com.jansir.firebasektx.core.RemoteConfigExtKt;
import com.jansir.firebasektx.core.StorageExtKt;
import com.jansir.firebasektx.task.OnFailureListener;
import com.jansir.firebasektx.task.OnSuccessListener;

public class Test {
    public static void main(String[] args) {

        AnalyticsExtKt.logEvent("");
        RemoteConfigExtKt.getString("");
        StorageExtKt.getDownloadUrl("").addOnFailureListener(var1 -> {

        }).addOnSuccessListener(var1 -> {

        });

    }
}
