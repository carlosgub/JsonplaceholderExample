package com.carlosgub.jsonexample.data

interface OperationCallback {

    fun onSuccess(obj:Any?)
    fun onError(obj:String?)
    fun showLoading()
    fun hideLoading()
}