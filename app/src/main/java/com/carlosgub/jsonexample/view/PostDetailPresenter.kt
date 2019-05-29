package com.carlosgub.jsonexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.carlosgub.jsonexample.R
import com.carlosgub.jsonexample.data.ApiClient
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import kotlinx.android.synthetic.main.activity_post_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailPresenter {
    private var call: Call<Post>?=null

    fun retrievePost(id:String,callback: OperationCallback) {
        callback.showLoading()
        call= ApiClient.build()?.getPost("posts/$id")
        call?.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(t.message)
                callback.hideLoading()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                response?.body()?.let {
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
