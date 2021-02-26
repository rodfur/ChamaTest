package com.chama.googleplacestest.data.model

class OperationResult<T>(val result: T? = null, val status: OperationResultStatus) {

    public enum class OperationResultStatus {
        SUCCESS,
        ERROR,
        NOT_AUTHORIZED,
        LOADING,
        UNDEFINED
    }
}