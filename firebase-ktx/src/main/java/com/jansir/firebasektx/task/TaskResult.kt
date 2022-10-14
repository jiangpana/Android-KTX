package com.jansir.firebasektx.task


open class TaskResult<ResultT> {


    val INTERNAL_STATE_NOT_STARTED = 1
    val INTERNAL_STATE_QUEUED = INTERNAL_STATE_NOT_STARTED shl 1
    val INTERNAL_STATE_IN_PROGRESS = INTERNAL_STATE_NOT_STARTED shl 2
    val INTERNAL_STATE_PAUSING = INTERNAL_STATE_NOT_STARTED shl 3
    val INTERNAL_STATE_PAUSED = INTERNAL_STATE_NOT_STARTED shl 4
    val INTERNAL_STATE_CANCELING = INTERNAL_STATE_NOT_STARTED shl 5
    val INTERNAL_STATE_FAILURE = INTERNAL_STATE_NOT_STARTED shl 6
    val INTERNAL_STATE_SUCCESS = INTERNAL_STATE_NOT_STARTED shl 7
    val INTERNAL_STATE_CANCELED = INTERNAL_STATE_NOT_STARTED shl 8
    val STATES_SUCCESS = INTERNAL_STATE_SUCCESS
    val STATES_PAUSED = INTERNAL_STATE_PAUSED
    val STATES_FAILURE = INTERNAL_STATE_FAILURE
    val STATES_CANCELED = INTERNAL_STATE_CANCELED
    val STATES_COMPLETE = STATES_SUCCESS or STATES_FAILURE or STATES_CANCELED
    val STATES_INPROGRESS = (STATES_COMPLETE or STATES_PAUSED).inv()

    var currentState = INTERNAL_STATE_NOT_STARTED


    internal val syncObject = Any()

    var snapState: Any? = null

    private val successManager: ResultListenerImpl<OnSuccessListener<in ResultT>, ResultT> =
        ResultListenerImpl(
            this, STATES_SUCCESS,
            object : OnRaise<OnSuccessListener<in ResultT>, ResultT> {
                override fun raise(
                    listener: OnSuccessListener<in ResultT>,
                    snappedState: ResultT
                ) {
                    listener.onSuccess(snappedState)
                }
            })

    private val failureManager: ResultListenerImpl<OnFailureListener, Exception> =
        ResultListenerImpl(
            this, STATES_FAILURE,
            object : OnRaise<OnFailureListener, Exception> {
                override fun raise(
                    listener: OnFailureListener,
                    snappedState: Exception
                ) {
                    listener.onFailure(snappedState)
                }
            })


    fun getInternalState(): Int {
        return currentState
    }

    fun success(res: ResultT) {
        synchronized(syncObject) {
            snapState = res
            tryChangeState(INTERNAL_STATE_SUCCESS)
        }
    }

    fun failure(res: Exception) {
        synchronized(syncObject) {
            snapState = res
            tryChangeState(INTERNAL_STATE_FAILURE)
        }
    }

    private fun tryChangeState(requestedState: Int) {
        currentState = requestedState
        successManager.onInternalStateChanged()
        failureManager.onInternalStateChanged();
    }


    open fun addOnSuccessListener(
        listener: OnSuccessListener<in ResultT>
    ): TaskResult<ResultT> {
        successManager.addListener(listener)
        return this
    }

    open fun addOnFailureListener(
        listener: OnFailureListener
    ): TaskResult<ResultT> {
        failureManager.addListener(listener)
        return this
    }


}

