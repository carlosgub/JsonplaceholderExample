package com.carlosgub.jsonexample.data

import com.carlosgub.jsonexample.model.Post
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiClient {

    //https://obscure-earth-55790.herokuapp.com/api/museums
    private val API_BASE_URL = "https://jsonplaceholder.typicode.com"

    private var servicesApiInterface:ServicesApiInterface?=null

    fun build():ServicesApiInterface?{
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java)

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServicesApiInterface{
        @GET("/posts")
        fun posts(): Call<MutableList<Post>>

        @GET
        fun getPost(@Url url:String): Call<Post>

        @FormUrlEncoded
        @POST("/posts")
        fun createPost(@Field("userId")userId:Int,
                    @Field("title") title:String,
                    @Field("body") body:String): Call<Post>
    }
}