package ru.you11.skbkonturtestproject.api

class CallResult<T> {

    constructor(data: T) {
        this.mData = data
    }

    constructor(message: String) {
        this.mError = message
    }

    private var mData: T? = null
    private var mError: String? = null

    val isSuccess get() = mError == null
    val data get() = mData!!
    val error get() = mError ?: ""
    var isCached = false
}