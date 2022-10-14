package com.jansir.firebasektx.core

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
private val firestore = Firebase.firestore
