package com.carlosgub.jsonexample.view.create

import com.carlosgub.jsonexample.data.ApiClient
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPresenter {
    private var call: Call<Post>?=null

    fun createPost(userId:String,title:String,body:String,callback: OperationCallback) {
        callback.showLoading()
        call= ApiClient.build()?.createPost(userId.toInt(),title,body)
        call?.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(t.message)
                callback.hideLoading()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
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
