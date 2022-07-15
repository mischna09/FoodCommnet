package com.cookie14.foodcommnet.api

import com.cookie14.foodcommnet.ArticleModel
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @GET("/ajax_test2.php")
    fun getList(
        //@Field("username") username: String?,
        //@Field("password") password: String?
    ): Single<Resource<List<TestList>>>

    @GET("/foodcomment/ajax_test2.php")
    fun getArticleList(): Single<Resource<List<ArticleModel>>>
}