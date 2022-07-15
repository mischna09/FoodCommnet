package com.cookie14.foodcommnet.api

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.util.concurrent.TimeUnit


class RetrofitManager {
    //private val instance = RetrofitManager()
    private var service: APIService

    //fun getInstance(): RetrofitManager {
    //    return instance
    //}

    constructor(){
        val url = "https://c14game.000webhostapp.com"
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)) // gson支援
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Rx支援
            .client(
                OkHttpClient().newBuilder()
                    .connectTimeout(5L, TimeUnit.SECONDS)
                    .readTimeout(20L, TimeUnit.SECONDS)
                    .writeTimeout(20L, TimeUnit.SECONDS)
                    .build()
            )
            .build()
        service = retrofit.create(APIService::class.java)
    }

    fun getAPI(): APIService {
        //return instance.service
        return service
    }
}
data class TestList(
    @SerializedName("name")
    var name:   String?,
    @SerializedName("num")
    var num:    Int?,
    @SerializedName("email")
    var email:  String?,
    @SerializedName("type")
    var type:   String?,
): Serializable