package com.carlosgub.jsonexample.view

import com.carlosgub.jsonexample.data.ApiClient
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LandingPresenter  {

    private var call: Call<MutableList<Post>>?=null

    fun retrievePosts(callback: OperationCallback) {
        callback.showLoading()
        call= ApiClient.build()?.posts()
        call?.enqueue(object : Callback<MutableList<Post>> {
            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                callback.onError(t.message)
                callback.hideLoading()
            }

            override fun onResponse(call: Call<MutableList<Post>>, response: Response<MutableList<Post>>) {
                response.body()?.let {
                    if(response.isSuccessful){ //200
                        callback.onSuccess(it)
                        callback.hideLoading()
                    }else{
                        callback.onError("Hubo un Error")
                        callback.hideLoading()
                    }
                }
            }
        })
    }

    fun cancel() {
        call?.cancel()
    }

}
