package com.carlosgub.jsonexample.view

import com.carlosgub.jsonexample.data.ApiClient
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LandingPresenter  {

    private var call: Single<MutableList<Post>>?=null

    fun retrievePosts(callback: OperationCallback) {
        callback.showLoading()
        call= ApiClient.build()?.posts()
        call?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe (
                {
                    callback.hideLoading()
                    callback.onSuccess(it)
                },{
                    callback.hideLoading()
                    callback.onError(it.message)
                }
            )

    }

}
