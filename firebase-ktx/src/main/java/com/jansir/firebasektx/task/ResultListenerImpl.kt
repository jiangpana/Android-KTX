package com.jansir.firebasektx.task

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class ResultListenerImpl<ListenerTypeT, ResultT>(
    private val result: TaskResult<*>, private val targetStates: Int,
    private val onRaise: OnRaise<ListenerTypeT, ResultT>,

    ) {

    private val listenerQueue: Queue<ListenerTypeT> = ConcurrentLinkedQueue()

    fun addListener(listener: ListenerTypeT) {
        var shouldFire = false
        synchronized(result.syncObject) {
            if (result.getInternalState() and targetStates != 0) {
                shouldFire = true
            }
            if (!listenerQueue.contains(listener)) {
                listenerQueue.add(listener)
            }
        }
        if (shouldFire) {
            onRaise.raise(listener, result.snapState as ResultT)
        }
    }


    fun onInternalStateChanged() {
        if (result.getInternalState() and targetStates != 0) {
            for (c in listenerQueue) {
                onRaise.raise(c, result.snapState as ResultT)
            }
        }

    }
}

interface OnRaise<ListenerTypeT, ResultT> {
    fun raise(listener: ListenerTypeT, snappedState: ResultT)
}

interface OnSuccessListener<TResult> {
    fun onSuccess(var1: TResult)
}


interface OnFailureListener {
    fun onFailure(var1: Exception?)
}
