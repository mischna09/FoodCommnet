package com.cookie14.foodcomment.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.HttpException
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Rx支援
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

object ApiManager {
    suspend fun <T> request(deferred: Deferred<Resource<T>>) : Resource<T>? {
        try {
            return deferred.await()
        } catch (e: Exception) {
            Log.e("ApiManager Error", e.message.toString())
            e.printStackTrace()
            val a = Resource<T>()
            a.code = e.message.toString()
            return a
        } catch (e: Throwable) {
            Log.e("ApiManager", "throwable")
            return null
        } catch (e: HttpException){
            println("CODEEEEEEEEEEEEEEEEEEEE ${e.message()}")
            return null
        }
    }
}